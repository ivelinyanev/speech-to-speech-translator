package translator.controllers;

import com.deepl.api.DeepLException;
import translator.services.SpeechToTextService;
import translator.services.TextToSpeechService;
import translator.services.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequestMapping("api/translation")
@RestController
public class TranslationController {

    private final SpeechToTextService speechToTextService;
    private final TranslationService translationService;
    private final TextToSpeechService textToSpeechService;

    @Autowired
    public TranslationController(SpeechToTextService speechToTextService,
                                 TranslationService translationService,
                                 TextToSpeechService textToSpeechService) {
        this.speechToTextService = speechToTextService;
        this.translationService = translationService;
        this.textToSpeechService = textToSpeechService;
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/upload")
    public ResponseEntity<byte[]> uploadAudio(@RequestParam("file") MultipartFile file)
            throws IOException, DeepLException, InterruptedException {

        if (file.isEmpty()) {
            throw new IllegalArgumentException("Uploaded file is empty");
        }

        String transcript = speechToTextService.transcribe(file);
        String translatedTranscript = translationService.translate(transcript);

        byte[] audio = textToSpeechService.generateAudio(translatedTranscript);

        return ResponseEntity.ok(audio);
    }

}
