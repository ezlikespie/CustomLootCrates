package me.ezlikespie.util;

import java.util.Comparator;
import java.util.UUID;

import me.ezlikespie.Crate;

public class CrateComparator implements Comparator<UUID> {
	
	@Override
	public int compare(UUID id1, UUID id2) {
		return Crate.getCrate(id1).getName().compareTo(Crate.getCrate(id2).getName());
	}
	
}
