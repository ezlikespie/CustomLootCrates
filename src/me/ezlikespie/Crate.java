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
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import me.ezlikespie.specialguis.CustomizeGUI;
import me.ezlikespie.specialguis.SelectCrateGUI;
import me.ezlikespie.util.FileHandler;
import me.ezlikespie.util.JsonItemStack;
import me.ezlikespie.util.Message;

public class Crate {

	private Map<Integer, ItemStack> inv;
	private UUID id;
	private String name;
	private UUID key;
	private double miningChance;
	private double entityChance;
	private double fishingChance;
	private List<String> lore = new ArrayList<String>();
	private static Map<EntityType, List<Crate>> entityList = new HashMap<EntityType, List<Crate>>();
	private static Map<Material, List<Crate>> materialList = new HashMap<Material, List<Crate>>();
	private static Map<UUID, List<String>> entityListSave = new HashMap<UUID, List<String>>();
	private static Map<UUID, List<String>> materialListSave = new HashMap<UUID, List<String>>();
	private static Map<UUID, Crate> crates = new HashMap<UUID, Crate>();
	private static Map<Location, UUID> physicalCrates = new HashMap<Location, UUID>();
	private static List<Location> lockedCrates = new ArrayList<Location>();
	
	public Crate() {
		
		inv = new HashMap<Integer, ItemStack>();
		id = UUID.randomUUID();
		crates.put(id, this);
		
	}
	
	public Crate(Map<Integer, ItemStack> items, String _name) {
		
		inv = items;
		name = _name;
		id = UUID.randomUUID();
		crates.put(id, this);
		
	}
	
	public Crate(Map<Integer, ItemStack> items, String _name, UUID _id) {
		
		inv = items;
		name = _name;
		id = _id;
		crates.put(id, this);
		
	}
	
	public static Map<EntityType, List<Crate>> getEntityList(){
		return entityList;
	}
	
	public static List<Crate> getEntityCrates(EntityType e){
		return entityList.get(e);
	}
	
	public static Map<Material, List<Crate>> getMaterialList(){
		return materialList;
	}
	
	public static List<Crate> getMaterialCrates(Material m){
		return materialList.get(m);
	}
	
	public static Map<Location, UUID> getPhysicalCrates(){
		return physicalCrates;
	}
	
	public static boolean isLocked(Location l) {
		return lockedCrates.contains(l);
	}
	
	public static void giveInvCrate(Player p, Crate c, int amount) {
		if(amount>64)
			amount=64;
		InventoryCrate invCrate = new InventoryCrate(c, amount);
		p.getInventory().addItem(invCrate);
		Message.send(p, "&aReceived "+amount+" &f\""+c.getName()+"&r\"&a crate(s)!");
	}
	
	public UUID getId() {
		return id;
	}
	
	public List<String> getLore() {
		return lore;
	}
	
	public void setName(String n) {
		name = n;
	}
	
	public void setKey(UUID k) {
		key = k;
	}
	
