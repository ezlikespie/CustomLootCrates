package me.ezlikespie;

import java.io.File;

import org.bukkit.plugin.java.JavaPlugin;

import me.ezlikespie.util.FileHandler;

public class Main extends JavaPlugin {

	private static Main instance;
	
	@Override
	public void onEnable() {
		instance = this;
		new CustomLootCrates();
		new CrateListener();
		if(!FileHandler.exists(getDataFolder()))
			FileHandler.newDir(getDataFolder());
		File cratesFile = new File(getDataFolder().getAbsolutePath()+"/crates.txt");
		File crateLocs = new File(getDataFolder().getAbsolutePath()+"/crateLocs.txt");
		File keysFile = new File(getDataFolder().getAbsolutePath()+"/keys.txt");
		if(!FileHandler.exists(cratesFile))
			FileHandler.newFile(cratesFile);
		if(!FileHandler.exists(crateLocs))
			FileHandler.newFile(crateLocs);
		if(!FileHandler.exists(keysFile))
			FileHandler.newFile(keysFile);
		Crate.loadCrates();
		Key.loadKeys();
	}
	
	@Override
	public void onDisable() {
		Crate.saveCrates();
		Key.saveKeys();
	}
	
	public static Main getInstance() {
		return instance;
	}
	
}
