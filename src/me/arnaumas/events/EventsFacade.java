package me.arnaumas.events;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.arnaumas.UhcMain;
import me.arnaumas.commands.RaycastCommand;
import me.arnaumas.commands.TpCommand;
import me.arnaumas.items.ItemManager;
import me.arnaumas.match.DeathManager;
import me.arnaumas.match.ScorebrdManager;


public class EventsFacade implements Listener {
	
	Map<String, Long> cooldowns = new HashMap<String, Long>();
    
    long lastUsed = 0; // will store currentTimeMillis
    double cooldown = 3; // cooldown in seconds    
	
	@EventHandler
	public void onPlayerDamage(EntityDamageEvent event) {
		if(event.getEntity() instanceof Player) {
			ScorebrdManager.updateHealth();
		}
	}

	@EventHandler
	public void onPlayerHeal(EntityRegainHealthEvent event) {
		if(event.getEntity() instanceof Player) {
			ScorebrdManager.updateHealth();
		}
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		DeathManager death = new DeathManager(event.getEntity(), event.getEntity().getWorld(), event.getDeathMessage());
		death.spawnTumba();
		death.setPlayerGamemode(GameMode.SPECTATOR);
		//event.getEntity().getPlayer().setOp(false); // deop
		//Falta posar death.missatge i death.actualitzarEquips
	}
	
	@EventHandler
    public void onConsume(PlayerItemConsumeEvent event) {
    	if (event.getItem().getItemMeta().equals(ItemManager.goldenHead.getItemMeta())) {
    		Player player = event.getPlayer();
               	player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 200, 1));
                player.sendMessage("Â§aGolden head!");
    		}
    }
	
	@EventHandler
	public void onPlayerClicks(PlayerInteractEvent event) {
	    Player player = event.getPlayer();
	    Action action = event.getAction();
	    ItemStack item = event.getItem();
	    if ( action == Action.RIGHT_CLICK_AIR  || action == Action.RIGHT_CLICK_BLOCK ) {
	    	/*
		     * Com que el jugador té dos mans, onPlayerClicks es crida dues vegades.
		     * La crida que es faci de la mà que no sigui la principal, fa un return
		     * per que no s'executi.
		     */
	    	EquipmentSlot e = event.getHand();
	        if (!e.equals(EquipmentSlot.HAND)) { // HAND és la mà dreta i OFF_HAND l'esquerre.  
	           return;
	        }
	        
	        if ( item != null && item.getType() == Material.STONE_HOE ) { 
			    
			    if (cooldowns.containsKey(event.getPlayer().getName())) {
			      lastUsed = cooldowns.get(event.getPlayer().getName());
			      // if the player has used this item recently, lastUsed becomes last usage
			    }
			    
			    if (System.currentTimeMillis() - lastUsed >= cooldown * 1000) {
			      cooldowns.put(event.getPlayer().getName(), System.currentTimeMillis());
			      // if cooldown has expired, allow event and add cooldown
			    } else {
			      event.setCancelled(true); // cancel event
			      double timeLeft = cooldown - ((System.currentTimeMillis() - lastUsed) / 1000.00);
			      String sTimeLeft = ""+timeLeft;
			      // get time till cooldown expires in seconds
			      event.getPlayer().sendMessage("§8Cooldown: " + sTimeLeft.substring(0, 3) + " segons!");
			      return;
			    }
			    player.sendMessage( "§4Pium pium" );
			    RaycastCommand.run(event.getPlayer());
			    
	         } else if(item != null && item.getType() == Material.WOODEN_HOE) {
	        	 TpCommand.run(event.getPlayer());
	         } else if(item != null && item.getType() == Material.DIAMOND_HOE) {
	        	 TpCommand.ulti(event.getPlayer());
	         } else if(item != null && item.getType() == Material.GOLDEN_HOE) {
	        	 RaycastCommand.proves(event.getPlayer());
	         }
	     }

	}
	
	@EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Bukkit.broadcastMessage(UhcMain.color("§aBon dia tingui "+event.getPlayer().getName()));
        //createScoreboard(event.getPlayer());
    }
	
}
