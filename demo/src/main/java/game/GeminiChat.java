package game;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import io.github.cdimascio.dotenv.Dotenv;  // ✅ add this import

public class GeminiChat {

    static Dotenv dotenv = Dotenv.load();  // ✅ loads .env file

    static Client client = Client.builder()
            .apiKey(dotenv.get("GOOGLE_API_KEY"))  // ✅ reads from .env
            .build();

    public static String chat(String responseString) {
        GenerateContentResponse response =
            client.models.generateContent(
                "gemini-3-flash-preview",
                responseString,
                null);

        return response.text();
    }
}
