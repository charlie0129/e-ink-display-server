package com.einkdisplay.einkdisplayserver.model;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.einkdisplay.einkdisplayserver.controller.EInkDisplayController;
import com.einkdisplay.einkdisplayserver.controller.ImageFileController;
import com.einkdisplay.einkdisplayserver.controller.MessageController;
import com.einkdisplay.einkdisplayserver.controller.UserController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class MessageModelAssembler
        implements RepresentationModelAssembler<Message, EntityModel<Message>> {
    @Override
    public EntityModel<Message> toModel(Message message) {
        return EntityModel.of(message,
                              linkTo(methodOn(MessageController.class).getMessageSingle(message.getId()))
                                      .withSelfRel(),
                              linkTo(methodOn(ImageFileController.class).getImageAllByMessage(message.getId()))
                                      .withRel("images"),
                              linkTo(methodOn(UserController.class).getMessageAllByUser(message.getUser().getId(), 0L))
                                      .withRel("messagesFromUser"),
                              linkTo(methodOn(EInkDisplayController.class).getMessageAllByDisplay(message.getEInkDisplay().getId(), 0L))
                                      .withRel("messagesFromDisplay"),
                              linkTo(methodOn(MessageController.class).getMessageAll())
                                      .withRel("messages"));
    }
}
