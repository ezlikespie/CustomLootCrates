package me.ezlikespie.specialitems.key_selector;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.meta.ItemMeta;

import me.ezlikespie.specialguis.SelectKeyGUI;
import me.ezlikespie.util.ActionItem;
import me.ezlikespie.util.Message;

public class SelectKeyPreviousItem extends ActionItem {

	public SelectKeyPreviousItem(int screen) {
		super(Material.ARROW, 1);
		screenNum=screen;
		ItemMeta itemMeta = getItemMeta();
		itemMeta.setDisplayName(Message.trans("&aPage "+screenNum));
		setItemMeta(itemMeta);
	}
	
	private int screenNum;
	
	public void click(InventoryClickEvent e, Player p) {
		p.openInventory(SelectKeyGUI.createGUI(screenNum-1).getInventory());
	}
	
}
