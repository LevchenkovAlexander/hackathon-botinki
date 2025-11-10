package com.hack.botinki.demo.bot;

import lombok.Data;

@Data
public class Message {
    private Sender sender;
    private Recipient recipient;
    private Long timestamp;
    private MessageBody body;
    
}