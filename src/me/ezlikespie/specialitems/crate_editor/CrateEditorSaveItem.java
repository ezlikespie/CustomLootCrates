package me.ezlikespie.specialitems.crate_editor;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import me.ezlikespie.Main;
import me.ezlikespie.specialguis.EditCrateGUI;
import me.ezlikespie.util.ActionItem;
import me.ezlikespie.util.Message;

public class CrateEditorSaveItem extends ActionItem {
	
	private EditCrateGUI eg;
	
	public CrateEditorSaveItem(EditCrateGUI _eg) {
		super(Material.EMERALD_BLOCK, 1);
		ItemMeta im = getItemMeta();
		im.setDisplayName(Message.trans("&e&lSave Crate"));
		setItemMeta(im);
		eg=_eg;
	}
	
	public void click(InventoryClickEvent e, Player p) {
		e.setCancelled(true);
		Inventory eginv = eg.getInventory();
		Map<Integer, ItemStack> items = new HashMap<Integer, ItemStack>();
		for(int i = 0; i<27; i++) {
			ItemStack currentItem = eginv.getItem(i);
			if(currentItem!=null)
				items.put(i, currentItem);
		}
		eg.getCrate().setInventory(items);
		new BukkitRunnable() {
			public void run() {
				p.closeInventory();
				cancel();
			}
		}.runTaskLater(Main.getInstance(), 1);
		Message.send(p, "&aCrate &f\""+eg.getCrate().getName()+"&f\"&r&a Saved");
	}
	
}
