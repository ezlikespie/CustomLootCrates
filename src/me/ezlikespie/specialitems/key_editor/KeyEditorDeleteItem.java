package me.ezlikespie.specialitems.key_editor;

import java.util.Arrays;
import java.util.Iterator;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import me.ezlikespie.Crate;
import me.ezlikespie.Key;
import me.ezlikespie.Main;
import me.ezlikespie.specialguis.EditKeyGUI;
import me.ezlikespie.util.ActionItem;
import me.ezlikespie.util.Message;

public class KeyEditorDeleteItem extends ActionItem {

	private EditKeyGUI ek;
	
	public KeyEditorDeleteItem(EditKeyGUI _ek) {
		super(Material.BARRIER, 1);
		ItemMeta deleteMeta = getItemMeta();
		deleteMeta.setDisplayName(Message.trans("&c&lDelete Key"));
		deleteMeta.setLore(Arrays.asList(Message.trans("&7Click to Delete Key")));
		setItemMeta(deleteMeta);
		ek=_ek;
	}
	
	public void click(InventoryClickEvent e, Player p) {
		Iterator<UUID> crateIt = Crate.getCrates().iterator();
		while(crateIt.hasNext()) {
			UUID current = crateIt.next();
			if(Crate.getCrate(current).getKey().equals(ek.getKey().getId())) {
				Message.send(p, "&cThis key is currently is use! Unable to delete");
				new BukkitRunnable() {
					public void run() {
						p.closeInventory();
						cancel();
					}
				}.runTaskLater(Main.getInstance(), 1);
				return;
			}
		}
		Key.removeKey(ek.getKey().getId());
		Message.send(p, "&cDeleted Key &f\""+ek.getKey().getName()+"&r&f\"");
		//Close Inventory
		new BukkitRunnable() {
			public void run() {
				p.closeInventory();
				cancel();
			}
		}.runTaskLater(Main.getInstance(), 1);
	}
	
}
