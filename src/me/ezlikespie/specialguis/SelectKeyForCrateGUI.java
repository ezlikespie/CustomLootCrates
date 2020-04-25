package me.ezlikespie.specialguis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import me.ezlikespie.Key;
import me.ezlikespie.specialitems.select_key_for_crate.KeyForCrateThumbnailItem;
import me.ezlikespie.specialitems.select_key_for_crate.SelectKeyForCrateNextItem;
import me.ezlikespie.specialitems.select_key_for_crate.SelectKeyForCratePreviousItem;
import me.ezlikespie.specialitems.selector.ReturnToSelectionItem;
import me.ezlikespie.specialitems.selector.SelectorCreateKeyItem;
import me.ezlikespie.util.ActionItem;
import me.ezlikespie.util.CustomGUI;
import me.ezlikespie.util.KeyComparator;
import me.ezlikespie.util.KeySelectObserver;

public class SelectKeyForCrateGUI extends CustomGUI {

	private int numKeys;
	private int currentScreen;
	
	public SelectKeyForCrateGUI(Inventory i) {
		super(i);
		setDrag(true);
		setClick(true);
	}
	
	public int getNumKeys() {
		return numKeys;
	}
	
	public int getCurrentScreen() {
		return currentScreen;
	}
	
	public int getNumScreens() {
		return (Key.getKeys().size()-1)/45 + 1;
	}
	
	public static SelectKeyForCrateGUI createGUI(int screen, KeySelectObserver obs) {
		
		Inventory newInv = Bukkit.createInventory(null, 54, "Select Key");
		SelectKeyForCrateGUI selectKey = new SelectKeyForCrateGUI(newInv);
		selectKey.currentScreen = screen;
		
		List<UUID> keys = new ArrayList<UUID>();
		Iterator<UUID> it = Key.getKeys().iterator();
		while(it.hasNext()) {
			keys.add(it.next());
		}
		Collections.sort(keys, new KeyComparator());
		
		for(int i = screen*45; i<keys.size()&&i<((screen+1)*45); i++)
			selectKey.setAction(i-screen*45, new KeyForCrateThumbnailItem(Key.getKey(keys.get(i)), obs));
		
		if(selectKey.currentScreen+1<selectKey.getNumScreens()) {
			ActionItem arrowItem = new SelectKeyForCrateNextItem(selectKey.currentScreen, obs);
			selectKey.setAction(50, arrowItem);
		}
		if(selectKey.currentScreen>0) {
			ActionItem arrowItem = new SelectKeyForCratePreviousItem(selectKey.currentScreen, obs);
			selectKey.setAction(48, arrowItem);
		}
		
		ActionItem createKey = new SelectorCreateKeyItem();
		selectKey.setAction(49, createKey);
		
		selectKey.numKeys=keys.size();
		
		ActionItem goBack = new ReturnToSelectionItem();
		selectKey.setAction(53, goBack);
		
		return selectKey;
		
	}
	
	public static SelectKeyForCrateGUI createGUI(KeySelectObserver obs) {
		return createGUI(0, obs);
	}
	
}
