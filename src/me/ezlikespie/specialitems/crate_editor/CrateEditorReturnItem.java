package me.ezlikespie.specialitems.crate_editor;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.meta.ItemMeta;

import me.ezlikespie.Crate;
import me.ezlikespie.util.ActionItem;
import me.ezlikespie.util.Message;

public class CrateEditorReturnItem extends ActionItem {

	public CrateEditorReturnItem() {
		super(Material.ARROW, 1);
		ItemMeta im = getItemMeta();
		im.setDisplayName(Message.trans("&e&lGo Back"));
		setItemMeta(im);
	}
	
	public void click(InventoryClickEvent e, Player p) {
		Crate.edit(p);
	}
	
}
