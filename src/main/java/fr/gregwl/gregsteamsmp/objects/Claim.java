package fr.gregwl.gregsteamsmp.objects;

import java.util.HashMap;

public class Claim {

    // Claim / TeamName
    private HashMap<String, String> chunks;

    public Claim(HashMap<String, String> chunks) {
        this.chunks = chunks;
    }

    public HashMap<String, String> getChunks() {
        return chunks;
    }
}
