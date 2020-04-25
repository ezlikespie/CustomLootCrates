package me.ezlikespie;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import me.ezlikespie.util.Message;

public class InventoryKey extends ItemStack {

	public InventoryKey(Key k, int amount) {
		
		super(Material.TRIPWIRE_HOOK, amount);
		ItemMeta invKeyMeta = getItemMeta();
		invKeyMeta.addEnchant(Enchantment.DURABILITY, 1, true);
		invKeyMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		invKeyMeta.setDisplayName(Message.trans("&r"+k.getName()));
		List<String> lore = k.getLore();
		if(lore.size()>0&&!lore.get(0).equals("")) {
			String[] loreArr = new String[lore.size()];
			for(int i = 0; i<loreArr.length; i++)
				loreArr[i] = Message.trans(lore.get(i));
			invKeyMeta.setLore(Arrays.asList(loreArr));
		}
		invKeyMeta.getPersistentDataContainer().set(new NamespacedKey(Main.getInstance(), "Key"), PersistentDataType.STRING, k.getId().toString());
		setItemMeta(invKeyMeta);
		
	}
	
}
