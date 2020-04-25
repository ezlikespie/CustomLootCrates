package me.ezlikespie.specialguis;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.ezlikespie.Crate;
import me.ezlikespie.Key;
import me.ezlikespie.specialitems.crate_editor.CrateEditorDeleteItem;
import me.ezlikespie.specialitems.crate_editor.CrateEditorEditNameItem;
import me.ezlikespie.specialitems.crate_editor.CrateEditorPreviewItem;
import me.ezlikespie.specialitems.crate_editor.CrateEditorReturnItem;
import me.ezlikespie.specialitems.crate_editor.CrateEditorSaveItem;
import me.ezlikespie.specialitems.crate_editor.CrateEditorSelectKeyItem;
import me.ezlikespie.specialitems.customizer.FillerPanelItem;
import me.ezlikespie.util.ActionItem;
import me.ezlikespie.util.CustomGUI;
import me.ezlikespie.util.KeySelectObserver;
import me.ezlikespie.util.Message;

public class EditCrateGUI extends CustomGUI implements KeySelectObserver {
	
	private Crate crate;
	
	public EditCrateGUI(Inventory i) {
		super(i);
	}
	
	public static EditCrateGUI createGUI(UUID crateId) {
		
		Inventory inv = Bukkit.createInventory(null, 54, "Edit Crate");
		EditCrateGUI editor = new EditCrateGUI(inv);
		
		editor.setCrate(Crate.getCrate(crateId));
		Map<Integer, ItemStack> items = editor.getCrate().getInventory();
		Iterator<Integer> it = items.keySet().iterator();
		while(it.hasNext()) {
			int i = it.next();
			inv.setItem(i, items.get(i));
		}
		
		//======================== Create Action Buttons ===================
		ActionItem saveCrate =  new CrateEditorSaveItem(editor);
		ActionItem selectKey =  new CrateEditorSelectKeyItem(editor);
		ItemMeta selectKeyMeta = selectKey.getItemMeta();
		selectKeyMeta.setLore(Arrays.asList(Message.trans("&7Currently Selected: &r"+Key.getKey(editor.getCrate().getKey()).getName())));
		selectKey.setItemMeta(selectKeyMeta);
		ActionItem returnSel = new CrateEditorReturnItem();
		ActionItem deleteCrate = new CrateEditorDeleteItem(editor);
		ActionItem editName = new CrateEditorEditNameItem(editor);
		ActionItem preview = new CrateEditorPreviewItem(editor);
		
		//==================== Add Gray Panel Filler Items ====================
		ActionItem grayPane = new FillerPanelItem();
		for(int i = 27; i<36; i++)
			inv.setItem(i, grayPane);
		
		//==================== Add Items =====================
		editor.setAction(45, preview);
		editor.setAction(39, saveCrate);
		editor.setAction(40, editName);
		editor.setAction(41, selectKey);
		editor.setAction(49, deleteCrate);
		editor.setAction(53, returnSel);
		
		return editor;
		
	}
	
	public void setCrate(Crate c) {
		crate = c;
	}
	
	public Crate getCrate() {
		return crate;
	}
	
	@Override
	@EventHandler
	public void click(InventoryClickEvent e) {
		if(e.getInventory()==null||e.getInventory().hashCode()!=inv.hashCode())
			return;
		if(!(e.getWhoClicked() instanceof Player))
			return;
		if(e.isShiftClick())
			e.setCancelled(true);
		Player p = (Player) e.getWhoClicked();
		if(27<=e.getSlot()&&e.getSlot()<=53)
			e.setCancelled(true);
		if(!actionItems.containsKey(e.getSlot()))
			return;
		actionItems.get(e.getSlot()).click(e, p);
	}
	
	@Override
	@EventHandler
	public void drag(InventoryDragEvent e) {
		if(e.getInventory()==null||e.getInventory().hashCode()!=inv.hashCode())
			return;
		Set<Integer> slots = e.getInventorySlots();
		Iterator<Integer> it = slots.iterator();
		while(it.hasNext()) {
			int next = it.next();
			if(27<=next&&next<=53) {
				e.setCancelled(true);
				break;
			}
		}
	}
	
	public void selectKey(UUID kid, Player p) {
		crate.setKey(kid);
		p.openInventory(getInventory());
		ItemStack selectKey = getInventory().getItem(41);
		ItemMeta selectKeyMeta = selectKey.getItemMeta();
		selectKeyMeta.setLore(Arrays.asList(Message.trans("&7Currently Selected: &r"+Key.getKey(kid).getName())));
		selectKey.setItemMeta(selectKeyMeta);
	}
	
}
