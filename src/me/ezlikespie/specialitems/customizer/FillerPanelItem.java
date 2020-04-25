package me.ezlikespie.specialitems.customizer;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.meta.ItemMeta;

import me.ezlikespie.util.ActionItem;

public class FillerPanelItem extends ActionItem {

	public FillerPanelItem() {
		super(Material.GRAY_STAINED_GLASS_PANE, 1);
		ItemMeta grayPaneMeta = getItemMeta();
		grayPaneMeta.setDisplayName(" ");
		setItemMeta(grayPaneMeta);
	}
	
	public void click(InventoryClickEvent e, Player p) {
		e.setCancelled(true);
	}
	
}
