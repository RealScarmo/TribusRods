package se.scarmo.tribusrods.gui;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class GUIItem {

    private String internalName;
    private int amount = 1;
    private int data = 0;
    private String displayName;
    private String[] lore;
    private boolean hideAttributes = false;
    private Material material;
    private int slot;

    public GUIItem setInternalName(String internalName) {
        this.internalName = internalName;
        return this;
    }

    public GUIItem setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public GUIItem setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public GUIItem setHideAttributes(boolean hideAttributes) {
        this.hideAttributes = hideAttributes;
        return this;
    }

    public GUIItem setLore(String[] lore) {
        this.lore = lore;
        return this;
    }

    public GUIItem setMaterial(Material material) {
        this.material = material;
        return this;
    }

    public GUIItem setSlot(int slot) {
        this.slot = slot;
        return this;
    }

    public GUIItem setData(int data) {
        this.data = data;
        return this;
    }

    public String getInternalName() {
        return internalName;
    }

    public int getAmount() {
        return amount;
    }

    public int getSlot() {
        return this.slot;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Material getMaterial() {
        return material;
    }

    public String[] getLore() {
        return lore;
    }

    public boolean isHideAttributes() {
        return hideAttributes;
    }

    public int getData() {
        return data;
    }

    public ItemStack create() {

        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();

        if (displayName != null) {
            meta.setDisplayName(displayName);
        }

        if (lore != null) {
            meta.setLore(Arrays.asList(lore));
        }

        if (hideAttributes) {
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        }

        item.setItemMeta(meta);

        item.setDurability((short) data);

        return item;

    }

    public GUIItem addToGUI(GUI gui) {
        gui.getInv().setItem(slot, create());
        return this;
    }

}
