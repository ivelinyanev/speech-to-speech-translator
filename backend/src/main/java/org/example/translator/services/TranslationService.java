package org.example.translator.services;

import org.example.translator.clients.OpenAIClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class TranslationService {

    private final OpenAIClient openAIClient;

    @Autowired
    public TranslationService(OpenAIClient openAIClient) {
        this.openAIClient = openAIClient;
    }

    public String transcribe(MultipartFile file) throws IOException {
        return openAIClient.sendToWhisper(file);
    }

}
