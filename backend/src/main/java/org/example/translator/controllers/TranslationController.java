package org.example.translator.controllers;

import com.deepl.api.DeepLException;
import org.example.translator.services.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("api/translation")
@RestController
public class TranslationController {

    private final TranslationService service;

    @Autowired
    public TranslationController(TranslationService service) {
        this.service = service;
    }

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadAudio(@RequestParam("file") MultipartFile file) throws IOException, DeepLException, InterruptedException {
        String transcript = service.transcribe(file);
        String translatedTranscript = service.translate(transcript);

        Map<String, String> response = new HashMap<>();
        response.put("original", transcript);
        response.put("translated", translatedTranscript);

        return ResponseEntity.ok(response);
    }

}
