package net.admingamer.mcsn.mcsnactionchat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ChatConfigSaver {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static File configFile;

    private static void initFile() {
        if (configFile == null) {
            // Puts the file right inside the standard .minecraft/config/ folder
            configFile = new File(FMLPaths.CONFIGDIR.get().toFile(), "mcsnactionchat.json");
        }
    }

    // Call this inside FriendConfigScreen when hitting "Save Settings"
    public static void saveConfig() {
        initFile();
        JsonObject root = new JsonObject();
        JsonObject friends = new JsonObject();

        // Serialize all your friend profile properties into JSON objects
        FriendManager.getFriendsDatabase().forEach((username, profile) -> {
            JsonObject friendData = new JsonObject();
            friendData.addProperty("nickname", profile.nickname);
            friendData.addProperty("colorRgb", profile.colorRgb);
            friends.add(username, friendData);
        });

        root.add("friends", friends);

        try (FileWriter writer = new FileWriter(configFile)) {
            GSON.toJson(root, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Call this when the game starts up to restore all saved profile files
    public static void loadConfig() {
        initFile();
        if (!configFile.exists()) {
            saveConfig(); // Create a blank template file if it's missing
            return;
        }

        try (FileReader reader = new FileReader(configFile)) {
            JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();
            if (root.has("friends")) {
                JsonObject friends = root.getAsJsonObject("friends");
                friends.entrySet().forEach(entry -> {
                    String username = entry.getKey();
                    JsonObject data = entry.getValue().getAsJsonObject();
                    String nickname = data.get("nickname").getAsString();
                    int colorRgb = data.get("colorRgb").getAsInt();

                    // Inject directly back into your runtime game profile system map
                    FriendManager.addOrUpdateFriend(username, nickname, colorRgb);
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
