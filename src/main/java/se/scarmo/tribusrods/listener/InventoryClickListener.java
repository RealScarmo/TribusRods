package se.scarmo.tribusrods.listener;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import se.scarmo.tribusrods.Core;
import se.scarmo.tribusrods.enchant.RodEnchant;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {

        if (e.getInventory() == null) return;
        if (e.getCurrentItem() == null) return;
        if (!e.getCurrentItem().hasItemMeta()) return;
        if (!e.getCurrentItem().getItemMeta().hasDisplayName()) return;

        Player p = (Player) e.getWhoClicked();

        if (e.getInventory().getTitle().equals("§8» §6§lFISHING-ROD")) {

            e.setCancelled(true);

            for (RodEnchant enchant : RodEnchant.values()) {

                if (e.getCurrentItem().getItemMeta().getDisplayName().equals(enchant.getLoreString())) {

                    String tokens = "%tokens_current_long%";
                    tokens = PlaceholderAPI.setPlaceholders(p, tokens);

                    int tokensInt = Integer.parseInt(tokens.replace(",", ""));

                    if (Core.getRodEnchantManager().getLevel(enchant, p.getItemInHand()) == enchant.getMaxLevel()) {
                        p.sendMessage("§6§lTribusMC §8» §cDenna upgrade är redan i max level.");
                        return;
                    }

                    if (enchant.getCostPerUpgrade() > tokensInt) {
                        p.sendMessage("§6§lTribusMC §8» §cDu har inte råd med detta. Du behöver " + enchant.getCostPerUpgrade() + " tokens.");
                        return;
                    }

                    Core.getInstance().getServer().dispatchCommand(Core.getInstance().getServer().getConsoleSender(), "tokens take " + p.getName() + " " + enchant.getCostPerUpgrade());
                    Core.getRodEnchantManager().increaseLevel(p, p.getItemInHand(), enchant, 1);

                    p.sendMessage("§6§lTribusMC §8» §aDu uppgraderade din fishing-rod!");
                    p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 1);

                    Core.getInstance().openUpgradeMenu(p, p.getItemInHand());

                    return;

                }

            }

        }

    }

}
