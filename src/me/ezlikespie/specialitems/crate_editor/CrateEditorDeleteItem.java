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

public class CrateEditorDeleteItem extends ActionItem {

	private EditCrateGUI eg;
	
	public CrateEditorDeleteItem(EditCrateGUI _eg) {
		super(Material.BARRIER, 1);
		ItemMeta deleteMeta = getItemMeta();
		deleteMeta.setDisplayName(Message.trans("&c&lDelete Crate"));
		deleteMeta.setLore(Arrays.asList(Message.trans("&7Click to Delete Crate")));
		setItemMeta(deleteMeta);
		eg = _eg;
	}
	
	public void click(InventoryClickEvent e, Player p) {
		Crate.removeCrate(eg.getCrate().getId());
		Message.send(p, "&cDeleted Crate &f\""+eg.getCrate().getName()+"&r&f\"");
		//Close Inventory
		new BukkitRunnable() {
			public void run() {
				p.closeInventory();
				cancel();
			}
		}.runTaskLater(Main.getInstance(), 1);
	}
	
}
