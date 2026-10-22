package net.admingamer.mcsn.mcsnactionchat;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class FriendManager {

    public static class FriendProfile {
        public String nickname;
        public int colorRgb;

        public FriendProfile(String nickname, int colorRgb) {
            this.nickname = nickname;
            this.colorRgb = colorRgb;
        }
    }

    // Add this helper method inside your FriendManager class
    public static Map<String, FriendProfile> getFriendsDatabase() {
        return FRIENDS_DB;
    }

    // Maps: Real Username -> Friend Profile Configuration
    private static final Map<String, FriendProfile> FRIENDS_DB = new HashMap<>();

    // Core custom chat system log stores
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

    public static void addOrUpdateFriend(String realUsername, String nickname, int rgbColor) {
        FRIENDS_DB.put(realUsername, new FriendProfile(nickname, rgbColor));
    }

    public static String getDisplayName(String realUsername) {
        return FRIENDS_DB.containsKey(realUsername) ? FRIENDS_DB.get(realUsername).nickname : realUsername;
    }

    public static TextColor getFriendColor(String realUsername) {
        return FRIENDS_DB.containsKey(realUsername) ?
                TextColor.fromRgb(FRIENDS_DB.get(realUsername).colorRgb) : TextColor.fromRgb(0xffffff);
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

    // --- Color Converters ---
    public static int hexToRgb(String hex) {
        if (!hex.startsWith("#")) hex = "#" + hex;
        try { return Integer.parseInt(hex.substring(1), 16) & 0xffffff; }
        catch (NumberFormatException e) { return 0xffffff; }
    }

    public static int hsvToRgb(float h, float s, float v) {
        return Color.HSBtoRGB(h / 360f, s / 100f, v / 100f) & 0xffffff;
    }
}
