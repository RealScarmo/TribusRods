package se.scarmo.tribusrods.gui;

import com.google.common.base.Strings;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import se.scarmo.tribusrods.Core;
import se.scarmo.tribusrods.enchant.RodEnchant;

public class UpgradeGUI extends GUI {

    public UpgradeGUI(Player p, ItemStack i) {

        super("upgrade", "§8» §6§lFISHING-ROD", 3, true);

        new GUIItem().setMaterial(Material.ENCHANTED_BOOK).setSlot(11)
                .setDisplayName("§b§lSEVEN SEAS")
                .setLore(enchantLore(RodEnchant.SEVEN_SEAS, i)).addToGUI(this);

        new GUIItem().setMaterial(Material.ENCHANTED_BOOK).setSlot(13)
                .setDisplayName("§e§lTOKEN CATCHER")
                .setLore(enchantLore(RodEnchant.TOKEN_BOOST, i)).addToGUI(this);

        new GUIItem().setMaterial(Material.ENCHANTED_BOOK).setSlot(15)
                .setDisplayName("§d§lSPAWNER CATCHER")
                .setLore(enchantLore(RodEnchant.SPAWNER_FINDER, i)).addToGUI(this);

    }

    private String[] enchantLore(RodEnchant e, ItemStack i) {

        return new String[]{
                "",
                e.getDesc(),
                "",
                "§8[" + progessBar(e, i) + "§8]",
                "",
                "§7Level §8» §e" + Core.getRodEnchantManager().getLevel(e, i) + "/" + e.getMaxLevel(),
                "§7Kostnad §8» §e" + e.getCostPerUpgrade() + " Tokens",
                "",
        };

    }

    private String progessBar(RodEnchant e, ItemStack i) {

        int count = 30;

        float percent = (float) Core.getRodEnchantManager().getLevel(e, i) / e.getMaxLevel();
        int progressBars = (int) (count * percent);

        return Strings.repeat("§a" + "|", progressBars)
                + Strings.repeat("§8" + "|", count - progressBars);

    }

}
