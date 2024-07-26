package fr.gregwl.gregsteamsmp.commands.teamsub;

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
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TeamKick extends SubCommand {

    private Plugin plugin = GregsTeamSMP.getInstance();
    private File saveDir = new File(plugin.getDataFolder(), "/teams/");

    @Override
    public String getName() {
        return "kick";
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
        if (args.length == 2) {
            final File file1 = new File(saveDir, "teamsowners.json");
            final File filePlayerList = new File(saveDir, "playerlist.json");


            final TeamOwnersSerializationManager teamOwnersSerializationManager = GregsTeamSMP.getInstance().getTeamOwnersSerializationManager();
            final String ownerJsonExport = FileUtils.loadContent(file1);
            final TeamOwners teamOwners = teamOwnersSerializationManager.deserialize(ownerJsonExport);

            final PlayerSerializationManager playerSerializationManager = GregsTeamSMP.getInstance().getPlayerSerializationManager();
            final String playersJsonExport = FileUtils.loadContent(filePlayerList);
            final PlayerList playerList = playerSerializationManager.deserialize(playersJsonExport);



            if (teamOwners.getTeamsOwners().containsKey(player.getUniqueId())) {

                final UUID targetplayerUUID = Bukkit.getPlayer(args[1]).getUniqueId();

                String teamName = teamOwners.getTeamsOwners().get(player.getUniqueId());
                final File fileTeam = new File(saveDir, teamName + ".json");
                final TeamSerializationManager teamSerializationManager = GregsTeamSMP.getInstance().getTeamSerializationManager();
                final String TeamJsonExport = FileUtils.loadContent(fileTeam);
                final Team team = teamSerializationManager.deserialize(TeamJsonExport);
                if(team.getMembers().contains(targetplayerUUID)) {
                    if(targetplayerUUID == player.getUniqueId()) {
                        player.sendMessage(GregsTeamSMP.msgPrefix + "Sorry, you can't kick yourself !");
                    } else {
                        team.getMembers().remove(targetplayerUUID);
                        team.setNbmembers(team.getNbmembers() - 1);

                        final String json = teamSerializationManager.serialize(team);

                        FileUtils.save(fileTeam, json);

                        // remove from the player list

                        playerList.getPlayerList().remove(targetplayerUUID);

                        final String jsonplayer  = playerSerializationManager.serialize(playerList);
                        FileUtils.save(filePlayerList, jsonplayer);

                        ArrayList<UUID> members = team.getMembers();

                        for(int i = 0; i < members.size(); i++) {
                            Player currentPlayer = Bukkit.getPlayer(members.get(i));
                            currentPlayer.sendMessage(GregsTeamSMP.msgPrefix + "§1§l" + Bukkit.getOfflinePlayer(targetplayerUUID).getName() + "§f was kicked from the team !");
                        }

                        if(Bukkit.getOfflinePlayer(targetplayerUUID).isOnline()) {
                            Bukkit.getPlayer(targetplayerUUID).sendMessage(GregsTeamSMP.msgPrefix + "You have been kicked from the§1§l " + teamName +"§f team");
                        }
                    }
                } else {
                    player.sendMessage(GregsTeamSMP.msgPrefix + "Sorry this player isn't in your team !");
                }

            } else {
                player.sendMessage(GregsTeamSMP.msgPrefix + "Sorry you can't do that.");
            }
        } else {
            player.sendMessage(GregsTeamSMP.msgPrefix + "(!) /team kick <playerName> !");
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
