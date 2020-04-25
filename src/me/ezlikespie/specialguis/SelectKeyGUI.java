package me.ezlikespie.specialguis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import me.ezlikespie.Key;
import me.ezlikespie.specialitems.key_selector.KeyThumbnailItem;
import me.ezlikespie.specialitems.key_selector.SelectKeyNextItem;
import me.ezlikespie.specialitems.key_selector.SelectKeyPreviousItem;
import me.ezlikespie.specialitems.selector.ReturnToSelectionItem;
import me.ezlikespie.specialitems.selector.SelectorCreateKeyItem;
import me.ezlikespie.util.ActionItem;
import me.ezlikespie.util.CustomGUI;
import me.ezlikespie.util.KeyComparator;

public class SelectKeyGUI extends CustomGUI {
	
	public SelectKeyGUI(Inventory i) {
		
		super(i);
		setClick(true);
		setDrag(true);
		
	}
	
	private int numKeys;
	private int currentScreen;
	
	public int getNumKeys() {
		return numKeys;
	}
	
	public int getCurrentScreen() {
		return currentScreen;
	}
	
	public int getNumScreens() {
		return (Key.getKeys().size()-1)/45 + 1;
	}
	
	public static SelectKeyGUI createGUI(int screen) {
		
		Inventory newInv = Bukkit.createInventory(null, 54, "Select Key");
		SelectKeyGUI selectKey = new SelectKeyGUI(newInv);
		selectKey.currentScreen = screen;
		
		List<UUID> keys = new ArrayList<UUID>();
		Iterator<UUID> it = Key.getKeys().iterator();
		while(it.hasNext()) {
			keys.add(it.next());
		}
		Collections.sort(keys, new KeyComparator());
		
		for(int i = screen*45; i<keys.size()&&i<((screen+1)*45); i++)
			selectKey.setAction(i-screen*45, new KeyThumbnailItem(Key.getKey(keys.get(i))));
		
		if(selectKey.currentScreen+1<selectKey.getNumScreens()) {
			ActionItem arrowItem = new SelectKeyNextItem(selectKey.currentScreen);
			selectKey.setAction(50, arrowItem);
		}
		if(selectKey.currentScreen>0) {
			ActionItem arrowItem = new SelectKeyPreviousItem(selectKey.currentScreen);
			selectKey.setAction(48, arrowItem);
		}
		
		ActionItem createKey = new SelectorCreateKeyItem();
		selectKey.setAction(49, createKey);
		
		selectKey.numKeys=keys.size();
		
		ActionItem goBack = new ReturnToSelectionItem();
		selectKey.setAction(53, goBack);
		
		return selectKey;
		
	}
	
	public static SelectKeyGUI createGUI() {
		return createGUI(0);
	}
	
}
