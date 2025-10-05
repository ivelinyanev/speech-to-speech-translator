package org.example.translator.controllers;

import com.deepl.api.DeepLException;
import org.example.translator.services.TranscriptionService;
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

    private final TranscriptionService transcriptionService;
    private final TranslationService translationService;

    @Autowired
    public TranslationController(TranscriptionService transcriptionService,
                                 TranslationService translationService) {
        this.transcriptionService = transcriptionService;
        this.translationService = translationService;
    }

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadAudio(@RequestParam("file") MultipartFile file)
            throws IOException, DeepLException, InterruptedException {

        if (file.isEmpty()) {
            throw new IllegalArgumentException("Uploaded file is empty");
        }

        String transcript = transcriptionService.transcribe(file);
        String translatedTranscript = translationService.translate(transcript);

        Map<String, String> response = new HashMap<>();
        response.put("original", transcript);
        response.put("translated", translatedTranscript);

        return ResponseEntity.ok(response);
    }

}
