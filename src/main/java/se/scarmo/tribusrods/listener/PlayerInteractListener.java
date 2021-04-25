package se.scarmo.tribusrods.listener;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import se.scarmo.tribusrods.Core;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {

        Player p = e.getPlayer();

        if (p.getItemInHand() == null) return;

        if (Core.getInstance().isRod(p.getItemInHand())) {

            Bukkit.getScheduler().scheduleSyncDelayedTask(Core.getInstance(), () -> p.getItemInHand().setDurability((short) 0), 1L);
            p.updateInventory();

        }

        if (p.isSneaking() && Core.getInstance().isRod(p.getItemInHand())) {
            Core.getInstance().openUpgradeMenu(p, p.getItemInHand());
            p.playSound(p.getLocation(), Sound.CLICK, 1, 1);
        }

    }

}
