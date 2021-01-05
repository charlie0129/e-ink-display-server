package com.einkdisplay.einkdisplayserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@RestController
public class HTTPRequestController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MessageRepository messageRepository;

    @PostMapping("/add-user")
    public @ResponseBody
    String addUser(@RequestParam(value = "name") String name, @RequestParam(value = "phone") long phone) {
        if (name.equals("") || phone == 0)
            return "Invalid field";
        User newUser = new User(name, phone);
        userRepository.save(newUser);
        return "User " + name + " with ID=" + newUser.getId() + " saved.";
    }

    @PostMapping("/add-message")
    public @ResponseBody
    String addMessage(@RequestParam(value = "userid") Long userID, @RequestParam(value = "message") String message) {
        Optional<User> referencedUser = userRepository.findById(userID);
        if (referencedUser.isPresent()) {
            Message newMessage = new Message(LocalDateTime.now(), message, referencedUser.get());
            messageRepository.save(newMessage);
            return "Message \"" + message + "\" with ID=" + newMessage.getId() + " saved to user " + userID + ".";
        } else
            return "Could find the user ID.";
    }

    @GetMapping("/get-message")
    public @ResponseBody
    ArrayList<Message> getMessage(@RequestParam(value = "userid", defaultValue = "0") Long userID) {
        Optional<User> referencedUser = userRepository.findById(userID);
        if (referencedUser.isPresent()) {
            return messageRepository.findByUserOrderByIdDesc(referencedUser.get());
        } else
            return null;
    }
}
