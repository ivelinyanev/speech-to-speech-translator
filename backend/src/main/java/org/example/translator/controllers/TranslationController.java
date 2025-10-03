package org.example.translator.controllers;

import org.example.translator.services.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequestMapping("api/translation")
@RestController
public class TranslationController {

    private final TranslationService service;

    @Autowired
    public TranslationController(TranslationService service) {
        this.service = service;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadAudio(@RequestParam("file") MultipartFile file) throws IOException {
        String textOutput = service.transcribe(file);

        return ResponseEntity.ok(textOutput);
    }
}
