package se.scarmo.tribusrods.enchant;

import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class RodEnchantManager {

    public int getLevel(RodEnchant e, ItemStack i) {

        net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(i);

        if (!nmsItem.hasTag()) return 0;

        NBTTagCompound t = nmsItem.getTag();

        if (!t.hasKey(e.getTag())) return 0;

        return t.getInt(e.getTag());

    }

    public void setLevel(Player p, ItemStack i, RodEnchant e, int amount) {

        net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(i);

        NBTTagCompound tag = nmsItem.getTag() != null ? nmsItem.getTag() : new NBTTagCompound();
        tag.setInt(e.getTag(), amount);
        nmsItem.setTag(tag);

        ItemStack i2 = CraftItemStack.asCraftMirror(nmsItem);
        ItemMeta meta = i2.getItemMeta();

        ArrayList<String> lore = new ArrayList<>();
        lore.addAll(meta.getLore());

        for (RodEnchant enchants : RodEnchant.values()) {

            for (int line = 0; line < lore.size(); line++) {

                if (lore.get(line).contains(enchants.getLoreString())) {
                    lore.remove(lore.get(line));
                }

            }

            if (getLevel(enchants, i2) != 0) {
                lore.add(enchants.loreString + " " + getLevel(enchants, i2));
            }

        }

        meta.setLore(lore);
        i2.setItemMeta(meta);

        p.getItemInHand().setType(Material.AIR);
        p.getInventory().setItem(p.getInventory().getHeldItemSlot(), i2);

    }

    public void increaseLevel(Player p, ItemStack i, RodEnchant e, int amount) {
        setLevel(p, i, e, getLevel(e, i) + amount);
    }

}
