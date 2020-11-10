package com.einkdisplay.einkdisplayserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
//import java.util.Comparator;
import java.util.Optional;
//import java.util.Vector;

@RestController
public class MessageController {
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
            Message newMessage = new Message(LocalDateTime.now(), message, userID);
            messageRepository.save(newMessage);
            return "Message \"" + message + "\" with ID=" + newMessage.getId() + " saved to user " + userID + ".";
        } else
            return "Could find the user ID.";
    }

    @GetMapping("/get-message")
    public @ResponseBody
    ArrayList<Message> getMessage(@RequestParam(value = "which", defaultValue = "-1") int which, @RequestParam(value = "userid", defaultValue = "0") long userID) {
        Iterable<Message> allMessages = messageRepository.findAll();
        ArrayList<Message> messageArrayList = new ArrayList<>();
        for (Message i : allMessages) {
            if (userID == 0) {
                messageArrayList.add(i);
            } else {
                if (i.getUserID() == userID)
                    messageArrayList.add(i);
            }

        }
        messageArrayList.sort(Message::compareTo);
        ArrayList<Message> returnMessages = new ArrayList<>();
        if (messageArrayList.size() > 0) {
            if (which < 0) {
                for (int i = -1; i >= (Math.abs(which) <= messageArrayList.size() ? which : -messageArrayList.size()); i--)
                    returnMessages.add(messageArrayList.get(messageArrayList.size() + i));
            } else if (which > 0) {
                for (int i = 0; i < (which <= messageArrayList.size() ? which : messageArrayList.size()); i++)
                    returnMessages.add(messageArrayList.get(i));
            }
        }
        return returnMessages;
    }
}
