package me.ezlikespie.specialitems.crate_selector;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.meta.ItemMeta;

import me.ezlikespie.specialguis.SelectCrateGUI;
import me.ezlikespie.util.ActionItem;
import me.ezlikespie.util.Message;

public class SelectCrateNextItem extends ActionItem {

	public SelectCrateNextItem(int screen) {
		super(Material.ARROW, 1);
		screenNum=screen;
		ItemMeta itemMeta = getItemMeta();
		itemMeta.setDisplayName(Message.trans("&aPage "+(screenNum+2)));
		setItemMeta(itemMeta);
	}
	
	private int screenNum;
	
	public void click(InventoryClickEvent e, Player p) {
		p.openInventory(SelectCrateGUI.createGUI(screenNum+1).getInventory());
	}
	
}
