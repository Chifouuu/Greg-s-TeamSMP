package fr.gregwl.gregsteamsmp.commands.teamsub.claims;

import fr.gregwl.gregsteamsmp.GregsTeamSMP;
import fr.gregwl.gregsteamsmp.files.ClaimSerializationManager;
import fr.gregwl.gregsteamsmp.files.FileUtils;
import fr.gregwl.gregsteamsmp.files.PlayerSerializationManager;
import fr.gregwl.gregsteamsmp.objects.Claim;
import fr.gregwl.gregsteamsmp.objects.PlayerList;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.List;

public class TeamUnclaim extends fr.gregwl.gregsteamsmp.commands.SubCommand {

    private Plugin plugin = GregsTeamSMP.getInstance();
    private File saveDir = new File(plugin.getDataFolder(), "/teams/");

    @Override
    public String getName() {
        return "unclaim";
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
        final File fileClaimsList = new File(saveDir, "claims.json");
        final ClaimSerializationManager claimSerializationManager = GregsTeamSMP.getInstance().getClaimSerializationManager();
        final String claimJsonExport = FileUtils.loadContent(fileClaimsList);
        final Claim claims = claimSerializationManager.deserialize(claimJsonExport);

        final File filePlayerList = new File(saveDir, "playerlist.json");
        final PlayerSerializationManager playerSerializationManager = GregsTeamSMP.getInstance().getPlayerSerializationManager();
        final String playerJsonExport = FileUtils.loadContent(filePlayerList);
        final PlayerList playerList = playerSerializationManager.deserialize(playerJsonExport);

        Chunk chunk = player.getLocation().getChunk();
        String chunkID = chunk.getX() + "," + chunk.getZ();

        String playerTeam = playerList.getPlayerList().get(player.getUniqueId());

        if(playerList.getPlayerList().containsKey(player.getUniqueId())) {
            if(claims.getChunks().containsKey(chunkID)) {
                String claimTeamOwner = claims.getChunks().get(chunkID);
                if(playerTeam.equals(claimTeamOwner)) {
                    claims.getChunks().remove(chunkID);
                    player.sendMessage(GregsTeamSMP.msgPrefix + "You unclaimed the chunk§1§l " + chunkID);

                    final String claimJson = claimSerializationManager.serialize(claims);
                    FileUtils.save(fileClaimsList, claimJson);
                }

            } else {
                player.sendMessage(GregsTeamSMP.msgPrefix + "This chunk isn't claimed !");
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
