package me.ezlikespie.specialitems.key_selector;

import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;

import me.ezlikespie.Key;
import me.ezlikespie.specialguis.EditKeyGUI;
import me.ezlikespie.util.ActionItem;
import me.ezlikespie.util.Message;

public class KeyThumbnailItem extends ActionItem {

	private UUID keyId;
	
	public KeyThumbnailItem(Key k) {
		super(Material.TRIPWIRE_HOOK, 1);
		ItemMeta thumbnailMeta = getItemMeta();
		thumbnailMeta.addEnchant(Enchantment.DURABILITY, 1, true);
		thumbnailMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		thumbnailMeta.setDisplayName(Message.trans("&r"+k.getName()));
		setItemMeta(thumbnailMeta);
		keyId = k.getId();
	}
	
	public void click(InventoryClickEvent e, Player p) {
		p.openInventory(EditKeyGUI.createGUI(keyId).getInventory());
	}
	
}
