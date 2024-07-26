package fr.gregwl.gregsteamsmp.commands.teamsub.leave;

import fr.gregwl.gregsteamsmp.GregsTeamSMP;
import fr.gregwl.gregsteamsmp.commands.SubCommand;
import fr.gregwl.gregsteamsmp.files.FileUtils;
import fr.gregwl.gregsteamsmp.files.PlayerSerializationManager;
import fr.gregwl.gregsteamsmp.files.TeamOwnersSerializationManager;
import fr.gregwl.gregsteamsmp.files.TeamSerializationManager;
import fr.gregwl.gregsteamsmp.objects.PlayerList;
import fr.gregwl.gregsteamsmp.objects.Team;
import fr.gregwl.gregsteamsmp.objects.TeamOwners;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TeamLeave extends SubCommand {

    private Plugin plugin = GregsTeamSMP.getInstance();
    private File saveDir = new File(plugin.getDataFolder(), "/teams/");

    @Override
    public String getName() {
        return "leave";
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
        final File file1 = new File(saveDir, "teamsowners.json");
        final File filePlayerList = new File(saveDir, "playerlist.json");


        final TeamOwnersSerializationManager teamOwnersSerializationManager = GregsTeamSMP.getInstance().getTeamOwnersSerializationManager();
        final String ownerJsonExport = FileUtils.loadContent(file1);
        final TeamOwners teamOwners = teamOwnersSerializationManager.deserialize(ownerJsonExport);

        final PlayerSerializationManager playerSerializationManager = GregsTeamSMP.getInstance().getPlayerSerializationManager();
        final String playersJsonExport = FileUtils.loadContent(filePlayerList);
        final PlayerList playerList = playerSerializationManager.deserialize(playersJsonExport);

        if(playerList.getPlayerList().containsKey(player.getUniqueId())) {
            if(teamOwners.getTeamsOwners().containsKey(player.getUniqueId())) {
                player.sendMessage(GregsTeamSMP.msgPrefix + "You are the owner of the team. /team disband");
            } else {
                final String teamName = playerList.getPlayerList().get(player.getUniqueId());

                // MODIFICATION SUR LE JSON TEAM

                final File file = new File(saveDir, teamName + ".json");
                final TeamSerializationManager teamSerializationManager = GregsTeamSMP.getInstance().getTeamSerializationManager();
                final String teamJsonExport = FileUtils.loadContent(file);
                final Team team = teamSerializationManager.deserialize(teamJsonExport);

                team.getMembers().remove(player.getUniqueId());
                team.setNbmembers(team.getNbmembers() - 1);

                ArrayList<UUID> members = team.getMembers();

                final String json = teamSerializationManager.serialize(team);
                FileUtils.save(file, json);

                // MODIFITCATION SUR LE JSON DE PLAYERS

                playerList.getPlayerList().remove(player.getUniqueId());

                final String playersJson = playerSerializationManager.serialize(playerList);
                FileUtils.save(filePlayerList, playersJson);

                // MESSAGES

                for(int i = 0; i < members.size(); i++) {
                    Player currentPlayer = Bukkit.getPlayer(members.get(i));
                    currentPlayer.sendMessage(GregsTeamSMP.msgPrefix + "§1§l" + player.getName() + "§f leaved the team.");
                }
                player.sendMessage(GregsTeamSMP.msgPrefix + "You leaved the§1§l " + teamName + "§f team.");

                // END
            }
        } else {
            player.sendMessage(GregsTeamSMP.msgPrefix + "You aren't in a team !");
        }

    }

    @Override
    public List<String> getSubCommandArguments(Player player, String[] args) {
        return null;
    }
}
