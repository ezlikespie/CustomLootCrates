package me.ezlikespie.specialitems.selector;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.meta.ItemMeta;

import me.ezlikespie.Crate;
import me.ezlikespie.util.ActionItem;
import me.ezlikespie.util.Message;

public class SelectorCreateCrateItem extends ActionItem {

	public SelectorCreateCrateItem() {
		
		super(Material.EMERALD_BLOCK, 1);
		ItemMeta createCrateMeta = getItemMeta();
		createCrateMeta.setDisplayName(Message.trans("&e&lCreate Crate"));
		createCrateMeta.setLore(Arrays.asList(Message.trans("&7Click to Create a New Crate")));
		setItemMeta(createCrateMeta);
	}
	
	public void click(InventoryClickEvent e, Player p) {
		e.setCancelled(true);
		Crate.customize(p);
	}
	
}
