 package me.ezlikespie;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import me.ezlikespie.specialguis.SelectKeyGUI;
import me.ezlikespie.util.FileHandler;
import me.ezlikespie.util.Message;

public class Key {

	private UUID id;
	private String name;
	private static Map<UUID, Key> keys = new HashMap<UUID, Key>();
	private double miningChance;
	private double entityChance;
	private double fishingChance;
	private List<String> lore = new ArrayList<String>();
	private static Map<EntityType, List<Key>> entityList = new HashMap<EntityType, List<Key>>();
	private static Map<UUID, List<String>> entityListSave = new HashMap<UUID, List<String>>();
	private static Map<Material, List<Key>> materialList = new HashMap<Material, List<Key>>();
	private static Map<UUID, List<String>> materialListSave = new HashMap<UUID, List<String>>();
	
	public Key() {
		
		id = UUID.randomUUID();
		keys.put(id, this);
		
	}
	
	public Key(String _name) {
		
		id = UUID.randomUUID();
		keys.put(id, this);
		name = _name;
		
	}
	
	public Key(String _name, UUID _id) {
		
		id = _id;
		keys.put(id, this);
		name = _name;
		
	}
	
	public double getMiningChance() {
		return miningChance;
	}
	
	public double getEntityChance() {
		return entityChance;
	}
	
	public double getFishingChance() {
		return fishingChance;
	}
	
	public void setLore(List<String> l) {
		lore = l;
	}
	
	public static Map<EntityType, List<Key>> getEntityList(){
		return entityList;
	}
	
	public static List<Key> getEntityKeys(EntityType e){
		return entityList.get(e);
	}
	
	public static Map<Material, List<Key>> getMaterialList(){
		return materialList;
	}
	
	public static List<Key> getMaterialKeys(Material m){
		return materialList.get(m);
	}
	
	public static void giveInvKey(Player p, Key k, int amount) {
		if(amount>64)
			amount=64;
		InventoryKey invKey = new InventoryKey(k, amount);
		p.getInventory().addItem(invKey);
		Message.send(p, "&aReceived "+amount+" &f\""+k.getName()+"&r\"&a key(s)!");
	}
	
