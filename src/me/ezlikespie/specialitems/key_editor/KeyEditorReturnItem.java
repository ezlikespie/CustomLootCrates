package me.ezlikespie.specialitems.key_editor;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.meta.ItemMeta;

import me.ezlikespie.Key;
import me.ezlikespie.util.ActionItem;
import me.ezlikespie.util.Message;

public class KeyEditorReturnItem extends ActionItem {

	public KeyEditorReturnItem() {
		super(Material.ARROW, 1);
		ItemMeta im = getItemMeta();
		im.setDisplayName(Message.trans("&e&lGo Back"));
		setItemMeta(im);
	}
	
	public void click(InventoryClickEvent e, Player p) {
		Key.edit(p);
	}
	
}
