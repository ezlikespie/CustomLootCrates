package me.ezlikespie.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Message {

	public static String trans(String... args) {
		String msg = "";
		for(String s : args) 
			msg+=s;
		return ChatColor.translateAlternateColorCodes('&', msg);
	}
	
	public static void log(String... args) {
		Bukkit.getServer().getConsoleSender().sendMessage(Message.trans(args));
	}
	
	public static String combine(String... args) {
		String msg = "";
		for(String s : args)
			msg+=s;
		return msg;
	}
	
	public static void send(Player p, Boolean doTrans, String... args) {
		String msg = (doTrans)?Message.trans(args):Message.combine(args);
		p.sendMessage(msg);
	}
	public static void send(Player p, String... args) {
		Message.send(p, true, args);
	}
	
}
