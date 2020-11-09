package com.einkdisplay.einkdisplayserver;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {
//    private final AtomicLong counter = new AtomicLong(0);
    private final UserMessages userMessages = new UserMessages();

    @GetMapping("/message")
    public UserMessages message(@RequestParam(value = "msg", defaultValue = "")String msg) {
        if(!msg.equals("")){
            userMessages.addMessage(msg);
        }
        return userMessages;
    }
//Back end for BUPT ChuYan project "e-ink Display".
}
