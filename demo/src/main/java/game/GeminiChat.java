package game;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;

public class GeminiChat {

    // obtain the API key from the environment so we don't hard‑code secrets
// and avoid a missing-symbol compile error
static Client client = Client.builder()
        .apiKey(System.getenv("GOOGLE_API_KEY"))
        .build();

    public static void chat(String responseString) {
        GenerateContentResponse response =
            client.models.generateContent(
                "gemini-3-flash-preview",
                responseString,
                null);

        System.out.println(response.text());
    }
}