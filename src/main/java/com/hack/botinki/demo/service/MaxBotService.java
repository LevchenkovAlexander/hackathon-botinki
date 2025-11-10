package com.hack.botinki.demo.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MaxBotService {
 
 @Value("${max.bot.token}")
 private String botToken;
 
 @Value("${max.api.url}")
 private String apiUrl;
 
 @Value("${mini.app.url}")
 private String miniAppUrl;
 
 private final RestTemplate restTemplate;
 
 public MaxBotService(RestTemplateBuilder restTemplateBuilder) {
     this.restTemplate = restTemplateBuilder.build();
 }
 
 public void sendMiniAppLink(Long chatId, Long userId) {
     String miniAppLink = miniAppUrl + userId;
     
     NewMessageBody message = createMessageWithMiniAppButton(chatId, miniAppLink);
     
     String url = apiUrl + "/messages?access_token=" + botToken + "&chat_id=" + chatId;
     
     try {
         SendMessageResult result = restTemplate.postForObject(url, message, SendMessageResult.class);
         System.out.println("Message sent: " + result.getMessage().getBody().getMid());
     } catch (Exception e) {
         System.err.println("Error sending message: " + e.getMessage());
     }
 }
 
 private NewMessageBody createMessageWithMiniAppButton(Long chatId, String miniAppLink) {
     NewMessageBody message = new NewMessageBody();
     message.setText("Нажмите на кнопку ниже, чтобы открыть мини-приложение:");
     
     // Создаем кнопку-ссылку
     LinkButton linkButton = new LinkButton();
     linkButton.setType("link");
     linkButton.setText("Открыть мини-приложение");
     linkButton.setUrl(miniAppLink);
     
     // Создаем клавиатуру с кнопкой
     Keyboard keyboard = new Keyboard();
     keyboard.setButtons(Arrays.asList(Arrays.asList(linkButton)));
     
     InlineKeyboardAttachment keyboardAttachment = new InlineKeyboardAttachment();
     keyboardAttachment.setType("inline_keyboard");
     keyboardAttachment.setPayload(keyboard);
     
     message.setAttachments(Arrays.asList(keyboardAttachment));
     
     return message;
 }
 
 // Для обработки callback кнопок (если нужно)
 public void answerCallback(String callbackId, String notification) {
     String url = apiUrl + "/answers?access_token=" + botToken + "&callback_id=" + callbackId;
     
     CallbackAnswer answer = new CallbackAnswer();
     answer.setNotification(notification);
     
     restTemplate.postForObject(url, answer, SimpleQueryResult.class);
 }
}
