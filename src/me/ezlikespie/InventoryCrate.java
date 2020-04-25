package me.ezlikespie;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import me.ezlikespie.util.Message;

public class InventoryCrate extends ItemStack {

	public InventoryCrate(Crate c, int amount) {
		
		super(Material.CHEST, amount);
		ItemMeta invCrateMeta = getItemMeta();
		invCrateMeta.setDisplayName(Message.trans("&r"+c.getName()));
		invCrateMeta.getPersistentDataContainer().set(new NamespacedKey(Main.getInstance(), "Crate"), PersistentDataType.STRING, c.getId().toString());
		setItemMeta(invCrateMeta);
		
	}
	
}
