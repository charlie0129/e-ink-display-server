package com.einkdisplay.einkdisplayserver.model;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.einkdisplay.einkdisplayserver.controller.UserController;
import com.einkdisplay.einkdisplayserver.model.User;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class UserModelAssembler
        implements RepresentationModelAssembler<User, EntityModel<User>> {
    @Override
    public EntityModel<User> toModel(User user) {
        return EntityModel.of(user,
                              linkTo(methodOn(UserController.class).getUserSingle(user.getId()))
                                      .withSelfRel());
    }
}
