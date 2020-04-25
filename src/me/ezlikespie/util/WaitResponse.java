package me.ezlikespie.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.ezlikespie.Main;
import me.ezlikespie.specialitems.crate_editor.CrateEditorEditNameItem;
import me.ezlikespie.specialitems.crate_editor.CrateEditorPreviewItem;
import me.ezlikespie.specialitems.customizer.CustomizerCreateCrateItem;
import me.ezlikespie.specialitems.key_editor.KeyEditorEditNameItem;
import me.ezlikespie.specialitems.key_editor.KeyEditorPreviewItem;
import me.ezlikespie.specialitems.selector.SelectorCreateKeyItem;

public class WaitResponse implements Listener {

	private Player waitPlayer;
	private boolean active = true;
	private ActionItem watcher;
	public static final int WAIT_TIME = 400;
	
	public WaitResponse(Player p) {
		
		Bukkit.getServer().getPluginManager().registerEvents(this, Main.getInstance());
		waitPlayer = p;
		
	}
	
	public void setWatcher(ActionItem item) {
		watcher = item;
	}
	
	public void setActive(boolean val) {
		active = val;
	}
	
	@EventHandler
	public void playerChat(AsyncPlayerChatEvent e) {
		
		if(waitPlayer==null||!active)
			return;
		
		Player p = e.getPlayer();
		if(!p.getUniqueId().equals(waitPlayer.getUniqueId()))
			return;
		
		e.setCancelled(true);
		String msg = e.getMessage();
		
		if(!isValid(msg)) {
			Message.send(p, "&cInvalid Name &f\""+msg+"&f\"&c, Try Again");
			return;
		}
		
		if(watcher instanceof CustomizerCreateCrateItem) {
			CustomizerCreateCrateItem _watcher = (CustomizerCreateCrateItem) watcher;
			_watcher.setName(msg);
			_watcher.createCrate();
		} else if(watcher instanceof SelectorCreateKeyItem) {
			SelectorCreateKeyItem _watcher = (SelectorCreateKeyItem) watcher;
			_watcher.setName(msg);
			_watcher.createKey();
		} else if(watcher instanceof CrateEditorEditNameItem) {
			CrateEditorEditNameItem _watcher = (CrateEditorEditNameItem) watcher;
			_watcher.setName(msg);
			_watcher.changeName(p);
		} else if(watcher instanceof KeyEditorEditNameItem) {
			KeyEditorEditNameItem _watcher = (KeyEditorEditNameItem) watcher;
			_watcher.setName(msg);
			_watcher.changeName(p);
		} else if(watcher instanceof CrateEditorPreviewItem) {
			CrateEditorPreviewItem _watcher = (CrateEditorPreviewItem) watcher;
			_watcher.setAmountToGive(msg, p);
			_watcher.giveCrate(p);
		} else if(watcher instanceof KeyEditorPreviewItem) {
			KeyEditorPreviewItem _watcher = (KeyEditorPreviewItem) watcher;
			_watcher.setAmountToGive(msg, p);
			_watcher.giveKey(p);
		}
		active = false;
		
	}
	
	private boolean isValid(String message) {
		return true;
		/*if(message.contains(" "))
			return false;
		return true;*/
	}
	
}
