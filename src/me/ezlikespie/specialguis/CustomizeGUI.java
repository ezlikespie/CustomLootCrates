package me.ezlikespie.specialguis;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.ezlikespie.Key;
import me.ezlikespie.specialitems.customizer.CustomizerCreateCrateItem;
import me.ezlikespie.specialitems.customizer.CustomizerSelectKeyItem;
import me.ezlikespie.specialitems.customizer.FillerPanelItem;
import me.ezlikespie.specialitems.selector.ReturnToSelectionItem;
import me.ezlikespie.util.ActionItem;
import me.ezlikespie.util.CustomGUI;
import me.ezlikespie.util.KeySelectObserver;
import me.ezlikespie.util.Message;

public class CustomizeGUI extends CustomGUI implements KeySelectObserver {

	private UUID keyId;
	
	public CustomizeGUI(Inventory i) {
		super(i);
	}
	
	public static CustomizeGUI createGUI() {
		
		Inventory inv = Bukkit.createInventory(null, 54, "Custom Crate");
		CustomizeGUI customizer = new CustomizeGUI(inv);
		
		//======================== Create Action Buttons ===================
		ActionItem createCrate =  new CustomizerCreateCrateItem(customizer);
		ActionItem selectKey =  new CustomizerSelectKeyItem(customizer);
		ActionItem returnSel = new ReturnToSelectionItem();
		
		//==================== Add Gray Panel Filler Items ====================
		ActionItem grayPane = new FillerPanelItem();
		for(int i = 27; i<36; i++)
			inv.setItem(i, grayPane);
		
		//==================== Add Items =====================
		customizer.setAction(39, createCrate);
		customizer.setAction(41, selectKey);
		customizer.setAction(53, returnSel);
		
		return customizer;
		
	}
	
	public UUID getKeyId() {
		return keyId;
	}
	
	@Override
	@EventHandler
	public void click(InventoryClickEvent e) {
		if(e.getInventory()==null||e.getInventory().hashCode()!=inv.hashCode())
			return;
		if(!(e.getWhoClicked() instanceof Player))
			return;
		if(e.isShiftClick())
			e.setCancelled(true);
		Player p = (Player) e.getWhoClicked();
		if(27<=e.getSlot()&&e.getSlot()<=53)
			e.setCancelled(true);
		if(!actionItems.containsKey(e.getSlot()))
			return;
		actionItems.get(e.getSlot()).click(e, p);
	}
	
	@Override
	@EventHandler
	public void drag(InventoryDragEvent e) {
		if(e.getInventory()==null||e.getInventory().hashCode()!=inv.hashCode())
			return;
		Set<Integer> slots = e.getInventorySlots();
		Iterator<Integer> it = slots.iterator();
		while(it.hasNext()) {
			int next = it.next();
			if(27<=next&&next<=53) {
				e.setCancelled(true);
				break;
			}
		}
	}
	
	public void selectKey(UUID kid, Player p) {
		keyId = kid;
		p.openInventory(getInventory());
		ItemStack selectKey = getInventory().getItem(41);
		ItemMeta selectKeyMeta = selectKey.getItemMeta();
		selectKeyMeta.setLore(Arrays.asList(Message.trans("&7Currently Selected: &r"+Key.getKey(kid).getName())));
		selectKey.setItemMeta(selectKeyMeta);
		ItemStack createCrate = getInventory().getItem(39);
		ItemMeta createCrateMeta = createCrate.getItemMeta();
		createCrateMeta.setLore(null);
		createCrate.setItemMeta(createCrateMeta);
	}
	
}
