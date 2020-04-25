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

public class CrateEditorPreviewItem extends ActionItem {

	private EditCrateGUI eg;
	private int amount;
	private boolean amountSelected;
	
	public CrateEditorPreviewItem(EditCrateGUI _eg) {
		super(Material.CHEST, 1);
		ItemMeta previewCrateMeta = getItemMeta();
		previewCrateMeta.setDisplayName(Message.trans("&e&lCurrently Editing: &r"+_eg.getCrate().getName()));
		previewCrateMeta.setLore(Arrays.asList(Message.trans("&7Click to Get Crate")));
		setItemMeta(previewCrateMeta);
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
		
		Message.send(p, "&eChoose a Number of Crates:");
		
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
	
	public void giveCrate(Player p) {
		if(amount==0)
			return;
		Crate.giveInvCrate(p, eg.getCrate(), amount);
	}
	
}
