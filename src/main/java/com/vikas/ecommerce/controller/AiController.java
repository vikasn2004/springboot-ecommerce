package com.vikas.ecommerce.controller;

import com.vikas.ecommerce.service.AiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/ecommerce/help")
@RequiredArgsConstructor
public class AiController {
    private final AiService aiService;

    @PostMapping("/faq")
    public ResponseEntity<Map<String,String>> faq(@RequestBody String question) {
        String answer = aiService.askFAQ(question);
        return ResponseEntity.ok(Map.of(
                "question", question,
                "answer", answer
        ));
    }
}