	public UUID getId(){
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public List<String> getLore() {
		return lore;
	}
	
	public void setName(String n) {
		name = n;
	}
	
	public static void removeKey(UUID id) {
		keys.remove(id);
	}
	
	public static Set<UUID> getKeys(){
		return keys.keySet();
	}
	
	public static Key getKey(UUID kid) {
		return keys.get(kid);
	}
	
	public static void edit(Player p) {
		
		SelectKeyGUI editor = SelectKeyGUI.createGUI();
		p.openInventory(editor.getInventory());
		
	}
	
	public void setMiningChance(double val) {
		miningChance = val;
	}
	
	public void setEntityChance(double val) {
		entityChance = val;
	}
	
	public void setFishingChance(double val) {
		fishingChance = val;
	}
	
	public static void saveKeys() {
		
		List<String> lines = new ArrayList<String>();
		Iterator<UUID> it = keys.keySet().iterator();
		while(it.hasNext()) {
			UUID kid = it.next();
			Key k = keys.get(kid);
			lines.add(kid.toString()+"\n");
			lines.add(k.getName()+"\n");
			lines.add("Lore: "+String.join(", ", ((k.getLore()==null)?new ArrayList<String>(Arrays.asList("")):k.getLore()))+"\n");
			lines.add("Allowed Blocks: "+String.join(", ", materialListSave.containsKey(kid)?materialListSave.get(kid):new ArrayList<String>(Arrays.asList("")))+"\n");
			lines.add("Allowed Entities: "+String.join(", ", entityListSave.containsKey(kid)?entityListSave.get(kid):new ArrayList<String>(Arrays.asList("")))+"\n");
			lines.add("Mining Chance: "+String.valueOf(k.getMiningChance())+"\n");
			lines.add("Entity Chance: "+String.valueOf(k.getEntityChance())+"\n");
			lines.add("Fishing Chance: "+String.valueOf(k.getFishingChance())+"\n");
			lines.add("\n");
		}
		
		FileHandler.write(new File(Main.getInstance().getDataFolder().getAbsolutePath()+"/keys.txt"), lines.toArray(new String[0]));
		
	}
	
	public static void loadKeys() {
		
		String[] lines = FileHandler.readLines(new File(Main.getInstance().getDataFolder().getAbsolutePath()+"/keys.txt"));
		UUID loadedId = null;
		String keyName = null;
		List<String> lore = null;
		List<Material> mat = new ArrayList<Material>();
		List<String> matStrings = new ArrayList<String>();
		List<EntityType> ent = new ArrayList<EntityType>();
		List<String> entStrings = new ArrayList<String>();
		double miningChance = 0;
		double entityChance = 0;
		double fishingChance = 0;
		for(int i = 0; i<lines.length; i++) {
			String line = lines[i];
			if(line.equals("")) {
				if(loadedId!=null&&!keys.containsKey(loadedId)) {
					Key ck = new Key(keyName, loadedId);
					materialListSave.put(loadedId, matStrings);
					entityListSave.put(loadedId, entStrings);
					for(Material m : mat) {
						if(materialList.containsKey(m))
							materialList.get(m).add(ck);
						else
							materialList.put(m, Arrays.asList(ck));
					}
					for(EntityType e : ent) {
						if(entityList.containsKey(e))
							entityList.get(e).add(ck);
						else
							entityList.put(e, Arrays.asList(ck));
					}
					ck.setLore(lore);
					ck.setMiningChance(miningChance);
					ck.setEntityChance(entityChance);
					ck.setFishingChance(fishingChance);
					keys.put(loadedId, ck);
				}
				continue;
			}
			//Key Id
			loadedId = UUID.fromString(line);
			//Key Name
			keyName = lines[++i];
			//Key Lore
			lore = Arrays.asList(lines[++i].substring(5).trim().split(","));
			for(int li = 0; li<lore.size(); li++)
				lore.set(li, lore.get(li).trim());
			//========== Key Allowed Blocks ==========
			String allowedB = lines[++i].substring(15).trim();
			mat = new ArrayList<Material>();
			if(!allowedB.equals("")) {
				String[] allowedBs = allowedB.split(",");
				for(String b : allowedBs) {
					try {
						Material _b = Enum.valueOf(Material.class, b.trim().toUpperCase());
						matStrings.add(b.toString());
						mat.add(_b);
					} catch(Exception e) {
						Bukkit.getLogger().log(Level.WARNING, "[CustomLootCrates] Could not load allowed block in key "+keyName);
					}
				}
			}
			//========== Key Allowed Entities ===========
			String allowedE = lines[++i].substring(17).trim();
			ent = new ArrayList<EntityType>();
			if(!allowedE.equals("")) {
				String[] allowedEs = allowedE.split(",");
				for(String e : allowedEs) {
					try {
						EntityType _e = Enum.valueOf(EntityType.class, e.trim().toUpperCase());
						entStrings.add(_e.toString());
						ent.add(_e);
					} catch(Exception ex) {
						Bukkit.getLogger().log(Level.WARNING, "[CustomLootCrates] Could not load allowed entity in key "+keyName);
					}
				}
			}
			//======== Key Chance Values ========
			try {
				miningChance = Double.valueOf(lines[++i].substring(14).trim());
				entityChance = Double.valueOf(lines[++i].substring(14).trim());
				fishingChance = Double.valueOf(lines[++i].substring(15).trim());
			} catch(Exception e) {
				Bukkit.getLogger().log(Level.WARNING, "[CustomLootCrates] Could not load config for key "+keyName);
			}
			
		}

		
	}
	
}
