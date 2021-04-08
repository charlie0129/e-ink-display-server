package com.einkdisplay.einkdisplayserver.controller;

import com.einkdisplay.einkdisplayserver.exception.ResourceNotFoundException;
import com.einkdisplay.einkdisplayserver.model.ImageFile;
import com.einkdisplay.einkdisplayserver.model.ImageFileModelAssembler;
import com.einkdisplay.einkdisplayserver.model.Message;
import com.einkdisplay.einkdisplayserver.repository.ImageFileRepository;
import com.einkdisplay.einkdisplayserver.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class ImageFileController {

    @Autowired
    private final ImageFileRepository imageFileRepository;
    private final MessageRepository messageRepository;
    private final ImageFileModelAssembler imageFileModelAssembler;

    public ImageFileController(ImageFileRepository imageFileRepository,
                               MessageRepository messageRepository,
                               ImageFileModelAssembler imageFileModelAssembler) {
        this.imageFileRepository = imageFileRepository;
        this.messageRepository = messageRepository;
        this.imageFileModelAssembler = imageFileModelAssembler;
    }

    @PostMapping("/api/messages/{messageId}/images")
    public ResponseEntity<?> addImage(@PathVariable String messageId,
                                      @RequestParam("file") MultipartFile multipartFile) {

        ImageFile imageFile;
        try {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            imageFile =
                    new ImageFile(fileName,
                                  multipartFile.getContentType(),
                                  multipartFile.getBytes(),
                                  messageRepository.findById(messageId)
                                                   .orElseThrow(() -> new ResourceNotFoundException("Message " +
                                                                                                    "with ID="
                                                                                                    + messageId
                                                                                                    + " could not be found.")));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        EntityModel<ImageFile> entityModel =
                imageFileModelAssembler.toModel(imageFileRepository.save(imageFile));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF)
                                                 .toUri())
                             .body(entityModel);
    }

    @GetMapping("/api/images/{imageId}")
    public ResponseEntity<?> getImage(@PathVariable String imageId) {
        ImageFile imageFile = imageFileRepository
                .findById(imageId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Image file" +
                        " " +
                        "with id=" + imageId + " " +
                        "could not be found."));

        return ResponseEntity.ok()
                             .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + imageFile.getName() + "\"")
                             .body(imageFile.getData());
    }

    @GetMapping("/api/images")
    public CollectionModel<EntityModel<ImageFile>> getImageAll() {
        List<EntityModel<ImageFile>> images =
                imageFileRepository.findAll()
                                   .stream()
                                   .map(imageFileModelAssembler::toModel)
                                   .collect(Collectors.toList());

        return CollectionModel.of(images,
                                  linkTo(methodOn(ImageFileController.class).getImageAll())
                                          .withSelfRel());
    }

    @GetMapping("/api/messages/{messageId}/images")
    public CollectionModel<EntityModel<ImageFile>> getImageAllByMessage(@PathVariable String messageId) {

        Message referencedMessage = messageRepository.findById(messageId)
                                                     .orElseThrow(
                                                             () -> new ResourceNotFoundException("Message " +
                                                                                                 "with ID="
                                                                                                 + messageId
                                                                                                 + " could not be " +
                                                                                                 "found."));
        List<EntityModel<ImageFile>> images =
                imageFileRepository.findByMessage(referencedMessage)
                                   .stream()
                                   .map(imageFileModelAssembler::toModel)
                                   .collect(Collectors.toList());

        return CollectionModel.of(images,
                                  linkTo(methodOn(ImageFileController.class).getImageAllByMessage(messageId))
                                          .withSelfRel());
    }

}
