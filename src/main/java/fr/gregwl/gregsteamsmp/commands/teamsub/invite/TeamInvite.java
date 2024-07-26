package fr.gregwl.gregsteamsmp.commands.teamsub.invite;

import fr.gregwl.gregsteamsmp.GregsTeamSMP;
import fr.gregwl.gregsteamsmp.files.FileUtils;
import fr.gregwl.gregsteamsmp.files.PlayerSerializationManager;
import fr.gregwl.gregsteamsmp.files.TeamOwnersSerializationManager;
import fr.gregwl.gregsteamsmp.objects.PlayerList;
import fr.gregwl.gregsteamsmp.objects.TeamOwners;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TeamInvite extends fr.gregwl.gregsteamsmp.commands.SubCommand {

    private Plugin plugin = GregsTeamSMP.getInstance();
    private File saveDir = new File(plugin.getDataFolder(), "/teams/");

    @Override
    public String getName() {
        return "invite";
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
        if(args.length == 2) {
            final UUID playerUUID = player.getUniqueId();
            final UUID targetplayerUUID = Bukkit.getPlayer(args[1]).getUniqueId();

            final File file1 = new File(saveDir, "teamsowners.json");
            final File filePlayerList = new File(saveDir, "playerlist.json");

            final TeamOwnersSerializationManager teamOwnersSerializationManager = GregsTeamSMP.getInstance().getTeamOwnersSerializationManager();
            final String ownerJsonExport = FileUtils.loadContent(file1);
            final TeamOwners teamOwners = teamOwnersSerializationManager.deserialize(ownerJsonExport);

            final PlayerSerializationManager playerSerializationManager = GregsTeamSMP.getInstance().getPlayerSerializationManager();
            final String playersJsonExport = FileUtils.loadContent(filePlayerList);
            final PlayerList playerList = playerSerializationManager.deserialize(playersJsonExport);

            if(playerList.getPlayerList().containsKey(playerUUID)) {
                if(teamOwners.getTeamsOwners().containsKey(playerUUID)){
                    if(playerList.getPlayerList().containsKey(targetplayerUUID)) {
                        player.sendMessage(GregsTeamSMP.msgPrefix + "the player you want to invite is already in a team !");
                    } else {
                        String teamName = teamOwners.getTeamsOwners().get(playerUUID);
                        if(GregsTeamSMP.invitedTeamPlayers.containsKey(targetplayerUUID)) {
                            GregsTeamSMP.invitedTeamPlayers.remove(targetplayerUUID);
                            GregsTeamSMP.invitedTeamPlayers.put(targetplayerUUID, teamName);
                            player.sendMessage(GregsTeamSMP.msgPrefix + "§1§l" + Bukkit.getPlayer(targetplayerUUID).getName() + "§f has been invited in your team.");
                            Bukkit.getPlayer(targetplayerUUID).sendMessage("You have been invited to the§1§l " + teamName + "§f team. Type§1§l /team join§f for join this team.");
                        } else {
                            GregsTeamSMP.invitedTeamPlayers.put(targetplayerUUID, teamName);
                            player.sendMessage(GregsTeamSMP.msgPrefix + "§1§l" + Bukkit.getPlayer(targetplayerUUID).getName() + "§f has been invited in your team.");
                            Bukkit.getPlayer(targetplayerUUID).sendMessage(GregsTeamSMP.msgPrefix + "You have been invited to the§1§l " + teamName + "§f team. Type§1§l /team join§f for join this team.");
                        }

                    }
                } else {
                    player.sendMessage(GregsTeamSMP.msgPrefix + "Sorry, you don't have permission to invite players !");
                }


            } else {
                player.sendMessage(GregsTeamSMP.msgPrefix + "Sorry, you aren't in a team !");
            }
        } else {
            player.sendMessage(GregsTeamSMP.msgPrefix + "(!) /team invite <playerName> !");
        }


    }

    @Override
    public List<String> getSubCommandArguments(Player player, String[] args) {
        if (args.length == 2){
            List<String> playerNames = new ArrayList<>();
            Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];
            Bukkit.getServer().getOnlinePlayers().toArray(players);
            for (int i = 0; i < players.length; i++){
                playerNames.add(players[i].getName());
            }

            return playerNames;
        }
        return null;

    }
}
