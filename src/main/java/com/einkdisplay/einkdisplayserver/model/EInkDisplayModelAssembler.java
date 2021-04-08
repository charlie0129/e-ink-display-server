package com.einkdisplay.einkdisplayserver.model;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.einkdisplay.einkdisplayserver.controller.EInkDisplayController;
import com.einkdisplay.einkdisplayserver.model.EInkDisplay;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class
EInkDisplayModelAssembler
        implements RepresentationModelAssembler<EInkDisplay, EntityModel<EInkDisplay>> {
    @Override
    public EntityModel<EInkDisplay> toModel(EInkDisplay display) {
        return EntityModel.of(display,
                              linkTo(methodOn(EInkDisplayController.class).getDisplaySingle(display.getId()))
                                      .withSelfRel(),
                              linkTo(methodOn(EInkDisplayController.class).getDisplayAll())
                                      .withRel("displays"));
    }
}
