package me.ezlikespie.specialitems.selector;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.meta.ItemMeta;

import me.ezlikespie.CustomLootCrates;
import me.ezlikespie.util.ActionItem;
import me.ezlikespie.util.Message;

public class ReturnToSelectionItem extends ActionItem {

	public ReturnToSelectionItem() {
		super(Material.ARROW, 1);
		ItemMeta im = getItemMeta();
		im.setDisplayName(Message.trans("&e&lGo Back"));
		setItemMeta(im);
	}
	
	public void click(InventoryClickEvent e, Player p) {
		e.setCancelled(true);
		CustomLootCrates.select(p);
	}
	
}
