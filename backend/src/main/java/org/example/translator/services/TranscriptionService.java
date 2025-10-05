package org.example.translator.services;

import com.openai.client.OpenAIClient;

import com.openai.models.audio.AudioModel;
import com.openai.models.audio.transcriptions.Transcription;
import com.openai.models.audio.transcriptions.TranscriptionCreateParams;
import com.openai.models.audio.transcriptions.TranscriptionCreateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;


@Service
public class TranscriptionService {

    private final OpenAIClient client;

    @Autowired
    public TranscriptionService(OpenAIClient client) {
        this.client = client;
    }

    public String transcribe(MultipartFile file) throws IOException {
        File tempFile = File.createTempFile("audio", ".m4a");
        file.transferTo(tempFile);

        TranscriptionCreateParams params = TranscriptionCreateParams.builder()
                .model(AudioModel.WHISPER_1)
                .file(tempFile.toPath())
                .build();

        Transcription transcription = client.audio()
                .transcriptions()
                .create(params)
                .asTranscription();


        tempFile.delete();

        return transcription.text();
    }

}
