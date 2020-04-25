package me.ezlikespie.specialitems.select_key_for_crate;

import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;

import me.ezlikespie.Key;
import me.ezlikespie.util.ActionItem;
import me.ezlikespie.util.KeySelectObserver;
import me.ezlikespie.util.Message;

public class KeyForCrateThumbnailItem extends ActionItem {

	private UUID keyId;
	private KeySelectObserver observer;
	
	public KeyForCrateThumbnailItem(Key k, KeySelectObserver obs) {
		super(Material.TRIPWIRE_HOOK, 1);
		ItemMeta thumbnailMeta = getItemMeta();
		thumbnailMeta.addEnchant(Enchantment.DURABILITY, 1, true);
		thumbnailMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		thumbnailMeta.setDisplayName(Message.trans("&r"+k.getName()));
		setItemMeta(thumbnailMeta);
		keyId = k.getId();
		observer = obs;
	}
	
	public void click(InventoryClickEvent e, Player p) {
		observer.selectKey(keyId, p);
	}
	
}
