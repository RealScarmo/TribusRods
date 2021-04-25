package se.scarmo.tribusrods.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import se.scarmo.tribusrods.Core;

import java.util.ArrayList;
import java.util.List;

public abstract class GUI {

    private ArrayList<GUIItem> items = new ArrayList<>();
    private String internalName;
    private int rows;
    private String title;
    private boolean fill;
    private Inventory inv;

    public GUI(String internalName, String title, int rows, boolean fill) {

        this.internalName = internalName;
        this.title = title;
        this.rows = rows;
        this.fill = fill;
        create();

    }

    public String getInternalName() {
        return internalName;
    }

    public int getRows() {
        return rows;
    }

    public String getTitle() {
        return title;
    }

    public boolean isFill() {
        return fill;
    }

    public List<GUIItem> getItems() {
        return items;
    }

    public Inventory getInv() {
        return inv;
    }

    public Inventory create() {

        this.inv = Bukkit.createInventory(null, rows * 9, title);

        if (fill) fill();

        for (GUIItem item : items) {
            inv.setItem(item.getSlot(), item.create());
        }

        return this.inv;

    }

    public void open(Player p) {
        p.openInventory(inv);
    }

    private void fill() {
        ItemStack fill = new GUIItem().setMaterial(Material.STAINED_GLASS_PANE).setData(7).setInternalName("fill").setAmount(1).setDisplayName(" ").create();

        for (int i = 0; i < rows * 9; i++) {
            inv.setItem(i, fill);
        }
    }

}
