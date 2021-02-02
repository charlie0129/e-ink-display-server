package com.einkdisplay.einkdisplayserver;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

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
                              linkTo(methodOn(UserController.class).getMessageAllByUser(message.getUser().getId(), 0L))
                              .withRel("messagesFromUser"),
                              linkTo(methodOn(EInkDisplayController.class).getMessageAllByDisplay(message.getEInkDisplay().getId(), 0L))
                              .withRel("messagesFromDisplay"),
                              linkTo(methodOn(MessageController.class).getMessageAll())
                             .withRel("messages"));
    }
}
