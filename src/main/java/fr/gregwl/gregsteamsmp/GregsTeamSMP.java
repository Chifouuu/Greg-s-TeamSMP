package fr.gregwl.gregsteamsmp;

import fr.gregwl.gregsteamsmp.commands.TeamCommand;
import fr.gregwl.gregsteamsmp.files.PlayerSerializationManager;
import fr.gregwl.gregsteamsmp.files.TeamOwnersSerializationManager;
import fr.gregwl.gregsteamsmp.files.TeamSerializationManager;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class GregsTeamSMP extends JavaPlugin {

    private static GregsTeamSMP instance;
    private TeamSerializationManager teamSerializationManager;
    private TeamOwnersSerializationManager teamOwnersSerializationManager;
    private PlayerSerializationManager playerSerializationManager;
    public static String msgPrefix = ("§f[§1§lGreg's§b TeamSMP§f] ");

    @Override
    public void onEnable() {
        instance = this;
        this.teamSerializationManager = new TeamSerializationManager();
        this.teamOwnersSerializationManager = new TeamOwnersSerializationManager();
        this.playerSerializationManager = new PlayerSerializationManager();

        getCommand("team").setExecutor(new TeamCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
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
}
