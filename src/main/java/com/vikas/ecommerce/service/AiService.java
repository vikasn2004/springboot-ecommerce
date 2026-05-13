package com.vikas.ecommerce.service;

import jakarta.annotation.PostConstruct;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class AiService {

    private final ChatClient.Builder builder;
    private ChatClient chatClient;

    public AiService(ChatClient.Builder builder) {
        this.builder = builder;
    }

    @PostConstruct
    public void init() {
        System.out.println("hello");
        chatClient = builder
                .defaultSystem("""
                        You are an AI assistant for an e-commerce platform.

                        Only answer questions related to:
                        - Orders and order status
                        - Products and categories
                        - Returns and refunds
                        - Payments
                        - Account and login issues

                        Keep answers concise, friendly, and helpful.

                        If the question is unrelated to e-commerce,
                        politely say you can only help with shopping related questions.
                        """)
                .build();
    }
    public String askFAQ(String question) {
        return  chatClient.prompt().user(question).call().content();
    }
}