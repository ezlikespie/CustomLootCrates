package me.ezlikespie.specialitems.crate_editor;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import me.ezlikespie.Crate;
import me.ezlikespie.Main;
import me.ezlikespie.specialguis.EditCrateGUI;
import me.ezlikespie.util.ActionItem;
import me.ezlikespie.util.Message;
import me.ezlikespie.util.WaitResponse;

public class CrateEditorEditNameItem extends ActionItem {

	private EditCrateGUI eg;
	
	public CrateEditorEditNameItem(EditCrateGUI _eg) {
		super(Material.OAK_SIGN, 1);
		ItemMeta nameMeta = getItemMeta();
		nameMeta.setDisplayName(Message.trans("&e&lChange Name"));
		nameMeta.setLore(Arrays.asList(Message.trans("&7Click to Change Name")));
		setItemMeta(nameMeta);
		eg = _eg;
	}
	
	private boolean nameChanged;
	private String newName;
	
	public void setName(String n) {
		nameChanged=true;
		newName = n;
	}
	
	public void click(InventoryClickEvent e, Player p) {
		Message.send(p, "&eEnter a New Crate Name:");
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
		Crate c = eg.getCrate();
		String name = c.getName();
		c.setName(newName);
		Message.send(p, "&aChanged name from &f\""+name+"&r&f\"&a to &f\""+newName+"&r&f\"");
	}
	
}
