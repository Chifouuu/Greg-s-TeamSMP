package fr.gregwl.gregsteamsmp.commands.teamsub;

import fr.gregwl.gregsteamsmp.GregsTeamSMP;
import fr.gregwl.gregsteamsmp.files.FileUtils;
import fr.gregwl.gregsteamsmp.files.PlayerSerializationManager;
import fr.gregwl.gregsteamsmp.files.TeamSerializationManager;
import fr.gregwl.gregsteamsmp.objects.PlayerList;
import fr.gregwl.gregsteamsmp.objects.Team;
import org.bukkit.Bukkit;
import org.bukkit.GameEvent;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TeamJoin extends fr.gregwl.gregsteamsmp.commands.SubCommand {

    private Plugin plugin = GregsTeamSMP.getInstance();
    private File saveDir = new File(plugin.getDataFolder(), "/teams/");

    @Override
    public String getName() {
        return "join";
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
        if(GregsTeamSMP.invitedTeamPlayers.containsKey(player.getUniqueId())) {
            String teamName = GregsTeamSMP.invitedTeamPlayers.get(player.getUniqueId());

            final File filePlayerList = new File(saveDir, "playerlist.json");
            final File fileTeam = new File(saveDir, teamName + ".json");

            GregsTeamSMP.invitedTeamPlayers.remove(player.getUniqueId());

            final PlayerSerializationManager playerSerializationManager = GregsTeamSMP.getInstance().getPlayerSerializationManager();
            final String playersJsonExport = FileUtils.loadContent(filePlayerList);
            final PlayerList playerList = playerSerializationManager.deserialize(playersJsonExport);

            playerList.getPlayerList().put(player.getUniqueId(), teamName);


            // save file players list with ther own teams
            final String jsonplayer  = playerSerializationManager.serialize(playerList);
            FileUtils.save(filePlayerList, jsonplayer);

            final TeamSerializationManager teamSerializationManager = GregsTeamSMP.getInstance().getTeamSerializationManager();
            final String TeamJsonExport = FileUtils.loadContent(fileTeam);
            final Team team = teamSerializationManager.deserialize(TeamJsonExport);

            team.getMembers().add(player.getUniqueId());
            team.setNbmembers(team.getNbmembers() + 1);
            ArrayList<UUID> members = team.getMembers();

            // save file team
            final String json = teamSerializationManager.serialize(team);
            FileUtils.save(fileTeam, json);

            for(int i = 0; i < members.size(); i++) {
                Player currentPlayer = Bukkit.getPlayer(members.get(i));
                currentPlayer.sendMessage(GregsTeamSMP.msgPrefix + "§1§l" + player.getName() + "§f joined the team.");
            }



        } else {
            player.sendMessage("Sorry, you don't have been invited in any team !");
        }
    }

    @Override
    public List<String> getSubCommandArguments(Player player, String[] args) {
        return null;
    }
}
