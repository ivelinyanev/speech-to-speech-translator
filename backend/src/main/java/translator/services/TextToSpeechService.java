package translator.services;

import com.openai.client.OpenAIClient;
import com.openai.models.audio.speech.SpeechCreateParams;
import com.openai.models.audio.speech.SpeechModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import com.openai.core.http.HttpResponse;

import java.io.IOException;

@Service
public class TextToSpeechService {

    private final OpenAIClient client;

    @Autowired
    public TextToSpeechService(OpenAIClient client) {
        this.client = client;
    }

    public byte[] generateAudio(String text) throws IOException {
        SpeechCreateParams params = SpeechCreateParams.builder()
                .model(SpeechModel.TTS_1)
                .voice(SpeechCreateParams.Voice.ASH)
                .input(text)
                .responseFormat(SpeechCreateParams.ResponseFormat.MP3)
                .build();

        HttpResponse response = client.audio().speech().create(params);

        return response.body().readAllBytes();
    }
}
