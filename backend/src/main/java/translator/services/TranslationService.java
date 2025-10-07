package translator.services;

import com.deepl.api.DeepLClient;
import com.deepl.api.DeepLException;
import com.deepl.api.TextResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import static translator.helpers.StringConstants.APPLICATION_PROPERTIES_PATH;
import static translator.helpers.StringConstants.DEEPL_KEY;

@PropertySource(APPLICATION_PROPERTIES_PATH)
@Service
public class TranslationService {

    private final DeepLClient deepLClient;

    public TranslationService(@Value(DEEPL_KEY) String deepLKey) {
        this.deepLClient = new DeepLClient(deepLKey);
    }

    public String translate(String transcript) throws  InterruptedException, DeepLException {
        TextResult result = deepLClient.translateText(transcript, "EN", "BG");

        return result.getText();
    }
}
