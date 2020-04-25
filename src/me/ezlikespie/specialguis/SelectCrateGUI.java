package me.ezlikespie.specialguis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import me.ezlikespie.Crate;
import me.ezlikespie.specialitems.crate_selector.CrateThumbnailItem;
import me.ezlikespie.specialitems.crate_selector.SelectCrateNextItem;
import me.ezlikespie.specialitems.crate_selector.SelectCratePreviousItem;
import me.ezlikespie.specialitems.selector.ReturnToSelectionItem;
import me.ezlikespie.specialitems.selector.SelectorCreateCrateItem;
import me.ezlikespie.util.ActionItem;
import me.ezlikespie.util.CrateComparator;
import me.ezlikespie.util.CustomGUI;

public class SelectCrateGUI extends CustomGUI {
	
	public SelectCrateGUI(Inventory i) {
		
		super(i);
		setClick(true);
		setDrag(true);
		
	}
	
	private int numCrates;
	private int currentScreen;
	
	public int getNumCrates() {
		return numCrates;
	}
	
	public int getCurrentScreen() {
		return currentScreen;
	}
	
	public int getNumScreens() {
		return (Crate.getCrates().size()-1)/45 + 1;
	}
	
	public static SelectCrateGUI createGUI(int screen) {
		
		Inventory newInv = Bukkit.createInventory(null, 54, "Select Crate");
		SelectCrateGUI selectCrate = new SelectCrateGUI(newInv);
		selectCrate.currentScreen = screen;
		
		List<UUID> crates = new ArrayList<UUID>();
		Iterator<UUID> it = Crate.getCrates().iterator();
		while(it.hasNext()) {
			crates.add(it.next());
		}
		Collections.sort(crates, new CrateComparator());
		
		for(int i = screen*45; i<crates.size()&&i<((screen+1)*45); i++)
			selectCrate.setAction(i-screen*45, new CrateThumbnailItem(Crate.getCrate(crates.get(i))));
		
		if(selectCrate.currentScreen+1<selectCrate.getNumScreens()) {
			ActionItem arrowItem = new SelectCrateNextItem(selectCrate.currentScreen);
			selectCrate.setAction(50, arrowItem);
		}
		if(selectCrate.currentScreen>0) {
			ActionItem arrowItem = new SelectCratePreviousItem(selectCrate.currentScreen);
			selectCrate.setAction(48, arrowItem);
		}
		
		ActionItem createCrate = new SelectorCreateCrateItem();
		selectCrate.setAction(49, createCrate);
		
		selectCrate.numCrates=crates.size();
		
		ActionItem goBack = new ReturnToSelectionItem();
		selectCrate.setAction(53, goBack);
		
		return selectCrate;
		
	}
	
	public static SelectCrateGUI createGUI() {
		return createGUI(0);
	}
	
}
