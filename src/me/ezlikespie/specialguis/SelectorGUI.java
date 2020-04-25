package me.ezlikespie.specialguis;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import me.ezlikespie.specialitems.selector.SelectorCreateCrateItem;
import me.ezlikespie.specialitems.selector.SelectorCreateKeyItem;
import me.ezlikespie.specialitems.selector.SelectorEditCrateItem;
import me.ezlikespie.specialitems.selector.SelectorEditKeyItem;
import me.ezlikespie.util.ActionItem;
import me.ezlikespie.util.CustomGUI;

public class SelectorGUI extends CustomGUI {

	public SelectorGUI(Inventory i) {
		super(i);
		setClick(true);
		setDrag(true);
	}
	
	public static SelectorGUI createGUI() {
		Inventory inv = Bukkit.createInventory(null, 27, "Custom Loot Crates");
		SelectorGUI selector = new SelectorGUI(inv);
		
		//==================== Create Selection Options ======================
		ActionItem createCrate = new SelectorCreateCrateItem();
		ActionItem editCrate = new SelectorEditCrateItem();
		
		ActionItem createKey =  new SelectorCreateKeyItem();
		ActionItem editKey = new SelectorEditKeyItem();
		
		
		//==================== Add Items =====================
		selector.setAction(11, createCrate);
		selector.setAction(12, editCrate);
		selector.setAction(14, createKey);
		selector.setAction(15, editKey);
		
		return selector;
	}
	
}
