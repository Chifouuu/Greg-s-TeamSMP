package fr.gregwl.gregsteamsmp.files;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.gregwl.gregsteamsmp.objects.PlayerList;
import fr.gregwl.gregsteamsmp.objects.TeamOwners;

public class PlayerSerializationManager {
    private Gson gson;

    public PlayerSerializationManager() {
        this.gson = createGsonInstance();
    }

    private Gson createGsonInstance() {
        return new GsonBuilder()
                .setPrettyPrinting()
                .serializeNulls()
                .disableHtmlEscaping()
                .create();
    }

    public String serialize(PlayerList playerList) {
        return this.gson.toJson(playerList);
    }

    public PlayerList deserialize(String json) {
        return this.gson.fromJson(json, PlayerList.class);
    }
}
