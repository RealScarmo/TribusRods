package se.scarmo.tribusrods.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import se.scarmo.tribusrods.Core;

public class AddGearCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return true;
        }

        if (!sender.hasPermission("tribus.addrodgear")) {
            sender.sendMessage("§6§lTribusMC §8» §cOkänt kommando.");
            return true;
        }

        Player p = (Player) sender;

        if (p.getItemInHand() == null) {
            sender.sendMessage("§6§lTribusMC §8» §cDu måste ha ett föremål i din hand när du kör detta kommando.");
            return true;
        }

        Core.getInstance().items.add(p.getItemInHand());
        Core.getInstance().getConfig().set("drops.gear", Core.getInstance().items);
        Core.getInstance().saveConfig();

        sender.sendMessage("§6§lTribusMC §8» §7Du la till föremålet i din hand som ett ''gear'' fishing-drop.");

        return false;

    }

}