	public UUID getKey() {
		return key;
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
	
	public static void removeCrate(UUID id) {
		crates.remove(id);
	}
	
	public Map<Integer, ItemStack> getInventory(){
		return inv;
	}
	
	public void setInventory(Map<Integer, ItemStack> arg) {
		inv = arg;
	}
	
	public static void customize(Player p) {
		
		CustomizeGUI customizer = CustomizeGUI.createGUI();
		p.openInventory(customizer.getInventory());
		
	}
	
	public static void edit(Player p) {
		
		SelectCrateGUI editor = SelectCrateGUI.createGUI();
		p.openInventory(editor.getInventory());
		
	}
	
	public static Set<UUID> getCrates(){
		return crates.keySet();
	}
	
	public static Crate getCrate(UUID crateId) {
		return crates.get(crateId);
	}
	
	public String getName() {
		return name;
	}
	
	public static void saveCrates() {
		
		List<String> lines = new ArrayList<String>();
		Iterator<UUID> it = crates.keySet().iterator();
		while(it.hasNext()) {
			UUID cid = it.next();
			Crate c = crates.get(cid);
			lines.add(cid.toString()+"\n");
			lines.add(c.getName()+"\n");
			lines.add(c.getKey().toString()+"\n");
			lines.add("Lore: "+String.join(", ", ((c.getLore()==null)?new ArrayList<String>(Arrays.asList("")):c.getLore()))+"\n");
			lines.add("Allowed Blocks: "+String.join(", ", materialListSave.containsKey(cid)?materialListSave.get(cid):new ArrayList<String>(Arrays.asList("")))+"\n");
			lines.add("Allowed Entities: "+String.join(", ", entityListSave.containsKey(cid)?entityListSave.get(cid):new ArrayList<String>(Arrays.asList("")))+"\n");
			lines.add("Mining Chance: "+String.valueOf(c.getMiningChance())+"\n");
			lines.add("Entity Chance: "+String.valueOf(c.getEntityChance())+"\n");
			lines.add("Fishing Chance: "+String.valueOf(c.getFishingChance())+"\n");
			Map<Integer, ItemStack> items = c.getInventory();
			Iterator<Integer> itemsIt = items.keySet().iterator();
			while(itemsIt.hasNext()) {
				int pos = itemsIt.next();
				ItemStack currentItem = items.get(pos);
				if(currentItem==null)
					continue;
				lines.add(pos+":"+JsonItemStack.toJson(currentItem)+"\n");
			}
			lines.add("\n");
		}
		
		FileHandler.write(new File(Main.getInstance().getDataFolder().getAbsolutePath()+"/crates.txt"), lines.toArray(new String[0]));
		
		lines = new ArrayList<String>();
		
		Iterator<Location> locIt = physicalCrates.keySet().iterator();
		while(locIt.hasNext()) {
			Location loc = locIt.next();
			lines.add(loc.getWorld().getName()+","+loc.getBlockX()+","+loc.getBlockY()+","+loc.getBlockZ()+"\n");
			lines.add(physicalCrates.get(loc).toString()+"\n");
		}
		
		FileHandler.write(new File(Main.getInstance().getDataFolder().getAbsoluteFile()+"/crateLocs.txt"), lines.toArray(new String[0]));
		
	}
	
	public void setLore(List<String> l) {
		lore = l;
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
	
	public static void loadCrates() {
		
		String[] lines = FileHandler.readLines(new File(Main.getInstance().getDataFolder().getAbsolutePath()+"/crates.txt"));
		boolean going = false;
		UUID loadedId = null;
		String crateName = null;
		UUID keyId = null;
		List<String> lore = null;
		List<Material> mat = new ArrayList<Material>();
		List<String> matStrings = new ArrayList<String>();
		List<EntityType> ent = new ArrayList<EntityType>();
		List<String> entStrings = new ArrayList<String>();
		double miningChance = 0;
		double entityChance = 0;
		double fishingChance = 0;
		Map<Integer, ItemStack> items = new HashMap<Integer, ItemStack>();
		for(int i = 0; i<lines.length; i++) {
			String line = lines[i];
			if(line.equals("")) {
				if(loadedId!=null&&!crates.containsKey(loadedId)) {
					Crate newC = new Crate(items, crateName, loadedId);
					newC.setKey(keyId);
					materialListSave.put(loadedId, matStrings);
					entityListSave.put(loadedId, entStrings);
					for(Material m : mat) {
						if(materialList.containsKey(m))
							materialList.get(m).add(newC);
						else
							materialList.put(m, Arrays.asList(newC));
					}
					for(EntityType e : ent) {
						if(entityList.containsKey(e))
							entityList.get(e).add(newC);
						else
							entityList.put(e, Arrays.asList(newC));
					}
					newC.setLore(lore);
					newC.setMiningChance(miningChance);
					newC.setEntityChance(entityChance);
					newC.setFishingChance(fishingChance);
					crates.put(loadedId, newC);
				}
				going = false;
				continue;
			}
			if(!going) {
				loadedId = UUID.fromString(line);
				crateName = lines[++i];
				keyId = UUID.fromString(lines[++i]);
				
				//Crate Lore
				lore = Arrays.asList(lines[++i].substring(5).trim().split(","));
				for(int li = 0; li<lore.size(); li++)
					lore.set(li, lore.get(li).trim());
				//========== Crate Allowed Blocks ==========
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
							Bukkit.getLogger().log(Level.WARNING, "[CustomLootCrates] Could not load allowed block in crate "+crateName);
						}
					}
				}
				//========== Crate Allowed Entities ===========
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
							Bukkit.getLogger().log(Level.WARNING, "[CustomLootCrates] Could not load allowed entity in crate "+crateName);
						}
					}
				}
				//======== Key Chance Values ========
				try {
					miningChance = Double.valueOf(lines[++i].substring(14).trim());
					entityChance = Double.valueOf(lines[++i].substring(14).trim());
					fishingChance = Double.valueOf(lines[++i].substring(15).trim());
				} catch(Exception e) {
					Bukkit.getLogger().log(Level.WARNING, "[CustomLootCrates] Could not load config for crate "+crateName);
				}
				
				going=true;
				items = new HashMap<Integer, ItemStack>();
				continue;
			} else {
				int semi = line.indexOf(":");
				items.put(Integer.valueOf(line.substring(0,semi)), JsonItemStack.fromJson(line.substring(semi+1,line.length())));
			}
		}
		
		lines = FileHandler.readLines(new File(Main.getInstance().getDataFolder().getAbsolutePath()+"/crateLocs.txt"));
		for(int i = 0; i<lines.length; i++) {
			String[] loc = lines[i].split(",");
			Location l = new Location(Bukkit.getWorld(loc[0]), Double.valueOf(loc[1]), Double.valueOf(loc[2]), Double.valueOf(loc[3]));
			physicalCrates.put(l, UUID.fromString(lines[++i]));
		}
		
	}
	
	public static void openCrate(Player p, Crate c, Location l) {
		physicalCrates.remove(l);
		lockCrate(l);
		particleShow(l, c, p);
	}
	
	public static void lockCrate(Location l) {
		
		lockedCrates.add(l);
		
	}
	
	public static void unlockCrate(Location l) {
		
		lockedCrates.remove(l);
		
	}
	
	public static void particleShow(Location l, Crate c, Player p) {
		
		Location loc = l.clone().add(0.5, 0, 0.5);
		
		new BukkitRunnable() {
			
			int count = 0;
			int layer = 0;
			int newTot = 128;
			public void run() {
				
				if(count%20==0)
					for(Player cp : l.getWorld().getPlayers())
						cp.playSound(l, Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 1, 1);
				
				if(count>=newTot+3)
					done();
				
				//big boom
				if(count>=newTot) {
					Iterator<Player> it = loc.getWorld().getPlayers().iterator();
					double size = 1.5+(count-newTot)*0.5;
					while(it.hasNext()) {
						Player cp = it.next();
						for(double i = 0; i<32; i++) {
							int numParts = 1+(int)Math.round(Math.sin(Math.PI*i/31.0)*31);
							double y = 0.5+Math.cos(Math.PI*i/31.0)*size;
							for(double n = 0; n<numParts; n++) {
								double x = Math.cos(Math.PI*2*n/(numParts-1))*size*numParts/32.0;
								double z = Math.sin(Math.PI*2*n/(numParts-1))*size*numParts/32.0;
								cp.spawnParticle(Particle.DRAGON_BREATH, loc.getX()+x, loc.getY()+y, loc.getZ()+z, 1, 0, 0, 0, 0);
							}
						}
						for(double i = 0; i<32; i++) {
							double x = Math.cos(Math.PI*2*i/31.0)*(size+0.25);
							double z = Math.sin(Math.PI*2*i/31.0)*(size+0.25);
							double y = 0.5;
							cp.spawnParticle(Particle.FLAME, loc.getX()+x, loc.getY()+y, loc.getZ()+z, 1, 0, 0, 0, 0);
						}
					}
					count++;
					return;
				}
				
				double x = Math.cos(count*2.0*Math.PI/32)*(1-layer/8.0);
				double x2 = Math.cos(Math.PI + count*2.0*Math.PI/32)*(1-layer/8.0);
				double y = 3.5-3.0*count/128;
				double z = Math.sin(count*2.0*Math.PI/32)*(1-layer/8.0);
				double z2 = Math.sin(Math.PI + count*2.0*Math.PI/32)*(1-layer/8.0);
				Iterator<Player> it = loc.getWorld().getPlayers().iterator();
				while(it.hasNext()) {
					Player cp = it.next();
					cp.spawnParticle(Particle.FLAME, loc.getX()+x, loc.getY()+y, loc.getZ()+z, 1, 0, 0, 0, 0);
					cp.spawnParticle(Particle.FLAME, loc.getX()+x2, loc.getY()+y, loc.getZ()+z2, 1, 0, 0, 0, 0);
				}
				
				count++;
				
			}
			
			public void done() {
				for(Player cp : l.getWorld().getPlayers())
					cp.playSound(l, Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
				unlockCrate(l);
				l.getBlock().setType(Material.AIR);
				Message.send(p, "&aOpened Crate &f\""+c.getName()+"&r\"");
				Map<Integer, ItemStack> items = c.getInventory();
				Iterator<Integer> it = items.keySet().iterator();
				while(it.hasNext())
					p.getInventory().addItem(items.get(it.next()));
				this.cancel();
			}
			
		}.runTaskTimer(Main.getInstance(), 0, 1);
		
	}
	
}
