package fr.gregwl.gregsteamsmp.commands.teamsub;

import org.bukkit.Chunk;
import org.bukkit.entity.Player;

import java.util.List;

public class TeamClaim extends fr.gregwl.gregsteamsmp.commands.SubCommand {
    @Override
    public String getName() {
        return "claim";
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
        Chunk chunk = player.getLocation().getChunk();

        String chunkID = chunk.getX() + "," + chunk.getZ();
    }

    @Override
    public List<String> getSubCommandArguments(Player player, String[] args) {
        return null;
    }
}
