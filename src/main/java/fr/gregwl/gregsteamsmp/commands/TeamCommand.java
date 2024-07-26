package fr.gregwl.gregsteamsmp.commands;

import fr.gregwl.gregsteamsmp.GregsTeamSMP;
import fr.gregwl.gregsteamsmp.commands.teamsub.*;
import fr.gregwl.gregsteamsmp.commands.teamsub.claims.TeamClaim;
import fr.gregwl.gregsteamsmp.commands.teamsub.claims.TeamUnclaim;
import fr.gregwl.gregsteamsmp.commands.teamsub.invite.TeamInvite;
import fr.gregwl.gregsteamsmp.commands.teamsub.invite.TeamJoin;
import fr.gregwl.gregsteamsmp.commands.teamsub.leave.TeamKick;
import fr.gregwl.gregsteamsmp.commands.teamsub.leave.TeamLeave;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TeamCommand implements TabExecutor {

    private ArrayList<SubCommand> subCommands = new ArrayList<>();

    public TeamCommand() {
        subCommands.add(new TeamCreate());
        subCommands.add(new TeamDisband());
        subCommands.add(new TeamInfo());
        subCommands.add(new TeamInvite());
        subCommands.add(new TeamJoin());
        subCommands.add(new TeamKick());
        subCommands.add(new TeamLeave());
        subCommands.add(new TeamClaim());
        subCommands.add(new TeamUnclaim());

        //pour rajouter des sous-commande de la commande "team", juste ajouter une ligne comme ci-dessus, puis
        //faire la class

    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if(sender instanceof Player) {
            Player player = (Player) sender;

            if(args.length > 0) {
                for (int i = 0; i < getSubCommands().size(); i++){
                    if (args[0].equalsIgnoreCase(getSubCommands().get(i).getName())){
                        getSubCommands().get(i).perform(player, args);
                    }
                }

            } else {
                sender.sendMessage(GregsTeamSMP.msgPrefix + "(!) /team <fonction> ... !");
            }
        }
        return true;
    }

    public ArrayList<SubCommand> getSubCommands() {
        return subCommands;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {

        if (args.length == 1){
            ArrayList<String> subcommandsArguments = new ArrayList<>();

            for (int i = 0; i < getSubCommands().size(); i++){
                subcommandsArguments.add(getSubCommands().get(i).getName());
            }

            return subcommandsArguments;
        }else if(args.length >= 2){
            for (int i = 0; i < getSubCommands().size(); i++){
                if (args[0].equalsIgnoreCase(getSubCommands().get(i).getName())){
                    return getSubCommands().get(i).getSubCommandArguments((Player) sender, args);
                }
            }
        }

        return null;

    }
}
