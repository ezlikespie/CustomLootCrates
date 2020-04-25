package me.ezlikespie.specialitems.crate_editor;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;

import me.ezlikespie.specialguis.SelectKeyForCrateGUI;
import me.ezlikespie.util.ActionItem;
import me.ezlikespie.util.KeySelectObserver;
import me.ezlikespie.util.Message;

public class CrateEditorSelectKeyItem extends ActionItem {
	
	private KeySelectObserver observer;
	
	public CrateEditorSelectKeyItem(KeySelectObserver obs) {
		super(Material.TRIPWIRE_HOOK, 1);
		ItemMeta selectKeyMeta = getItemMeta();
		selectKeyMeta.setDisplayName(Message.trans("&e&lSelect Key"));
		selectKeyMeta.setLore(Arrays.asList(Message.trans("&7Click to Select from Available Keys")));
		selectKeyMeta.addEnchant(Enchantment.DURABILITY, 1, true);
		selectKeyMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		setItemMeta(selectKeyMeta);
		observer = obs;
	}
	
	public void click(InventoryClickEvent e, Player p) {
		p.openInventory(SelectKeyForCrateGUI.createGUI(observer).getInventory());
	}
	
}
