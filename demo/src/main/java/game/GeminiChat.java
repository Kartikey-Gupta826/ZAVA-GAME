package game;

import java.util.ArrayList;
import java.util.List;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import io.github.cdimascio.dotenv.Dotenv;

public class GeminiChat {

    static Dotenv dotenv = Dotenv.load();
    static Client client = Client.builder()
            .apiKey(dotenv.get("GOOGLE_API_KEY"))
            .build();

    // ── NARRATOR ──
    public static String narrate(String prompt) {
        String systemContext = """
                You are a dramatic RPG narrator.
                Speak in third person, dark and epic tone.
                2-3 sentences max. No bullet points. No markdown. No questions.
                Just vivid, punchy prose.

                Narrate this:
                """;

        GenerateContentResponse response =
                client.models.generateContent(
                        "gemini-3.1-flash-lite",
                        systemContext + prompt,
                        null);

        return response.text().trim();
    }

    // ── VILLAIN ──
    private static final List<String> villainHistory = new ArrayList<>();
    private static String currentVillainIntro = "";
    private static String currentPlayerName = "";

    // Call once at start of each encounter
    public static void resetVillain(String playerName) {
        villainHistory.clear();
        currentVillainIntro = "";
        currentPlayerName = playerName;
    }

    // First call — villain introduces itself
    public static String villainIntro() {
        String prompt = """
                You are a menacing RPG villain. Choose a dark, memorable name.
                You just encountered a hero named \s""" + currentPlayerName + """
                .
                Introduce yourself dramatically in 2 sentences.
                No bullet points. No markdown. Stay in character.
                """;

        GenerateContentResponse response =
                client.models.generateContent(
                        "gemini-3.1-flash-lite",
                        prompt,
                        null);

        currentVillainIntro = response.text().trim();
        villainHistory.add("Villain: " + currentVillainIntro);
        return currentVillainIntro;
    }

    // All subsequent calls — villain replies to player input
    public static String villainReply(String playerInput) {
        villainHistory.add("Hero: " + playerInput);

        String history = String.join("\n", villainHistory);

        String systemContext = """
                You are a menacing RPG villain. Your intro was:
                \s""" + currentVillainIntro + """

                Stay in character 100%. Speak with arrogance and dark wit.
                2 sentences MAX. No bullet points. No markdown. No options.
                Never break the fourth wall.

                Conversation so far:
                \s""" + history + """

                Your response:
                """;

        GenerateContentResponse response =
                client.models.generateContent(
                        "gemini-3.1-flash-lite",
                        systemContext,
                        null);

        String reply = response.text().trim();
        villainHistory.add("Villain: " + reply);
        return reply;
    }
}