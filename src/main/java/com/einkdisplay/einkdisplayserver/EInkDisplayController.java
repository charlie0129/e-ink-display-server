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

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class EInkDisplayController {
    private final EInkDisplayRepository eInkDisplayRepository;
    private final MessageRepository messageRepository;
    private final EInkDisplayModelAssembler eInkDisplayModelAssembler;
    private final MessageModelAssembler messageModelAssembler;

    public EInkDisplayController(EInkDisplayRepository eInkDisplayRepository,
                                 MessageRepository messageRepository,
                                 EInkDisplayModelAssembler eInkDisplayModelAssembler,
                                 MessageModelAssembler messageModelAssembler) {
        this.eInkDisplayRepository = eInkDisplayRepository;
        this.messageRepository = messageRepository;
        this.eInkDisplayModelAssembler = eInkDisplayModelAssembler;
        this.messageModelAssembler = messageModelAssembler;
    }


    @GetMapping("/api/displays")
    public CollectionModel<EntityModel<EInkDisplay>> getDisplayAll() {

        List<EntityModel<EInkDisplay>> displays =
                eInkDisplayRepository.findAll()
                                     .stream()
                                     .map(eInkDisplayModelAssembler::toModel)
                                     .collect(Collectors.toList());

        return CollectionModel.of(displays,
                                  linkTo(methodOn(EInkDisplayController.class).getDisplayAll())
                                          .withSelfRel());
    }


    @GetMapping("/api/displays/{id}")
    public EntityModel<EInkDisplay> getDisplaySingle(@PathVariable Long id) {

        EInkDisplay referencedDisplay =
                eInkDisplayRepository.findById(id)
                                     .orElseThrow(() -> new ResourceNotFoundException("Display with ID=" + id + " could " +
                                                                                      "not be found."));
        return eInkDisplayModelAssembler.toModel(referencedDisplay);
    }

    @PostMapping("/api/displays")
    public ResponseEntity<?> addDisplay(@RequestBody EInkDisplay newDisplay) {
        EntityModel<EInkDisplay> entityModel =
                eInkDisplayModelAssembler.toModel(eInkDisplayRepository.save(newDisplay));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF)
                                                 .toUri())
                             .body(entityModel);
    }

    @PutMapping("/api/displays/{id}")
    public ResponseEntity<?> updateDisplay(@PathVariable Long id,
                                           @RequestBody EInkDisplay newDisplay) {
        EInkDisplay updatedDisplay =
                eInkDisplayRepository.findById(id)
                                     .map(eInkDisplay -> {
                                         eInkDisplay.setLatitude(newDisplay.getLatitude());
                                         eInkDisplay.setLongitude(newDisplay.getLongitude());
                                         eInkDisplay.setName(newDisplay.getName());
                                         return eInkDisplayRepository.save(eInkDisplay);
                                     })
                                     .orElseThrow(() -> new ResourceNotFoundException("Display with ID=" + id + " could " +
                                                                                      "not be found."));

        EntityModel<EInkDisplay> entityModel = eInkDisplayModelAssembler.toModel(updatedDisplay);

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF)
                                                 .toUri())
                             .body(entityModel);
    }

    @DeleteMapping("/api/displays/{id}")
    public ResponseEntity<?> deleteDisplay(@PathVariable Long id) {

        eInkDisplayRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/api/displays/{displayId}/messages")
    public CollectionModel<EntityModel<Message>> getMessageAllByDisplay(@PathVariable Long displayId) {

        List<EntityModel<Message>> messages =
                messageRepository.findByDisplayOrderByIdDesc(eInkDisplayRepository.findById(displayId)
                                                                                  .orElseThrow(() -> new ResourceNotFoundException(
                                                                                              "Display with ID=" + displayId + " could " +
                                                                                              "not be found.")))
                                 .stream()
                                 .map(messageModelAssembler::toModel)
                                 .collect(Collectors.toList());

        return CollectionModel.of(messages,
                                  linkTo(methodOn(EInkDisplayController.class).getMessageAllByDisplay(displayId))
                                          .withSelfRel());
    }

}
