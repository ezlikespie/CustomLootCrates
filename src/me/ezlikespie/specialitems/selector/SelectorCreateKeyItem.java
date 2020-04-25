package me.ezlikespie.specialitems.selector;

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
import me.ezlikespie.util.ActionItem;
import me.ezlikespie.util.Message;
import me.ezlikespie.util.WaitResponse;

public class SelectorCreateKeyItem extends ActionItem {

	private String keyName;
	private boolean keyCreated = false;
	private Player creatingPlayer;
	
	public SelectorCreateKeyItem() {
		super(Material.TRIPWIRE_HOOK, 1);
		ItemMeta createKeyMeta = getItemMeta();
		createKeyMeta.setDisplayName(Message.trans("&e&lCreate Key"));
		createKeyMeta.setLore(Arrays.asList(Message.trans("&7Click to Create a New Key")));
		createKeyMeta.addEnchant(Enchantment.DURABILITY, 1, true);
		createKeyMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		setItemMeta(createKeyMeta);
	}
	
	public void setName(String s) {
		keyName = s;
		keyCreated = true;
	}
	
	public void click(InventoryClickEvent e, Player p) {
		e.setCancelled(true);
		Message.send(p, "&eEnter a Key Name:");
		WaitResponse waiter = new WaitResponse(p);
		
		creatingPlayer = p;
		
		//Close Inventory
		new BukkitRunnable() {
			public void run() {
				p.closeInventory();
				cancel();
			}
		}.runTaskLater(Main.getInstance(), 1);
		
		waiter.setWatcher(this);
		new BukkitRunnable() {
			public void run() {
				if(keyCreated)
					return;
				Message.send(p, "&cSorry, you waited too long. Key creation cancelled");
				waiter.setActive(false);
			}
		}.runTaskLater(Main.getInstance(), WaitResponse.WAIT_TIME);
	}
	
	public void createKey() {
		new Key(keyName);
		Message.send(creatingPlayer, "&aKey &f\""+keyName+"&f\" &acreated");
	}
	
}
