package me.ezlikespie;

import org.bukkit.entity.Player;

import me.ezlikespie.specialguis.SelectorGUI;
import me.ezlikespie.util.Command;

public class CustomLootCrates extends Command {

	public CustomLootCrates() {
		
		super();
		addName("customlootcrates");
		
	}
	
	public static void select(Player p) {
		
		SelectorGUI selector = SelectorGUI.createGUI();
		p.openInventory(selector.getInventory());
		
	}
	
	
	public void execute(Player p, String[] args) {
		select(p);
	}
	
}
