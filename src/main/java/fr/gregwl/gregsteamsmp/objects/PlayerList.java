package fr.gregwl.gregsteamsmp.objects;

import java.util.HashMap;
import java.util.UUID;

public class PlayerList {

    HashMap<UUID, String> playerList = new HashMap<>();

    public PlayerList(HashMap<UUID, String> playerList) {
        this.playerList = playerList;
    }

    public HashMap<UUID, String> getPlayerList() {
        return playerList;
    }
}

