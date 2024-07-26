package fr.gregwl.gregsteamsmp.files;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.gregwl.gregsteamsmp.objects.Claim;
import fr.gregwl.gregsteamsmp.objects.Team;

public class ClaimSerializationManager {

    private Gson gson;

    public ClaimSerializationManager() {
        this.gson = createGsonInstance();
    }

    private Gson createGsonInstance() {
        return new GsonBuilder()
                .setPrettyPrinting()
                .serializeNulls()
                .disableHtmlEscaping()
                .create();
    }

    public String serialize(Claim claim) {
        return this.gson.toJson(claim);
    }

    public Claim deserialize(String json) {
        return this.gson.fromJson(json, Claim.class);
    }
}
