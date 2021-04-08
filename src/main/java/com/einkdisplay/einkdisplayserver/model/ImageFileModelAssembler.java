package com.einkdisplay.einkdisplayserver.model;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.einkdisplay.einkdisplayserver.controller.ImageFileController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class ImageFileModelAssembler
        implements RepresentationModelAssembler<ImageFile, EntityModel<ImageFile>> {
    @Override
    public EntityModel<ImageFile> toModel(ImageFile imageFile) {
        return EntityModel.of(imageFile,
                              linkTo(methodOn(ImageFileController.class).getImage(imageFile.getId())).withSelfRel());
    }
}
