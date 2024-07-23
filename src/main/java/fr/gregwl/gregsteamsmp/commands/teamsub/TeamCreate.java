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
import org.apache.maven.model.Profile;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.checkerframework.checker.units.qual.A;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class TeamCreate extends SubCommand {

    private Plugin plugin = GregsTeamSMP.getInstance();
    private File saveDir = new File(plugin.getDataFolder(), "/teams/");



    @Override
    public String getName() {
        return "create";
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
            final String teamName = (args[1]);
            final UUID teamOwner = player.getUniqueId();
            final File file = new File(saveDir, teamName + ".json");
            final File file1 = new File(saveDir, "teamsowners.json");
            final File filePlayerList = new File(saveDir, "playerlist.json");

            if(file.exists()) {
                player.sendMessage(GregsTeamSMP.msgPrefix + "Sorry, this team already exists !");
            } else {
                ArrayList<UUID> members = new ArrayList<>();
                members.add(teamOwner);
                Team currentTeam = new Team(teamName, 1, teamOwner, members);
                final TeamSerializationManager teamSerializationManager = GregsTeamSMP.getInstance().getTeamSerializationManager();
                final String json = teamSerializationManager.serialize(currentTeam);

                FileUtils.save(file, json);
                player.sendMessage(GregsTeamSMP.msgPrefix + "The team§1§l " + teamName + "§f has been created !");
                Bukkit.broadcastMessage(GregsTeamSMP.msgPrefix + "§1§l" + player.getName() + "§f created the§1§l " + teamName + "§f team !");
                if(file1.exists()) {
                    final TeamOwnersSerializationManager teamOwnersSerializationManager = GregsTeamSMP.getInstance().getTeamOwnersSerializationManager();
                    final String ownerJsonExport = FileUtils.loadContent(file1);
                    final TeamOwners teamOwners = teamOwnersSerializationManager.deserialize(ownerJsonExport);

                    teamOwners.getTeamsOwners().put(teamOwner, teamName);

                    final String json1 = teamOwnersSerializationManager.serialize(teamOwners);

                    FileUtils.save(file1, json1);

                    // Player in team list
                    if(filePlayerList.exists()) {
                        final PlayerSerializationManager playerSerializationManager = GregsTeamSMP.getInstance().getPlayerSerializationManager();
                        final String playerJsonExport = FileUtils.loadContent(filePlayerList);
                        final PlayerList playerList = playerSerializationManager.deserialize(playerJsonExport);

                        playerList.getPlayerList().put(teamOwner, teamName);

                        final String jsonplayer  = playerSerializationManager.serialize(playerList);

                        FileUtils.save(filePlayerList, jsonplayer);
                    } else {
                        HashMap<UUID, String> hashmap = new HashMap<>();
                        hashmap.put(teamOwner, teamName);
                        PlayerList playerList = new PlayerList(hashmap);

                        final PlayerSerializationManager playerSerializationManager = GregsTeamSMP.getInstance().getPlayerSerializationManager();
                        final String jsonplayer = playerSerializationManager.serialize(playerList);

                        FileUtils.save(filePlayerList, jsonplayer);
                    }
                } else {
                    HashMap<UUID, String> hashMap = new HashMap<>();
                    hashMap.put(teamOwner, teamName);
                    TeamOwners teamOwners = new TeamOwners(hashMap);

                    final TeamOwnersSerializationManager teamOwnersSerializationManager = GregsTeamSMP.getInstance().getTeamOwnersSerializationManager();
                    final String json1 = teamOwnersSerializationManager.serialize(teamOwners);

                    FileUtils.save(file1, json1);

                    if(filePlayerList.exists()) {
                        final PlayerSerializationManager playerSerializationManager = GregsTeamSMP.getInstance().getPlayerSerializationManager();
                        final String playerJsonExport = FileUtils.loadContent(filePlayerList);
                        final PlayerList playerList = playerSerializationManager.deserialize(playerJsonExport);

                        playerList.getPlayerList().put(teamOwner, teamName);

                        final String jsonplayer  = playerSerializationManager.serialize(playerList);

                        FileUtils.save(filePlayerList, jsonplayer);
                    } else {
                        HashMap<UUID, String> hashmap = new HashMap<>();
                        hashmap.put(teamOwner, teamName);
                        PlayerList playerList = new PlayerList(hashmap);

                        final PlayerSerializationManager playerSerializationManager = GregsTeamSMP.getInstance().getPlayerSerializationManager();
                        final String jsonplayer = playerSerializationManager.serialize(playerList);

                        FileUtils.save(filePlayerList, jsonplayer);
                    }

                }


            }
        } else {
            player.sendMessage(GregsTeamSMP.msgPrefix + "(!) /team create <nameofteam> !");
        }
    }

    @Override
    public List<String> getSubCommandArguments(Player player, String[] args) {
        return null;

        // Ici l'auto-completation du tab avec la sous commande.

    }
}
