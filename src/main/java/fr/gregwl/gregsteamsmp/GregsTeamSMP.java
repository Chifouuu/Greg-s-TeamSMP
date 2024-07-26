package fr.gregwl.gregsteamsmp;

import fr.gregwl.gregsteamsmp.claims.ClaimEventHandler;
import fr.gregwl.gregsteamsmp.commands.TeamCommand;
import fr.gregwl.gregsteamsmp.files.*;
import fr.gregwl.gregsteamsmp.objects.Claim;
import fr.gregwl.gregsteamsmp.objects.PlayerList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

public final class GregsTeamSMP extends JavaPlugin {

    private static GregsTeamSMP instance;
    private TeamSerializationManager teamSerializationManager;
    private TeamOwnersSerializationManager teamOwnersSerializationManager;
    private PlayerSerializationManager playerSerializationManager;
    private ClaimSerializationManager claimSerializationManager;
    public static String msgPrefix = ("§f[§1§lGreg's§b TeamSMP§f] ");
    public static HashMap<UUID, String> invitedTeamPlayers = new HashMap<>();

    @Override
    public void onEnable() {
        invitedTeamPlayers.clear();
        instance = this;
        this.teamSerializationManager = new TeamSerializationManager();
        this.teamOwnersSerializationManager = new TeamOwnersSerializationManager();
        this.playerSerializationManager = new PlayerSerializationManager();
        this.claimSerializationManager = new ClaimSerializationManager();
        File saveDir = new File(this.getDataFolder(), "/teams/");

        getCommand("team").setExecutor(new TeamCommand());
        Bukkit.getPluginManager().registerEvents(new ClaimEventHandler(), this);

        final File filePlayerList = new File(saveDir, "playerlist.json");
        if(!filePlayerList.exists()) {
            HashMap<UUID, String> hashmap = new HashMap<>();
            PlayerList playerList = new PlayerList(hashmap);

            final PlayerSerializationManager playerSerializationManager = GregsTeamSMP.getInstance().getPlayerSerializationManager();
            final String jsonplayer = playerSerializationManager.serialize(playerList);

            FileUtils.save(filePlayerList, jsonplayer);
        }

        final File fileClaimsList = new File(saveDir, "claims.json");
        if(!fileClaimsList.exists()) {
            HashMap<String, String> hashMap = new HashMap<>();
            Claim claim = new Claim(hashMap);

            final ClaimSerializationManager claimSerializationManager = GregsTeamSMP.getInstance().getClaimSerializationManager();
            final String jsonClaims = claimSerializationManager.serialize(claim);

            FileUtils.save(fileClaimsList, jsonClaims);
        }
    }

    @Override
    public void onDisable() {
        invitedTeamPlayers.clear();
    }

    public static GregsTeamSMP getInstance() {
        return instance;
    }

    public TeamSerializationManager getTeamSerializationManager() {
        return teamSerializationManager;
    }

    public TeamOwnersSerializationManager getTeamOwnersSerializationManager() {
        return teamOwnersSerializationManager;
    }

    public PlayerSerializationManager getPlayerSerializationManager() {
        return playerSerializationManager;
    }

    public ClaimSerializationManager getClaimSerializationManager() {
        return claimSerializationManager;
    }
}
