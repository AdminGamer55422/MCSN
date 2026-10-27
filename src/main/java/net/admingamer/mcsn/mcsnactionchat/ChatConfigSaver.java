package net.admingamer.mcsn.mcsnactionchat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
            configFile = new File(FMLPaths.CONFIGDIR.get().toFile(), "mcsnactionchat.json");
        }
    }

    public static void saveConfig() {
        initFile();
        JsonObject root = new JsonObject();
        JsonObject friends = new JsonObject();

        PlayerManager.getPlayersDatabase().forEach((username, profile) -> {
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

    public static void loadConfig() {
        initFile();
        if (!configFile.exists()) {
            saveConfig();
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

                    PlayerManager.addOrUpdatePlayer(username, nickname, colorRgb);
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
