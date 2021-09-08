package me.arnaumas.events;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.arnaumas.UhcMain;
import me.arnaumas.items.ItemManager;
import me.arnaumas.match.DeathManager;
import me.arnaumas.match.ScorebrdManager;


public class EventsListerner implements Listener {
	
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
                player.sendMessage("Â§aGulden head luuuuul");
    		}
    }
	
	@EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Bukkit.broadcastMessage(UhcMain.color("§aBon dia tingui "+event.getPlayer().getName()));
        //createScoreboard(event.getPlayer());
    }
}
