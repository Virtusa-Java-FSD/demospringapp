package com.revature.ecommerceapp.controller;


import com.revature.ecommerceapp.models.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @MessageMapping("/chat")
    @SendTo("/topic/chat")
    public ChatMessage sendMessage(ChatMessage message) {
        if (message.getType() == null) {
            message.setType(ChatMessage.MessageType.CHAT);
        }
        return message;
    }
}