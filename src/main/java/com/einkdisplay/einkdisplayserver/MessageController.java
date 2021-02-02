package com.einkdisplay.einkdisplayserver;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class MessageController {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final EInkDisplayRepository eInkDisplayRepository;
    private final MessageModelAssembler messageModelAssembler;


    public MessageController(MessageRepository messageRepository,
                             UserRepository userRepository,
                             EInkDisplayRepository eInkDisplayRepository,
                             MessageModelAssembler messageModelAssembler) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.eInkDisplayRepository = eInkDisplayRepository;
        this.messageModelAssembler = messageModelAssembler;
    }


    @GetMapping("/api/messages")
    public CollectionModel<EntityModel<Message>> getMessageAll() {

        List<EntityModel<Message>> messsages =
                messageRepository.findAll()
                                 .stream()
                                 .map(messageModelAssembler::toModel)
                                 .collect(Collectors.toList());

        return CollectionModel.of(messsages,
                                  linkTo(methodOn(MessageController.class).getMessageAll())
                                          .withSelfRel());
    }


    @PostMapping("/api/messages")
    public ResponseEntity<?> addMessage(@RequestBody MessageForm messageForm) {

        User referencedUser = userRepository.findById(messageForm.getUserId())
                                            .orElseThrow(() -> new ResourceNotFoundException("User with ID="
                                                                                             + messageForm.getUserId()
                                                                                             + " could not be found."));

        EInkDisplay referencedDisplay =
                eInkDisplayRepository.findById(messageForm.geteInkDisplayId())
                                     .orElseThrow(() -> new ResourceNotFoundException("Display with ID="
                                                                                      + messageForm.geteInkDisplayId()
                                                                                      + " could not be found."));

        LocalDateTime submissionLocalDateTime;

        if (messageForm.getTime().equals("")) {
            submissionLocalDateTime = LocalDateTime.now();
        } else {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            submissionLocalDateTime = LocalDateTime.parse(messageForm.getTime(), dateTimeFormatter);
        }

        EntityModel<Message> entityModel =
                messageModelAssembler.toModel(messageRepository.save(new Message(submissionLocalDateTime,
                                                                                 messageForm.getMessage(),
                                                                                 referencedUser,
                                                                                 referencedDisplay)));

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF)
                                                 .toUri())
                             .body(entityModel);
    }


    @GetMapping("/api/messages/{messageId}")
    public EntityModel<Message> getMessageSingle(@PathVariable Long messageId) {

        return messageModelAssembler.toModel(messageRepository.findById(messageId)
                                                              .orElseThrow(() -> new ResourceNotFoundException("Message " +
                                                                                                               "with ID="
                                                                                                               + messageId
                                                                                                               + " could not be found.")));
    }


    @PutMapping("/api/messages/{messageId}")
    public ResponseEntity<?> updateMessage(@PathVariable Long messageId,
                                           @RequestBody MessageForm newMessage) {

        User referencedUser = userRepository.findById(newMessage.getUserId())
                                            .orElseThrow(() -> new ResourceNotFoundException("User with ID="
                                                                                             + newMessage.getUserId()
                                                                                             + " could not be found."));

        EInkDisplay referencedDisplay =
                eInkDisplayRepository.findById(newMessage.geteInkDisplayId())
                                     .orElseThrow(() -> new ResourceNotFoundException("Display with ID="
                                                                                      + newMessage.geteInkDisplayId()
                                                                                      + " could not be found."));

        LocalDateTime submissionLocalDateTime;

        if (newMessage.getTime().equals("")) {
            submissionLocalDateTime = LocalDateTime.now();
        } else {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            submissionLocalDateTime = LocalDateTime.parse(newMessage.getTime(), dateTimeFormatter);
        }

        Message updatedMessage =
                messageRepository.findById(messageId)
                                 .map(message -> {
                                     message.setMessage(newMessage.getMessage());
                                     message.setEInkDisplay(referencedDisplay);
                                     message.setUser(referencedUser);
                                     message.setTime(submissionLocalDateTime);
                                     return messageRepository.save(message);
                                 })
                                 .orElseThrow(() -> new ResourceNotFoundException("Message with ID="
                                                                                  + messageId
                                                                                  + " could not be found."));

        EntityModel<Message> entityModel = messageModelAssembler.toModel(updatedMessage);

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF)
                                                 .toUri())
                             .body(entityModel);
    }


    @DeleteMapping("/api/messages/{messageId}")
    public ResponseEntity<?> deleteMessage(@PathVariable Long messageId) {

        messageRepository.deleteById(messageId);

        return ResponseEntity.noContent().build();
    }
}


class MessageForm {
    private String message;
    private String time = "";
    private Long userId;
    private Long eInkDisplayId;

    public MessageForm() {
    }

    public MessageForm(String message,
                       String time,
                       Long userId,
                       Long eInkDisplayId) {
        this.message = message;
        this.time = time;
        this.userId = userId;
        this.eInkDisplayId = eInkDisplayId;
    }

    public MessageForm(String message,
                       Long userId,
                       Long eInkDisplayId) {
        this.message = message;
        this.userId = userId;
        this.eInkDisplayId = eInkDisplayId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long geteInkDisplayId() {
        return eInkDisplayId;
    }

    public void seteInkDisplayId(Long eInkDisplayId) {
        this.eInkDisplayId = eInkDisplayId;
    }
}
