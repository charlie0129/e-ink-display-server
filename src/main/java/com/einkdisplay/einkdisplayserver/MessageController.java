package com.einkdisplay.einkdisplayserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Vector;

@RestController
public class MessageController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/add-user")
    public @ResponseBody String addUser (@RequestParam String name) {
        User newUser = new User(name);
        userRepository.save(newUser);
        return "User " + name + " with ID=" + newUser.getId() + " saved.";
    }

    @PostMapping("/add-message")
    public @ResponseBody String addMessage(@RequestParam Long id, @RequestParam String message) {
        Optional<User> referencedUser = userRepository.findById(id);
        if(referencedUser.isPresent()) {
            referencedUser.get().addMessage(message);
            return "Added message \""+message+ "\" to user " + id + ".";
        }
        else
            return "Could find the user ID.";
    }

    @GetMapping("/get-message")
    public @ResponseBody
    Vector<MessageBody> getMessage(@RequestParam(value = "id") long id, @RequestParam(value="which", defaultValue = "-1") int which) {
        Optional<User> referencedUser = userRepository.findById(id);
        if(referencedUser.isPresent() && which != 0) {
            if(which<0){
                return referencedUser.get().getMessages(which);
            } else {
                return referencedUser.get().getMessages(which);
            }
        }
        else
            return null;
    }
}
