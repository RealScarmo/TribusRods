package se.scarmo.tribusrods.listener;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import se.scarmo.tribusrods.Core;
import se.scarmo.tribusrods.gui.UpgradeGUI;

import java.util.Locale;

public class CommandPreprocessListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCommand(PlayerCommandPreprocessEvent e) {

        Player p = e.getPlayer();

        if (p.getItemInHand() == null) return;

        if (e.getMessage().toLowerCase().startsWith("/upgrade") && Core.getInstance().isRod(p.getItemInHand())) {
            e.setCancelled(true);
            Core.getInstance().openUpgradeMenu(p, p.getItemInHand());
            p.playSound(p.getLocation(), Sound.CLICK, 1, 1);
        }

    }

}
