package com.hack.botinki.demo.bot;

import lombok.Data;

@Data
public class Sender {
    private Long userId;
    private String firstName;
    private String lastName;
    private String username;
    private Boolean isBot;
    
}
