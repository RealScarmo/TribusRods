package se.scarmo.tribusrods;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import se.scarmo.tribusrods.command.AddGearCommand;
import se.scarmo.tribusrods.command.RodCommand;
import se.scarmo.tribusrods.enchant.RodEnchantManager;
import se.scarmo.tribusrods.gui.UpgradeGUI;
import se.scarmo.tribusrods.listener.CommandPreprocessListener;
import se.scarmo.tribusrods.listener.InventoryClickListener;
import se.scarmo.tribusrods.listener.PlayerFishListener;
import se.scarmo.tribusrods.listener.PlayerInteractListener;

import java.util.*;

public class Core extends JavaPlugin {

    private static Core core;
    private static RodEnchantManager rodEnchantManager;

    public ItemStack freshRod;

    public String worldName;

    public int moneyDrop = 0;
    public int tokensDrop = 0;
    public ArrayList<List<String>> spawners = new ArrayList<>();
    public ArrayList<ItemStack> items = new ArrayList<>();

    @Override
    public void onEnable() {

        saveDefaultConfig();

        core = this;
        rodEnchantManager = new RodEnchantManager();

        getCommand("rod").setExecutor(new RodCommand());
        getCommand("addrodgear").setExecutor(new AddGearCommand());

        getServer().getPluginManager().registerEvents(new PlayerInteractListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryClickListener(), this);
        getServer().getPluginManager().registerEvents(new CommandPreprocessListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerFishListener(), this);

        setFreshRod();

        worldName = getConfig().getString("darkzone-world");

        moneyDrop = getConfig().getInt("drops.money");
        tokensDrop = getConfig().getInt("drops.tokens");

        for (String s : getConfig().getConfigurationSection("drops.spawners").getKeys(false)) {
            spawners.add(getConfig().getStringList("drops.spawners." + s + ".commands"));
        }

        if (getConfig().get("drops.gear") != null) {
            for (Object i : getConfig().getList("drops.gear")) {
                ItemStack item = (ItemStack) i;
                items.add(item);
            }
        }

    }

    public static Core getInstance() {
        return core;
    }

    public static RodEnchantManager getRodEnchantManager() {
        return rodEnchantManager;
    }

    public void setFreshRod() {

        ItemStack item = new ItemStack(Material.FISHING_ROD);
        ItemMeta meta = item.getItemMeta();

        meta.addEnchant(Enchantment.DURABILITY, 100, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);

        meta.setDisplayName("§e§lFishing Rod");

        meta.setLore(Arrays.asList(
                "",
                "§7Fiska i §cdarkzone §7med denna fishing rod",
                "§7för att få tokens, spawners, nycklar och pengar.",
                "",
                "§7Skriv §e/upgrade §7eller shift-högerklicka i luften när du har",
                "§7denna fishing rod i handen för att uppgradera den.",
                "",
                "§6Uppgraderingar:"
        ));

        meta.spigot().setUnbreakable(true);

        item.setItemMeta(meta);

        freshRod = item;

    }

    public boolean isRod(ItemStack i) {

        if (!i.getType().equals(Material.FISHING_ROD)) return false;
        if (!i.hasItemMeta()) return false;
        if (!i.getItemMeta().hasDisplayName()) return false;
        if (!i.getItemMeta().hasLore()) return false;
        if (!i.getItemMeta().hasItemFlag(ItemFlag.HIDE_ENCHANTS)) return false;

        if (!i.getItemMeta().getDisplayName().equals(freshRod.getItemMeta().getDisplayName())) return false;

        return true;

    }

    public void openUpgradeMenu(Player p, ItemStack i) {

        new UpgradeGUI(p, i).open(p);

    }

}
