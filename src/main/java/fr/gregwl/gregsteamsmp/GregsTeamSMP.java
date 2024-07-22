package fr.gregwl.gregsteamsmp;

import fr.gregwl.gregsteamsmp.command.team.TeamCommand;
import fr.gregwl.gregsteamsmp.command.team.TeamTabCompletation;
import fr.gregwl.gregsteamsmp.files.TeamSerializationManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class GregsTeamSMP extends JavaPlugin {

    private static GregsTeamSMP instance;
    private TeamSerializationManager teamSerializationManager;
    public static String msgPrefix = ("§f[§1§lGreg's§b TeamSMP§f] ");

    @Override
    public void onEnable() {
        instance = this;
        this.teamSerializationManager = new TeamSerializationManager();

        getCommand("team").setExecutor(new TeamCommand());
        getCommand("team").setTabCompleter(new TeamTabCompletation());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static GregsTeamSMP getInstance() {
        return instance;
    }
}
