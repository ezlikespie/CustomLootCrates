package me.ezlikespie.util;

import java.util.UUID;

import org.bukkit.entity.Player;

public interface KeySelectObserver {

	public void selectKey(UUID id, Player p);
	
}
