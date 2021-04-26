package se.scarmo.tribusrods.listener;

import net.minecraft.server.v1_8_R3.EntityFishingHook;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import se.scarmo.tribusrods.Core;
import se.scarmo.tribusrods.enchant.RodEnchant;

import java.lang.reflect.Field;
import java.util.*;

public class PlayerFishListener implements Listener {

    private Core core;

    public PlayerFishListener(Core core) {
        this.core = core;
    }


    @EventHandler
    public void onFish(PlayerFishEvent e) {

        Player p = e.getPlayer();

        if (p.getItemInHand() == null) return;
        if (!Core.getInstance().isRod(p.getItemInHand())) return;

        if (!p.getWorld().getName().equals(core.worldName)) {
            e.setCancelled(true);
            p.sendMessage("§6§lTribusMC §8» §cDu kan endast använda denna fishing rod i Darkzone.");
            return;
        }

        int dropType = new Random().nextInt(3);
        int dropChance = new Random().nextInt(101);

        if (e.getState() == PlayerFishEvent.State.FISHING) {
            setBiteTime(e.getHook(), catchTicks(p));
        }

        if (e.getCaught() == null) return;

        p.sendMessage("§6§lTribusMC §8» §7Du fick §e" + core.econ.format(core.moneyDrop) + "§7.");
        core.econ.depositPlayer(p, Core.getInstance().moneyDrop);
        e.setExpToDrop(0);

        switch (dropType) {
            case 0:

                if (Core.getRodEnchantManager().getLevel(RodEnchant.SPAWNER_FINDER, p.getItemInHand()) == 0) return;

                if (Core.getRodEnchantManager().getLevel(RodEnchant.SPAWNER_FINDER, p.getItemInHand()) * 2 >= dropChance) {
                    e.getCaught().remove();
                    p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 1);
                    randomSpawner(p);
                }

                return;

            case 1:

                if (Core.getRodEnchantManager().getLevel(RodEnchant.TOKEN_BOOST, p.getItemInHand()) == 0) return;

                if (Core.getRodEnchantManager().getLevel(RodEnchant.TOKEN_BOOST, p.getItemInHand()) * 2 >= dropChance) {
                    e.getCaught().remove();
                    p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 1);
                    Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "tokens give " + p.getName() + " " + core.tokensDrop);
                }

                return;

            case 2:

                if (8 >= dropChance) {

                    e.getCaught().remove();
                    p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 1);
                    randomGear(p);

                }

        }

    }

    private void randomSpawner(Player p) {

        Random r = new Random();

        List<String> commands = core.spawners.get(r.nextInt(core.spawners.size()));

        for (String s : commands) {
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), s.replace("%player%", p.getName()));
        }

    }

    private void randomGear(Player p) {

        int c = new Random().nextInt(core.items.size());
        ItemStack item = core.items.get(c);

        if (p.getInventory().firstEmpty() == -1) {
            p.getWorld().dropItem(p.getLocation(), item);
            return;
        }

        p.getInventory().addItem(item);

        if (!item.hasItemMeta()) return;
        if (!item.getItemMeta().hasDisplayName()) return;

        Bukkit.getOnlinePlayers().forEach(o -> o.sendMessage("§6§lTribusMC §8» §e" + p.getName() + " §7fick precis " +  item.getItemMeta().getDisplayName() +  " §7genom att fiska i §cDarkzone§7!"));

    }

    private void setBiteTime(FishHook hook, int time) {
        net.minecraft.server.v1_8_R3.EntityFishingHook hookCopy = (EntityFishingHook) ((CraftEntity) hook).getHandle();

        Field fishCatchTime = null;

        try {
            fishCatchTime = net.minecraft.server.v1_8_R3.EntityFishingHook.class.getDeclaredField("aw");
        } catch (NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }

        assert fishCatchTime != null;
        fishCatchTime.setAccessible(true);

        try {
            fishCatchTime.setInt(hookCopy, time);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }

        fishCatchTime.setAccessible(false);

    }

    private int catchTicks(Player p) {
        return 400 - (Core.getRodEnchantManager().getLevel(RodEnchant.SEVEN_SEAS, p.getItemInHand()) * 18);
    }

}
