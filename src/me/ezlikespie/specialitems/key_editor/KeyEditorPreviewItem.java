package me.ezlikespie.specialitems.key_editor;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import me.ezlikespie.Key;
import me.ezlikespie.Main;
import me.ezlikespie.specialguis.EditKeyGUI;
import me.ezlikespie.util.ActionItem;
import me.ezlikespie.util.Message;
import me.ezlikespie.util.WaitResponse;

public class KeyEditorPreviewItem extends ActionItem {

	private EditKeyGUI eg;
	private int amount;
	private boolean amountSelected;
	
	public KeyEditorPreviewItem(EditKeyGUI _eg) {
		super(Material.TRIPWIRE_HOOK, 1);
		ItemMeta previewItemMeta = getItemMeta();
		previewItemMeta.setDisplayName(Message.trans("&e&lCurrently Editing: &r"+_eg.getKey().getName()));
		previewItemMeta.setLore(Arrays.asList(Message.trans("&7Click to Get Key")));
		previewItemMeta.addEnchant(Enchantment.DURABILITY, 1, true);
		previewItemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		setItemMeta(previewItemMeta);
		eg = _eg;
	}
	
	public void setAmountToGive(String msg, Player p) {
		try {
			amount = Integer.parseInt(msg);
		} catch (NumberFormatException e) {
			Message.send(p, "&cCannot read number! Operation cancelled");
		}
		amountSelected = true;
	}
	
	public void click(InventoryClickEvent e, Player p) {
		
		Message.send(p, "&eChoose a Number of Keys:");
		
		//Close Inventory
		new BukkitRunnable() {
			public void run() {
				p.closeInventory();
				cancel();
			}
		}.runTaskLater(Main.getInstance(), 1);
		
		WaitResponse waiter = new WaitResponse(p);
		waiter.setWatcher(this);
		new BukkitRunnable() {
			public void run() {
				if(amountSelected)
					return;
				Message.send(p, "&cSorry, you waited too long. Action cancelled");
				waiter.setActive(false);
			}
		}.runTaskLater(Main.getInstance(), WaitResponse.WAIT_TIME);
		
	}
	
	public void giveKey(Player p) {
		if(amount==0)
			return;
		Key.giveInvKey(p, eg.getKey(), amount);
	}
	
}
