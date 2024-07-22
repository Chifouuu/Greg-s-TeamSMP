package fr.gregwl.gregsteamsmp.files;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.gregwl.gregsteamsmp.objects.TeamOwners;

public class TeamOwnersSerializationManager {

    private Gson gson;

    public TeamOwnersSerializationManager() {
        this.gson = createGsonInstance();
    }

    private Gson createGsonInstance() {
        return new GsonBuilder()
                .setPrettyPrinting()
                .serializeNulls()
                .disableHtmlEscaping()
                .create();
    }

    public String serialize(TeamOwners teamOwners) {
        return this.gson.toJson(teamOwners);
    }

    public TeamOwners deserialize(String json) {
        return this.gson.fromJson(json, TeamOwners.class);
    }
}
