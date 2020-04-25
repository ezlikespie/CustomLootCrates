package me.ezlikespie;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import me.ezlikespie.util.Message;

public class CrateListener implements Listener {

	public CrateListener() {
		Bukkit.getServer().getPluginManager().registerEvents(this, Main.getInstance());
	}
	
	@EventHandler
	public void blockPlace(BlockPlaceEvent e) {
		if(e.isCancelled())
			return;
		PersistentDataContainer cont = e.getItemInHand().getItemMeta().getPersistentDataContainer();
		NamespacedKey nk = new NamespacedKey(Main.getInstance(), "Crate");
		NamespacedKey nk2 = new NamespacedKey(Main.getInstance(), "Key");
		if(cont.has(nk2, PersistentDataType.STRING)) {
			e.setCancelled(true);
			return;
		}
		if(!cont.has(nk, PersistentDataType.STRING))
			return;
		UUID id = UUID.fromString(cont.get(nk, PersistentDataType.STRING));
		Player p = e.getPlayer();
		if(!Crate.getCrates().contains(id)) {
			Message.send(p, "&cCrate No Longer Exists!");
			return;
		}
		Block b = e.getBlock();
		Crate.getPhysicalCrates().put(b.getLocation(), id);
	}
	
	@EventHandler
	public void blockBreak(BlockBreakEvent e) {
		if(e.isCancelled())
			return;
		if(!Crate.getPhysicalCrates().containsKey(e.getBlock().getLocation())) {
			miningEvent(e);
			return;
		}
		e.setDropItems(false);
		e.getPlayer().getInventory().addItem(new InventoryCrate(Crate.getCrate(Crate.getPhysicalCrates().get(e.getBlock().getLocation())), 1));
	}
	
	@EventHandler
	public void blockRightClick(PlayerInteractEvent e) {
		if(!e.getAction().equals(Action.RIGHT_CLICK_BLOCK))
			return;
		if(!Crate.getPhysicalCrates().containsKey(e.getClickedBlock().getLocation()))
			return;
		e.setCancelled(true);
		Player p = e.getPlayer();
		ItemStack item = e.getItem();
		Crate c = Crate.getCrate(Crate.getPhysicalCrates().get(e.getClickedBlock().getLocation()));
		if(item==null) {
			Message.send(p, "&cThis Crate Requires Key &f\""+Key.getKey(c.getKey()).getName()+"&r\"");
			return;
		}
		PersistentDataContainer cont = item.getItemMeta().getPersistentDataContainer();
		NamespacedKey nk = new NamespacedKey(Main.getInstance(), "Key");
		if(!cont.has(nk, PersistentDataType.STRING)) {
			Message.send(p, "&cThis Crate Requires Key &f\""+Key.getKey(c.getKey()).getName()+"&r\"");
			return;
		}
		UUID id = UUID.fromString(cont.get(nk, PersistentDataType.STRING));
		
		if(!id.equals(c.getKey())) {
			Message.send(p, "&cThis Crate Requires Key &f\""+Key.getKey(c.getKey()).getName()+"&r\"");
			return;
		}
		int amount = item.getAmount();
		p.getInventory().remove(item);
		p.getInventory().addItem(new InventoryKey(Key.getKey(id), amount-1));
		Crate.openCrate(p, c, e.getClickedBlock().getLocation());
	}
	
	@EventHandler
	public void lockedInteract(PlayerInteractEvent e) {
		if(e.getClickedBlock()==null)
			return;
		if(Crate.isLocked(e.getClickedBlock().getLocation()))
			e.setCancelled(true);
	}
	
	public void miningEvent(BlockBreakEvent e) {
		List<Crate> crates = Crate.getMaterialCrates(e.getBlock().getType());
		List<Key> keys = Key.getMaterialKeys(e.getBlock().getType());
		if(crates==null&&keys==null)
			return;
		Random r = new Random();
		if(crates!=null)
			for(Crate c : crates) {
				if(r.nextDouble()>c.getMiningChance())
					continue;
				Crate.giveInvCrate(e.getPlayer(), c, 1);
			}
		if(keys!=null)
			for(Key k : keys) {
				if(r.nextDouble()>k.getMiningChance())
					continue;
				Key.giveInvKey(e.getPlayer(), k, 1);
			}
	}
	
	@EventHandler
	public void killMob(EntityDeathEvent e) {
		Player p = e.getEntity().getKiller();
		if(p==null)
			return;
		List<Crate> crates = Crate.getEntityCrates(e.getEntityType());
		List<Key> keys = Key.getEntityKeys(e.getEntityType());
		if(crates==null&&keys==null)
			return;
		Random r = new Random();
		if(crates!=null) 
			for(Crate c : crates) {
				if(r.nextDouble()>c.getEntityChance())
					continue;
				Crate.giveInvCrate(p, c, 1);
			}
		if(keys!=null)
			for(Key k : keys) {
				if(r.nextDouble()>k.getMiningChance())
					continue;
				Key.giveInvKey(p, k, 1);
			}
	}
	
	@EventHandler
	public void playerFish(PlayerFishEvent e) {
		if(e.isCancelled())
			return;
		if(!e.getState().equals(PlayerFishEvent.State.REEL_IN))
			return;
		if(!e.getHook().getLocation().getBlock().getType().equals(Material.WATER))
			return;
		Random r = new Random();
		for(UUID id : Crate.getCrates()) {
			Crate c = Crate.getCrate(id);
			if(r.nextDouble()>c.getFishingChance())
				continue;
			Crate.giveInvCrate(e.getPlayer(), c, 1);
		}
		for(UUID id : Key.getKeys()) {
			Key k = Key.getKey(id);
			if(r.nextDouble()>k.getFishingChance())
				continue;
			Key.giveInvKey(e.getPlayer(), k, 1);
		}
	}
	
}
