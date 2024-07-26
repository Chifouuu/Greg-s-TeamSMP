package fr.gregwl.gregsteamsmp.commands.teamsub;

import fr.gregwl.gregsteamsmp.GregsTeamSMP;
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
            final File filePlayerList = new File(saveDir, "playerlist.json");

            final TeamOwnersSerializationManager teamOwnersSerializationManager = GregsTeamSMP.getInstance().getTeamOwnersSerializationManager();
            final String ownerJsonExport = FileUtils.loadContent(file1);
            final TeamOwners teamOwners = teamOwnersSerializationManager.deserialize(ownerJsonExport);

            final PlayerSerializationManager playerSerializationManager = GregsTeamSMP.getInstance().getPlayerSerializationManager();
            final String playersJsonExport = FileUtils.loadContent(filePlayerList);
            final PlayerList playerList = playerSerializationManager.deserialize(playersJsonExport);

            String teamName = teamOwners.getTeamsOwners().get(player.getUniqueId());

            final File fileTeam = new File(saveDir, teamName + ".json");


            final TeamSerializationManager teamSerializationManager = GregsTeamSMP.getInstance().getTeamSerializationManager();
            final String TeamJsonExport = FileUtils.loadContent(fileTeam);
            final Team team = teamSerializationManager.deserialize(TeamJsonExport);

            if(teamOwners.getTeamsOwners().containsKey(player.getUniqueId())) {


                teamOwners.getTeamsOwners().remove(player.getUniqueId());
                final String json1 = teamOwnersSerializationManager.serialize(teamOwners);
                FileUtils.save(file1, json1);

                playerList.getPlayerList().remove(player.getUniqueId());


                player.sendMessage(GregsTeamSMP.msgPrefix + "The team§1§l " + teamName + "§f has been disbanded !");
                Bukkit.broadcastMessage(GregsTeamSMP.msgPrefix + "§1§l" + player.getName() + "§f disbanded the§1§l " + teamName + "§f team !");

                ArrayList<UUID> members = team.getMembers();



                for(int i = 0; i < members.size(); i++) {
                    Player currentPlayer = Bukkit.getPlayer(members.get(i));
                    playerList.getPlayerList().remove(currentPlayer.getUniqueId());
                }

                final String playerJson = playerSerializationManager.serialize(playerList);
                FileUtils.save(filePlayerList, playerJson);

                fileTeam.delete();
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
