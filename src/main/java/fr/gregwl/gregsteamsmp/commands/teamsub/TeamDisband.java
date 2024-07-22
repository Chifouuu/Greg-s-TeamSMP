package fr.gregwl.gregsteamsmp.commands.teamsub;

import fr.gregwl.gregsteamsmp.GregsTeamSMP;
import fr.gregwl.gregsteamsmp.files.FileUtils;
import fr.gregwl.gregsteamsmp.files.TeamOwnersSerializationManager;
import fr.gregwl.gregsteamsmp.objects.TeamOwners;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.List;

public class TeamDisband extends fr.gregwl.gregsteamsmp.commands.SubCommand {

    private Plugin plugin = GregsTeamSMP.getInstance();
    private File saveDir = new File(plugin.getDataFolder(), "/teams/");

    @Override
    public String getName() {
        return "disband";
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public String getSyntax() {
        return null;
    }

    @Override
    public void perform(Player player, String[] args) {
        if(args.length == 1) {
            final File file1 = new File(saveDir, "teamsowners.json");

            final TeamOwnersSerializationManager teamOwnersSerializationManager = GregsTeamSMP.getInstance().getTeamOwnersSerializationManager();
            final String ownerJsonExport = FileUtils.loadContent(file1);
            final TeamOwners teamOwners = teamOwnersSerializationManager.deserialize(ownerJsonExport);

            if(teamOwners.getTeamsOwners().containsKey(player.getUniqueId())) {
                String teamName = teamOwners.getTeamsOwners().get(player.getUniqueId());

                teamOwners.getTeamsOwners().remove(player.getUniqueId());
                final String json1 = teamOwnersSerializationManager.serialize(teamOwners);
                FileUtils.save(file1, json1);

                final File file = new File(saveDir, teamName + ".json");
                file.delete();

                player.sendMessage(GregsTeamSMP.msgPrefix + "The team§1§l " + teamName + "§f has been disbanded !");
                Bukkit.broadcastMessage(GregsTeamSMP.msgPrefix + "§1§l" + player.getName() + "§f disbanded the§1§l " + teamName + "§f team !");
            } else {
                player.sendMessage(GregsTeamSMP.msgPrefix + "Sorry, you can't do that !");
            }

        } else {
            player.sendMessage(GregsTeamSMP.msgPrefix + "(!) /team disband !");
        }
    }

    @Override
    public List<String> getSubCommandArguments(Player player, String[] args) {

        return null;
    }
}
