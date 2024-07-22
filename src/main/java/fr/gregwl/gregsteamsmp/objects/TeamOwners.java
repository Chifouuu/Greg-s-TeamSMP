package fr.gregwl.gregsteamsmp.objects;

import java.util.HashMap;
import java.util.UUID;

public class TeamOwners {

    HashMap<UUID, String> TeamsOwners = new HashMap<>();

    public TeamOwners(HashMap<UUID, String> teamsOwners) {
        TeamsOwners = teamsOwners;
    }

    public HashMap<UUID, String> getTeamsOwners() {
        return TeamsOwners;
    }



}
