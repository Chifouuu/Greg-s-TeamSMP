package fr.gregwl.gregsteamsmp.objects;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class Team {
    private String teamName;
    private int nbmembers;
    private UUID owner;
    private ArrayList<UUID> members;

    public Team(String teamName, int nbmembers, UUID owner, ArrayList<UUID> members) {
        this.teamName = teamName;
        this.nbmembers = nbmembers;
        this.owner = owner;
        this.members = members;
    }

    public String getTeamName() {
        return teamName;
    }

    public int getNbmembers() {
        return nbmembers;
    }

    public UUID getOwner() {
        return owner;
    }

    public ArrayList<UUID> getMembers() {
        return members;
    }
}
