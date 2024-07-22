package fr.gregwl.gregsteamsmp.commands.teamsub;

import fr.gregwl.gregsteamsmp.GregsTeamSMP;
import fr.gregwl.gregsteamsmp.commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TeamCreate extends SubCommand {

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
            String teamName = (args[1]);
            UUID teamOwner = player.getUniqueId();

            player.sendMessage("console - teamname:" + teamName + " Owner:" + teamOwner);
        } else {
            player.sendMessage(GregsTeamSMP.msgPrefix + "(!) /team create <nameofteam> !");
        }
    }

    @Override
    public List<String> getSubCommandArguments(Player player, String[] args) {
        return null;

    }
}
