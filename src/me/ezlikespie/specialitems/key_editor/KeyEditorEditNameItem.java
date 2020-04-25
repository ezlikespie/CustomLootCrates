package me.ezlikespie.specialitems.key_editor;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import me.ezlikespie.Key;
import me.ezlikespie.Main;
import me.ezlikespie.specialguis.EditKeyGUI;
import me.ezlikespie.util.ActionItem;
import me.ezlikespie.util.Message;
import me.ezlikespie.util.WaitResponse;

public class KeyEditorEditNameItem extends ActionItem {
	
	private EditKeyGUI ek;
	
	public KeyEditorEditNameItem(EditKeyGUI _ek) {
		
		super(Material.OAK_SIGN, 1);
		ItemMeta signMeta = getItemMeta();
		signMeta.setDisplayName(Message.trans("&e&lChange Name"));
		signMeta.setLore(Arrays.asList(Message.trans("&7Click to Change Name")));
		setItemMeta(signMeta);
		ek = _ek;
	}
	
	private boolean nameChanged;
	private String newName;
	
	public void setName(String n) {
		newName = n;
		nameChanged = true;
	}
	
	public void click(InventoryClickEvent e, Player p) {
		Message.send(p, "&eEnter a New Key Name:");
		WaitResponse w = new WaitResponse(p);
		w.setWatcher(this);
		//Close Inventory
		new BukkitRunnable() {
			public void run() {
				p.closeInventory();
				cancel();
			}
		}.runTaskLater(Main.getInstance(), 1);
		new BukkitRunnable() {
			public void run() {
				if(nameChanged)
					return;
				Message.send(p, "&cSorry, you waited too long. Name change cancelled");
				w.setActive(false);
			}
		}.runTaskLater(Main.getInstance(), WaitResponse.WAIT_TIME);
	}
	
	public void changeName(Player p) {
		Key k = ek.getKey();
		String name = k.getName();
		k.setName(newName);
		Message.send(p, "&aChanged name from &f\""+name+"&r&f\"&a to &f\""+newName+"&r&f\"");
	}
	
}
