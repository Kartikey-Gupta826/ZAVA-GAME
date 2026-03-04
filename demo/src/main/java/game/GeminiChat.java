package game;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import io.github.cdimascio.dotenv.Dotenv;  

public class GeminiChat {

    static Dotenv dotenv = Dotenv.load();  

    static Client client = Client.builder()
            .apiKey(dotenv.get("GOOGLE_API_KEY"))  
            .build();

    public static String chat(String responseString) {
        GenerateContentResponse response =
            client.models.generateContent(
                "gemini-3.1-flash-lite-preview",
                responseString,
                null);

        return response.text();
    }
}
