package se.scarmo.tribusrods.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import se.scarmo.tribusrods.Core;

public class RodCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (!sender.hasPermission("tribus.giverod")) {
            sender.sendMessage("§6§lTribusMC §8» §cOkänt kommando.");
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage("§6§lTribusMC §8» §cAnvändning: /rod <spelare>.");
            return true;
        }

        if (Bukkit.getPlayer(args[0]) == null) {
            sender.sendMessage("§6§lTribusMC §8» §cSpelaren hittades inte.");
            return true;
        }

        Player t = Bukkit.getPlayer(args[0]);

        if (t.getInventory().firstEmpty() == -1) {
            sender.sendMessage("§6§lTribusMC §8» §cSpelarens inventory är fullt.");
            return true;
        }

        sender.sendMessage("§6§lTribusMC §8» §aDu gav en Fishing Rod till " + t.getName());
        t.getInventory().addItem(Core.getInstance().freshRod);

        return false;
    }

}
