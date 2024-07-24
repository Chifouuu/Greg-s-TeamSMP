package fr.gregwl.gregsteamsmp.commands.teamsub;

import fr.gregwl.gregsteamsmp.GregsTeamSMP;
import fr.gregwl.gregsteamsmp.files.FileUtils;
import fr.gregwl.gregsteamsmp.files.PlayerSerializationManager;
import fr.gregwl.gregsteamsmp.files.TeamSerializationManager;
import fr.gregwl.gregsteamsmp.objects.PlayerList;
import fr.gregwl.gregsteamsmp.objects.Team;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class TeamInfo extends fr.gregwl.gregsteamsmp.commands.SubCommand {

    private Plugin plugin = GregsTeamSMP.getInstance();
    private File saveDir = new File(plugin.getDataFolder(), "/teams/");

    @Override
    public String getName() {
        return "info";
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

        final UUID playerUUID = player.getUniqueId();

        final File filePlayerList = new File(saveDir, "playerlist.json");

        final PlayerSerializationManager playerSerializationManager = GregsTeamSMP.getInstance().getPlayerSerializationManager();
        final String playerJsonExport = FileUtils.loadContent(filePlayerList);
        final PlayerList playerList = playerSerializationManager.deserialize(playerJsonExport);

        if(playerList.getPlayerList().containsKey(playerUUID)) {
            String teamName = playerList.getPlayerList().get(playerUUID);
            final File fileTeam = new File(saveDir, teamName + ".json");

            final TeamSerializationManager teamSerializationManager = GregsTeamSMP.getInstance().getTeamSerializationManager();
            final String TeamJsonExport = FileUtils.loadContent(fileTeam);
            final Team team = teamSerializationManager.deserialize(TeamJsonExport);

            String teamOwnerName = Bukkit.getOfflinePlayer(team.getOwner()).getName();
            int nbMembres = team.getNbmembers();
            ArrayList<UUID> members = team.getMembers();
            ArrayList<String> membersNames = new ArrayList<>();
            for(int i = 0; i < members.size(); i++) {
                membersNames.add(Bukkit.getOfflinePlayer(members.get(i)).getName());
            }

            player.sendMessage("§7§l---------");
            player.sendMessage(GregsTeamSMP.msgPrefix + "Team Information:");
            player.sendMessage("§1§lName§7:§f " + teamName);
            player.sendMessage("§1§lOwner§7:§f " + teamOwnerName);
            player.sendMessage("§1§lMembers§7:§f " + nbMembres);
            player.sendMessage("§1§lList of members§7:§f " + membersNames);
            player.sendMessage("§7§l---------");

        } else {
            player.sendMessage(GregsTeamSMP.msgPrefix + "Sorry. You are not in a team !");
        }
    }

    @Override
    public List<String> getSubCommandArguments(Player player, String[] args) {
        return null;
    }
}
