package com.example.demo.Chat;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Map;

@Controller
public class WebSocketChatController {

    // Store active users and their sessions
    private Map<String, String> activeUsers = new HashMap<>();

    @MessageMapping("/chat.sendMessage/{roomId}")
    @SendTo("/topic/{roomId}")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage,
                                   @DestinationVariable("roomId") String roomId,
                                   @Header("simpSessionId") String sessionId) {
        // Handle incoming chat message
        // ...

        return chatMessage;
    }

    @MessageMapping("/chat.addUser/{roomId}")
    @SendTo("/topic/{roomId}")
    public ChatMessage addUser(@Payload ChatMessage chatMessage,
                               @DestinationVariable("roomId") String roomId,
                               @Header("simpSessionId") String sessionId) {
        // Add user to the chat room
        activeUsers.put(sessionId, chatMessage.getSender());

        chatMessage.setContent("User '" + chatMessage.getSender() + "' joined the chat.");
        chatMessage.setType(ChatMessage.MessageType.JOIN);
        return chatMessage;
    }

    @MessageMapping("/chat.editMessage/{roomId}/{messageId}")
    @SendTo("/topic/{roomId}")
    public ChatMessage editMessage(@Payload ChatMessage chatMessage,
                                   @DestinationVariable("roomId") String roomId,
                                   @DestinationVariable("messageId") String messageId,
                                   @Header("simpSessionId") String sessionId) {
        // Handle message editing
        // ...

        chatMessage.setContent("User '" + chatMessage.getSender() + "' edited the message: " + chatMessage.getContent());
        return chatMessage;
    }

    @MessageMapping("/chat.deleteMessage/{roomId}/{messageId}")
    @SendTo("/topic/{roomId}")
    public ChatMessage deleteMessage(@Payload ChatMessage chatMessage,
                                     @DestinationVariable("roomId") String roomId,
                                     @DestinationVariable("messageId") String messageId,
                                     @Header("simpSessionId") String sessionId) {
        // Handle message deletion
        // ...

        chatMessage.setContent("User '" + chatMessage.getSender() + "' deleted the message.");
        return chatMessage;
    }
}
