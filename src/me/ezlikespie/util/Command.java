package me.ezlikespie.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import me.ezlikespie.Main;

public abstract class Command implements Listener {

	public Command() {
		Bukkit.getServer().getPluginManager().registerEvents(this, Main.getInstance());
	}
	
	//----------------------------------------------------
	//						Variables
	//----------------------------------------------------
	protected List<String> names = new ArrayList<String>();
	protected List<String[]> permissions = new ArrayList<String[]>();
	protected String errorMsg = Message.trans("&cYou do not have permission to use this command!");
	
	//------------------------------------------------------
	//					Permission Setter
	//------------------------------------------------------
	protected void addPermission(String... args) {
		permissions.add(args);
	}
	protected void setPermissionDeniedMessage(String... args) {
		errorMsg = Message.trans(args);
	}
	
	//------------------------------------------------------
	//					  Name Setter
	//------------------------------------------------------
	protected void addName(String... args) {
		for(String s : args)
			names.add(s);
	}
	
	//------------------------------------------------------
	//					Permission Checker
	//------------------------------------------------------
	protected Boolean hasPermission(Player p) {
		if(permissions.size()==0)
			return true;
		Iterator<String[]> it = permissions.iterator();
		topLoop:
		while(it.hasNext()) {
			String[] subset = it.next();
			subLoop:
			for(String s : subset) {
				if((s.equalsIgnoreCase("op")||s.equalsIgnoreCase("operator"))&&p.isOp())
					continue subLoop;
				else if(p.hasPermission(s))
					continue subLoop;
				continue topLoop;
			}
			return true;
		}
		return false;
	}
	
	//------------------------------------------------------
	//					Command Event
	//------------------------------------------------------
	@EventHandler
	public void command(PlayerCommandPreprocessEvent e) {
		
		//================== Check Name ===============
		String[] msg = e.getMessage().replaceFirst("/", "").split(" ");
		if(!names.contains(msg[0]))
			return;
		e.setCancelled(true);
		
		//Get Player
		Player p = e.getPlayer();
		
		//============== Check Permissions ===============
		if(!hasPermission(p)) {
			if(errorMsg==null||errorMsg.equals(""))
				return;
			Message.send(p, errorMsg);
		}
		
		String[] args = new String[msg.length-1];
		
		for(int i = 1; i<msg.length; i++)
			args[i-1]=msg[i];
		
		execute(p, args);
		
	}
	
	//Main Functionality
	protected abstract void execute(Player p, String[] args);
	
}
