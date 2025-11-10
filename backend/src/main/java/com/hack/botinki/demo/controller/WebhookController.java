package com.hack.botinki.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hack.botinki.demo.bot.Message;
import com.hack.botinki.demo.bot.Update;
import com.hack.botinki.demo.service.MaxBotService;

@RestController
@RequestMapping("/webhook")
public class WebhookController {
 
	private final MaxBotService botService;
 
	public WebhookController(MaxBotService botService) {
		this.botService = botService;
	}
 
	@PostMapping
	public ResponseEntity<String> handleWebhook(@RequestBody Update update) {
		System.out.println("Received update: " + update.getUpdateType());
 
		switch (update.getUpdateType()) {
		case "message_created":
			handleMessageCreated(update);
			break;
		case "bot_started":
			handleBotStarted(update);
			break;
		case "message_callback":
			handleMessageCallback(update);
			break;
		}
 
		return ResponseEntity.ok("OK");
	}
 
	private void handleMessageCreated(Update update) {
		Message message = update.getMessage();
		if (message != null && message.getBody() != null) {
			String text = message.getBody().getText();
			Long chatId = message.getRecipient().getChatId();
			Long userId = message.getSender().getUserId();
         
			if ("/start".equals(text) || "мини приложение".equalsIgnoreCase(text)) {
				botService.sendMiniAppLink(chatId, userId);
			}
		}
	}
 
	private void handleBotStarted(Update update) {
		Long chatId = update.getChatId();
		Long userId = update.getUser().getUserId();
     
     // Отправляем ссылку на мини-приложение при старте бота
		botService.sendMiniAppLink(chatId, userId);
	}
 
	private void handleMessageCallback(Update update) {
		// Обработка нажатий на кнопки (если нужно)
		System.out.println("Callback received from user: " + update.getUser().getUserId());
	}
}