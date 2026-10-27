package net.admingamer.mcsn.mcsnactionchat;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class PlayerManager {

    public static class PlayerProfile {
        public String nickname;
        public int colorRgb;

        public PlayerProfile(String nickname, int colorRgb) {
            this.nickname = nickname;
            this.colorRgb = colorRgb;
        }
    }

    public static Map<String, PlayerProfile> getPlayersDatabase() {
        return PLAYERS_DB;
    }

    private static final Map<String, PlayerProfile> PLAYERS_DB = new HashMap<>();

    private static final List<Component> CUSTOM_LOG = new ArrayList<>();
    private static final List<ActiveMessage> VISIBLE_MESSAGES = new ArrayList<>();
    private static final int MAX_HUD_LINES = 4;

    public static class ActiveMessage {
        public final Component text;
        public int ticksLeft;

        public ActiveMessage(Component text, int ticks) {
            this.text = text;
            this.ticksLeft = ticks;
        }
    }

    public static void addOrUpdatePlayer(String realUsername, String nickname, int rgbColor) {
        PLAYERS_DB.put(realUsername, new PlayerProfile(nickname, rgbColor));
    }

    public static String getDisplayName(String realUsername) {
        return PLAYERS_DB.containsKey(realUsername) ? PLAYERS_DB.get(realUsername).nickname : realUsername;
    }

    public static TextColor getPlayerColor(String realUsername) {
        return PLAYERS_DB.containsKey(realUsername) ?
                TextColor.fromRgb(PLAYERS_DB.get(realUsername).colorRgb) : TextColor.fromRgb(0xffffff);
    }

    public static void addCustomMessage(Component msg, int durationTicks) {
        CUSTOM_LOG.add(msg);
        VISIBLE_MESSAGES.add(new ActiveMessage(msg, durationTicks));
        if (VISIBLE_MESSAGES.size() > MAX_HUD_LINES) {
            VISIBLE_MESSAGES.remove(0);
        }
    }

    public static List<Component> getCustomLog() { return CUSTOM_LOG; }
    public static List<ActiveMessage> getVisibleMessages() { return VISIBLE_MESSAGES; }

    public static void tickHudMessages() {
        VISIBLE_MESSAGES.removeIf(msg -> {
            msg.ticksLeft--;
            return msg.ticksLeft <= 0;
        });
    }

    public static int hexToRgb(String hex) {
        if (!hex.startsWith("#")) hex = "#" + hex;
        try { return Integer.parseInt(hex.substring(1), 16) & 0xffffff; }
        catch (NumberFormatException e) { return 0xffffff; }
    }

    public static int hsvToRgb(float h, float s, float v) {
        return Color.HSBtoRGB(h / 360f, s / 100f, v / 100f) & 0xffffff;
    }
}
