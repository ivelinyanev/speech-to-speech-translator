package org.example.translator.services;

import com.deepl.api.DeepLClient;
import com.deepl.api.DeepLException;
import com.deepl.api.TextResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.translator.clients.OpenAIClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.example.translator.helpers.StringConstants.*;

@PropertySource(APPLICATION_PROPERTIES_PATH)
@Service
public class TranslationService {

    private final OpenAIClient openAIClient;
    private final DeepLClient deepLClient;

    @Autowired
    public TranslationService(OpenAIClient openAIClient,
                              @Value(DEEPL_KEY) String deepLKey) {
        this.openAIClient = openAIClient;
        this.deepLClient = new DeepLClient(deepLKey);
    }

    public String transcribe(MultipartFile file) throws IOException {
        String whisperResponse = openAIClient.sendToWhisper(file);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(whisperResponse);

        return jsonNode.get("text").asText();
    }

    public String translate(String transcript) throws  InterruptedException, DeepLException {
        TextResult result = deepLClient.translateText(transcript, "EN", "BG");

        return result.getText();
    }

}
