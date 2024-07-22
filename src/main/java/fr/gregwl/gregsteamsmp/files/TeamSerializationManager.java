package fr.gregwl.gregsteamsmp.files;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.gregwl.gregsteamsmp.objects.Team;

public class TeamSerializationManager {

    private Gson gson;

    public TeamSerializationManager() {
        this.gson = createGsonInstance();
    }

    private Gson createGsonInstance() {
        return new GsonBuilder()
                .setPrettyPrinting()
                .serializeNulls()
                .disableHtmlEscaping()
                .create();
    }

    public String serialize(Team team) {
        return this.gson.toJson(team);
    }

    public Team deserialize(String json) {
        return this.gson.fromJson(json, Team.class);
    }
}
