package me.ezlikespie.specialitems.selector;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.meta.ItemMeta;

import me.ezlikespie.Key;
import me.ezlikespie.util.ActionItem;
import me.ezlikespie.util.Message;

public class SelectorEditKeyItem extends ActionItem {

	public SelectorEditKeyItem() {
		super(Material.CHEST, 1);
		ItemMeta editCrateMeta = getItemMeta();
		editCrateMeta.setDisplayName(Message.trans("&e&lEdit Key"));
		editCrateMeta.setLore(Arrays.asList(Message.trans("&7Click to Edit an Existing Key")));
		setItemMeta(editCrateMeta);
	}
	
	public void click(InventoryClickEvent e, Player p) {
		e.setCancelled(true);
		Key.edit(p);
	}
	
}
