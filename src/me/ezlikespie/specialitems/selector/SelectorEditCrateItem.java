package me.ezlikespie.specialitems.selector;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.meta.ItemMeta;

import me.ezlikespie.Crate;
import me.ezlikespie.util.ActionItem;
import me.ezlikespie.util.Message;

public class SelectorEditCrateItem extends ActionItem {

	public SelectorEditCrateItem() {
		super(Material.CHEST, 1);
		ItemMeta editCrateMeta = getItemMeta();
		editCrateMeta.setDisplayName(Message.trans("&e&lEdit Crate"));
		editCrateMeta.setLore(Arrays.asList(Message.trans("&7Click to Customize an Existing Crate")));
		setItemMeta(editCrateMeta);
	}
	
	public void click(InventoryClickEvent e, Player p) {
		e.setCancelled(true);
		Crate.edit(p);
	}
	
}
