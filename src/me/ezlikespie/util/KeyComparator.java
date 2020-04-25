package me.ezlikespie.util;

import java.util.Comparator;
import java.util.UUID;

import me.ezlikespie.Key;

public class KeyComparator implements Comparator<UUID> {
	
	@Override
	public int compare(UUID id1, UUID id2) {
		return Key.getKey(id1).getName().compareTo(Key.getKey(id2).getName());
	}
	
}
