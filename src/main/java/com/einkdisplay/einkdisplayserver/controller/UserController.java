package com.einkdisplay.einkdisplayserver.controller;

import com.einkdisplay.einkdisplayserver.model.Message;
import com.einkdisplay.einkdisplayserver.model.MessageModelAssembler;
import com.einkdisplay.einkdisplayserver.repository.MessageRepository;
import com.einkdisplay.einkdisplayserver.exception.ResourceConflictException;
import com.einkdisplay.einkdisplayserver.exception.ResourceNotFoundException;
import com.einkdisplay.einkdisplayserver.model.User;
import com.einkdisplay.einkdisplayserver.model.UserModelAssembler;
import com.einkdisplay.einkdisplayserver.repository.UserRepository;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserController {

    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final UserModelAssembler userModelAssembler;
    private final MessageModelAssembler messageModelAssembler;


    public UserController(UserRepository userRepository,
                          MessageRepository messageRepository,
                          UserModelAssembler userModelAssembler,
                          MessageModelAssembler messageModelAssembler) {
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
        this.userModelAssembler = userModelAssembler;
        this.messageModelAssembler = messageModelAssembler;
    }


    @PostMapping("/api/users")
    public ResponseEntity<?> addUser(@RequestBody User newUser) {

        Optional<User> referencedUser = userRepository.findById(newUser.getId());

        if (referencedUser.isPresent()) {
            throw new ResourceConflictException("User with ID=" + newUser.getId() + " already exists.");
        } else {
            EntityModel<User> entityModel =
                    userModelAssembler.toModel(userRepository.save(newUser));

            return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF)
                                                     .toUri())
                                 .body(entityModel);
        }
    }


    @GetMapping("/api/users/{id}")
    public EntityModel<User> getUserSingle(@PathVariable(value = "id") Long id) {

        User referencedUser =
                userRepository.findById(id)
                              .orElseThrow(() -> new ResourceNotFoundException("User with ID="
                                                                               + id
                                                                               + " could not be found."));

        return userModelAssembler.toModel(referencedUser);
    }


    @PutMapping("/api/users/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id,
                                        @RequestParam(value = "name") String name) {

        User updatedUser =
                userRepository.findById(id)
                              .map(user -> {
                                  user.setName(name);
                                  return userRepository.save(user);
                              })
                              .orElseThrow(() -> new ResourceNotFoundException("User with ID="
                                                                               + id
                                                                               + " could not be found."));

        EntityModel<User> entityModel = userModelAssembler.toModel(updatedUser);

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF)
                                                 .toUri())
                             .body(entityModel);
    }


    @DeleteMapping("/api/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {

        userRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }


    @GetMapping("/api/users/{userId}/messages")
    public CollectionModel<EntityModel<Message>> getMessageAllByUser(@PathVariable Long userId,
                                                                     @RequestParam(value = "n", defaultValue = "0") Long n) {

        User referencedUser = userRepository.findById(userId)
                                            .orElseThrow(() -> new ResourceNotFoundException("User with ID="
                                                                                             + userId
                                                                                             + " could not be found."));

        List<EntityModel<Message>> messages;

        messages = messageRepository.findByUserOrderByTimeDesc(referencedUser)
                                    .stream()
                                    .map(messageModelAssembler::toModel)
                                    .collect(Collectors.toList());

        if (0 < n) {
            messages = messages.subList(0, n.intValue());
        }

        return CollectionModel.of(messages,
                                  linkTo(methodOn(UserController.class).getMessageAllByUser(userId, n))
                                          .withSelfRel());
    }
}
