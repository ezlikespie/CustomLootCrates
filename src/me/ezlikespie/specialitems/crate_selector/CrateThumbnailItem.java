package me.ezlikespie.specialitems.crate_selector;

import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.meta.ItemMeta;

import me.ezlikespie.Crate;
import me.ezlikespie.specialguis.EditCrateGUI;
import me.ezlikespie.util.ActionItem;
import me.ezlikespie.util.Message;

public class CrateThumbnailItem extends ActionItem {

	private UUID crateId;
	
	public CrateThumbnailItem(Crate c) {
		super(Material.CHEST, 1);
		ItemMeta thumbnailMeta = getItemMeta();
		thumbnailMeta.setDisplayName(Message.trans("&r"+c.getName()));
		setItemMeta(thumbnailMeta);
		crateId = c.getId();
	}
	
	public void click(InventoryClickEvent e, Player p) {
		p.openInventory(EditCrateGUI.createGUI(crateId).getInventory());
	}
	
}
