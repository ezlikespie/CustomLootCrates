package me.ezlikespie.specialitems.select_key_for_crate;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.meta.ItemMeta;

import me.ezlikespie.specialguis.SelectKeyForCrateGUI;
import me.ezlikespie.util.ActionItem;
import me.ezlikespie.util.KeySelectObserver;
import me.ezlikespie.util.Message;

public class SelectKeyForCratePreviousItem extends ActionItem {

	private KeySelectObserver observer;
	
	public SelectKeyForCratePreviousItem(int screen, KeySelectObserver obs) {
		super(Material.ARROW, 1);
		screenNum=screen;
		ItemMeta itemMeta = getItemMeta();
		itemMeta.setDisplayName(Message.trans("&aPage "+screenNum));
		setItemMeta(itemMeta);
		observer = obs;
	}
	
	private int screenNum;
	
	public void click(InventoryClickEvent e, Player p) {
		p.openInventory(SelectKeyForCrateGUI.createGUI(screenNum-1, observer).getInventory());
	}
	
}
