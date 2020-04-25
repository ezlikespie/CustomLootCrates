package me.ezlikespie.specialguis;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import me.ezlikespie.Key;
import me.ezlikespie.specialitems.key_editor.KeyEditorDeleteItem;
import me.ezlikespie.specialitems.key_editor.KeyEditorEditNameItem;
import me.ezlikespie.specialitems.key_editor.KeyEditorPreviewItem;
import me.ezlikespie.specialitems.key_editor.KeyEditorReturnItem;
import me.ezlikespie.util.ActionItem;
import me.ezlikespie.util.CustomGUI;

public class EditKeyGUI extends CustomGUI {

	private Key myKey;
	
	public EditKeyGUI(Inventory i) {
		super(i);
		setClick(true);
		setDrag(true);
	}
	
	public static EditKeyGUI createGUI(UUID keyId) {
		
		Inventory inv = Bukkit.createInventory(null, 54, "Edit Key");
		EditKeyGUI editor = new EditKeyGUI(inv);
		
		editor.myKey = Key.getKey(keyId);
		
		
		ActionItem previewItem = new KeyEditorPreviewItem(editor);
		ActionItem returnSel = new KeyEditorReturnItem();
		ActionItem editName = new KeyEditorEditNameItem(editor);
		ActionItem deleteItem = new KeyEditorDeleteItem(editor);
		
		editor.setAction(22, previewItem);
		editor.setAction(39, editName);
		editor.setAction(41, deleteItem);
		editor.setAction(53, returnSel);
		
		return editor;
		
	}
	
	public Key getKey() {
		return myKey;
	}
	
}
