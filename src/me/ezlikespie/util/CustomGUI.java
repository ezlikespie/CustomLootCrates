package me.ezlikespie.util;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.Inventory;

import me.ezlikespie.Main;

public class CustomGUI implements Listener {

	protected Inventory inv;
	protected UUID id;
	protected Map<Integer, ActionItem> actionItems = new HashMap<Integer, ActionItem>();
	protected boolean cancelClick = false;
	protected boolean cancelDrag = false;
	
	public CustomGUI(Inventory i) {
		
		inv = i;
		id = UUID.randomUUID();
		
		Bukkit.getServer().getPluginManager().registerEvents(this, Main.getInstance());
		
	}
	
	public Inventory getInventory() {
		return inv;
	}
	
	public UUID getId() {
		return id;
	}
	
	public void setClick(boolean val) {
		cancelClick = val;
	}
	
	public void setDrag(boolean val) {
		cancelDrag = val;
	}
	
	@EventHandler
	public void click(InventoryClickEvent e) {
		if(e.getInventory()==null||e.getInventory().hashCode()!=inv.hashCode())
			return;
		if(!(e.getWhoClicked() instanceof Player))
			return;
		if(cancelClick)
			e.setCancelled(true);
		if(e.isShiftClick())
			return;
		Player p = (Player) e.getWhoClicked();
		if(!actionItems.containsKey(e.getSlot()))
			return;
		actionItems.get(e.getSlot()).click(e, p);
	}
	
	@EventHandler
	public void drag(InventoryDragEvent e) {
		if(e.getInventory()==null||e.getInventory().hashCode()!=inv.hashCode())
			return;
		if(cancelDrag)
			e.setCancelled(true);
	}
	
	public void openGUI(Player p) {
		p.openInventory(inv);
	}
	
	public void setAction(int slot, ActionItem item) {
		inv.setItem(slot, item);
		actionItems.put(slot, item);
	}
	
}
