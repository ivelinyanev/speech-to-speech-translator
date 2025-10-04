package org.example.translator.helpers;

public class StringConstants {

    public static final String APPLICATION_PROPERTIES_PATH = "classpath:application.properties";

    // WHISPER

    public static final String AI_MODEL = "whisper-1";
    public static final String WHISPER_KEY = "${open.ai.key}";
    public static final String LANG_PARAM = "en";

    public static final String IO_FILE_PROCESS_ERROR = "An I/O error occurred while processing the file: ";
    public static final String IO_LOG_ERROR = "I/O error";
    public static final String WHISPER_CALL_FAIL_ERROR = "Failed to call Whisper API";

    // DEEPL

    public static final String DEEPL_KEY = "${deepl.key}";
    public static final String DEEPL_ERROR = "DeepL error";

    public static final String INTERRUPTION_ERROR = "Interruption error";
    public static final String TEXT_TRANSLATION_ERROR_MESSAGE = "An interruption occurred while translating the text";
    public static final String DEEPL_STANDARD_ERROR_MESSAGE = "An error occurred with DeepL";

}
