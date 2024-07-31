package fr.gregwl.gregsteamsmp.claims;

import fr.gregwl.gregsteamsmp.GregsTeamSMP;
import fr.gregwl.gregsteamsmp.files.ClaimSerializationManager;
import fr.gregwl.gregsteamsmp.files.FileUtils;
import fr.gregwl.gregsteamsmp.files.PlayerSerializationManager;
import fr.gregwl.gregsteamsmp.objects.Claim;
import fr.gregwl.gregsteamsmp.objects.PlayerList;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class ClaimEventHandler implements Listener {
    private Plugin plugin = GregsTeamSMP.getInstance();
    private File saveDir = new File(plugin.getDataFolder(), "/teams/");

    @EventHandler
    public void onInterract(PlayerInteractEvent event) {

        if(event.getPlayer().isOp()) {

        } else {
            if(event.getClickedBlock() != null) {
                Chunk chunk = event.getClickedBlock().getChunk();
                String chunkID = chunk.getX() + "," + chunk.getZ();

                final File fileClaimsList = new File(saveDir, "claims.json");
                final ClaimSerializationManager claimSerializationManager = GregsTeamSMP.getInstance().getClaimSerializationManager();
                final String claimJsonExport = FileUtils.loadContent(fileClaimsList);
                final Claim claims = claimSerializationManager.deserialize(claimJsonExport);

                final File filePlayerList = new File(saveDir, "playerlist.json");
                final PlayerSerializationManager playerSerializationManager = GregsTeamSMP.getInstance().getPlayerSerializationManager();
                final String playerJsonExport = FileUtils.loadContent(filePlayerList);
                final PlayerList playerList = playerSerializationManager.deserialize(playerJsonExport);
                String claimTeamOwner = claims.getChunks().get(chunkID);
                if(claims.getChunks().containsKey(chunkID)) {
                    //verifie si c'est un chunk claim ou pas
                    if(playerList.getPlayerList().containsKey(event.getPlayer().getUniqueId())) {
                        // Si le joueur a une team
                        String playerTeam = playerList.getPlayerList().get(event.getPlayer().getUniqueId());
                        if(!(playerTeam.equals(claimTeamOwner))) {
                            event.setCancelled(true);
                            event.getPlayer().sendMessage(GregsTeamSMP.msgPrefix + "You are not allowed to build here. It's claimed by§1§l " + claimTeamOwner);

                        } else {
                        }
                    } else {
                        // si jamais le joueur n'as pas de team
                        event.setCancelled(true);
                        event.getPlayer().sendMessage(GregsTeamSMP.msgPrefix + "You are not allowed to build here. It's claimed by§1§l " + claimTeamOwner);
                    }
                }
                }
        }

    }

    @EventHandler
    public void onChunckEnter(PlayerMoveEvent event) {

        final File fileClaimsList = new File(saveDir, "claims.json");
        final ClaimSerializationManager claimSerializationManager = GregsTeamSMP.getInstance().getClaimSerializationManager();
        final String claimJsonExport = FileUtils.loadContent(fileClaimsList);
        final Claim claims = claimSerializationManager.deserialize(claimJsonExport);

        Player player = event.getPlayer();
        if(!(event.getFrom().getChunk().equals(event.getTo().getChunk()))){
            String chunkIDTo = event.getTo().getChunk().getX() + "," + event.getTo().getChunk().getZ();
            String chunkIDFrom = event.getFrom().getChunk().getX() + "," + event.getFrom().getChunk().getZ();

            String teamChunkTo = claims.getChunks().get(chunkIDTo);
            if(claims.getChunks().containsKey(chunkIDTo)) {
                if(claims.getChunks().containsKey(chunkIDFrom) && claims.getChunks().get(chunkIDFrom).equals(teamChunkTo)) {

                }else {
                    player.sendTitle("§4§lCaution !", "§fYou enter a chunk held by the§l " + teamChunkTo);
                }
            } else if(claims.getChunks().containsKey(chunkIDFrom) && !claims.getChunks().containsKey(chunkIDTo)) {
                player.sendTitle("§4§lCaution !", "§fYou enter in Wilderness");
            } else {

            }
        }
    }
}
