package fr.gregwl.gregsteamsmp.command.team;

import fr.gregwl.gregsteamsmp.GregsTeamSMP;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class TeamCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if(args.length > 0) {
            if(args[0].equalsIgnoreCase("create")) {
                sender.sendMessage("tu créer une team là frro");
            }
        } else {
            sender.sendMessage(GregsTeamSMP.msgPrefix + "(!) /team <fonction> ... !");
        }

        return false;
    }
}
