package com.hack.botinki.demo.bot;

import java.util.List;

import lombok.Data;

@Data
public class NewMessageBody {
    private String text;
    private List<Object> attachments;
    private Boolean notify = true;

}
