package com.einkdisplay.einkdisplayserver;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {
    private final AtomicLong counter = new AtomicLong(0);
    private final UserMessage userMessage = new UserMessage();

    @GetMapping("/message")
    public UserMessage message(@RequestParam(value = "msg", defaultValue = "")String msg) {
        if(!msg.equals("")){
            userMessage.getUserMessages().put(counter.incrementAndGet(),msg);
        }
        return userMessage;
    }
}
