package com.hack.botinki.demo.bot;

import lombok.Data;

@Data
public class InlineKeyboardAttachment {
    private String type;
    private Keyboard payload;
    
}
