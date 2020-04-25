package me.ezlikespie.specialitems.customizer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import me.ezlikespie.Crate;
import me.ezlikespie.Main;
import me.ezlikespie.specialguis.CustomizeGUI;
import me.ezlikespie.util.ActionItem;
import me.ezlikespie.util.Message;
import me.ezlikespie.util.WaitResponse;

public class CustomizerCreateCrateItem extends ActionItem {

	private CustomizeGUI cg;
	
	public CustomizerCreateCrateItem(CustomizeGUI _cg) {
		super(Material.EMERALD_BLOCK, 1);
		ItemMeta createCrateMeta = getItemMeta();
		createCrateMeta.setDisplayName(Message.trans("&e&lCreate Crate"));
		setItemMeta(createCrateMeta);
		cg = _cg;
	}
	
	private boolean crateCreated = false;
	private String crateName;
	private Map<Integer, ItemStack> inv;
	private Player creatingPlayer;
	
	public void setName(String val) {
		crateName = val;
		crateCreated = true;
	}
	
	public void click(InventoryClickEvent e, Player p) {
		e.setCancelled(true);
		
		if(cg.getKeyId()==null) {
			Message.send(p, "&cYou must select a key first!");
			ItemStack selectKey = e.getCurrentItem();
			ItemMeta selectKeyMeta = selectKey.getItemMeta();
			selectKeyMeta.setLore(Arrays.asList(Message.trans("&cMust Select Key")));
			selectKey.setItemMeta(selectKeyMeta);
			return;
		}
		
		//Close Inventory
		new BukkitRunnable() {
			public void run() {
				p.closeInventory();
				cancel();
			}
		}.runTaskLater(Main.getInstance(), 1);
		
		Message.send(p, "&eEnter a Crate Name:");
		WaitResponse waiter = new WaitResponse(p);
		creatingPlayer = p;
		Inventory original = e.getClickedInventory();
		inv = new HashMap<Integer, ItemStack>();
		for(int i = 0; i<27; i++) {
			ItemStack currentItem = original.getItem(i);
			if(currentItem!=null)
				inv.put(i, currentItem);
		}
		
		waiter.setWatcher(this);
		new BukkitRunnable() {
			public void run() {
				if(crateCreated)
					return;
				Message.send(p, "&cSorry, you waited too long. Crate creation cancelled");
				waiter.setActive(false);
			}
		}.runTaskLater(Main.getInstance(),WaitResponse.WAIT_TIME);
	}
	
	public void createCrate() {
		Crate c = new Crate(inv, crateName);
		c.setKey(cg.getKeyId());
		Message.send(creatingPlayer, "&aCrate &f\""+crateName+"&r\" &acreated");
	}
	
}
