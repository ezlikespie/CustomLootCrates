package me.ezlikespie.util;

import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public abstract class ActionItem extends ItemStack {

	private UUID id;
	
	@SuppressWarnings("deprecation")
	public ActionItem(Material mat, int num, byte data) {
		
		super(mat, num, data);
		
		id = UUID.randomUUID();
		
	}
	
	public ActionItem(Material mat, int num) {
		this(mat, num, (byte) 0);
	}
	
	public ActionItem(Material mat) {
		this(mat, 1, (byte) 0);
	}
	
	public UUID getId() {
		return id;
	}
	
	public abstract void click(InventoryClickEvent e, Player p);
	
}
