package me.arnaumas.events;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
<<<<<<< HEAD
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
=======
import org.bukkit.Material;
>>>>>>> 62233b12a59bca7914591d72ffe08fb120cce5a7
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
<<<<<<< HEAD
import org.bukkit.event.player.PlayerToggleSneakEvent;
=======
>>>>>>> 62233b12a59bca7914591d72ffe08fb120cce5a7
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
<<<<<<< HEAD
import org.bukkit.scheduler.BukkitRunnable;

import me.arnaumas.UhcMain;
import me.arnaumas.commands.Comandos;
=======

import me.arnaumas.UhcMain;
>>>>>>> 62233b12a59bca7914591d72ffe08fb120cce5a7
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
<<<<<<< HEAD
	
	// SWEEP ATTACK ---> TP   NO ESBORRAR
	public static void efecteParticules1(Player p) {
		final Player player = p;
		final Location loc = player.getLocation();
		new BukkitRunnable(){
			double phi = 0;
			public void run() {
				phi += Math.PI/10;
				for(double theta = 0; theta <= 2*Math.PI; theta += Math.PI/40) {
					double r = 1.5;
					double x = r*Math.cos(theta)*Math.sin(phi);
					double y = r*Math.cos(phi) + 1.5;
					double z = r*Math.sin(theta)*Math.sin(phi);
					loc.add(x,y,z);
					player.getWorld().spawnParticle(Particle.SWEEP_ATTACK,loc,0,0,0,0,1);
					loc.subtract(x,y,z);
				}
				
				if(phi > Math.PI) {
					this.cancel();
				}
			}
						
		}.runTaskTimer(UhcMain.getInstance(), 0, 1);
	}
	
	
	// PROVES
	public static boolean efecteParticules2(Player p) {
		final Player player = p;
		final Location loc = player.getLocation();
		String[] basee = UhcMain.getInstance().getConfig().getString("base").split(";");
		Location base = new Location(p.getWorld(),Integer.parseInt(basee[0]),Integer.parseInt(basee[1]),Integer.parseInt(basee[2]));

		for(int i=0;i<5;i++) {
			final int i2 = i;
			new BukkitRunnable(){
				double r = 0.5;
				double y = 2;
				public void run() {
					player.getWorld().playSound(loc, Sound.ITEM_SHOVEL_FLATTEN, 1, 1);
					if(!compararLocations(loc,player.getLocation())) {
						System.out.println("FORA");
						this.cancel();
						//Abans de sortir canvio les coordenades de loc, per que 
						//els threads ja estan llançats, i si tornes a la posició correcta
						//en el següent thread, llavors ell a l'executar-se per primer cop i comprovar
						//que estas en la posició correcta, no entra en aquest if, i no es fa el cancel() ni el return.
						//Sí o sí els 5 threads ja estan llençats, per tant, la primera iteració de cada thread la farà
						//sempre. Si el jugador es manté en una posició diferent a la inicial, llavors sempre entrarà
						//aquí i s'aniran cancel·lant els threads segons vagin executant-se. Per tant, cal posar una posció
						//remota a loc, de manera que mai més la condició d'aquest if sigui true (per tant false, per que va negat).
						loc.subtract(-9999,0,-9999);
						return;
					}
					for(int i=0;i<255-loc.getBlockY();i++) {
						Location loc2 = new Location(player.getWorld(),loc.getX(),loc.getY()+i,loc.getZ());
						player.getWorld().spawnParticle(Particle.FIREWORKS_SPARK,loc2,0,0,0,0,1);
					}
					for (int degree = 0; degree < 360; degree++) {
					    double radians = Math.toRadians(degree);
					    double x = r*Math.cos(radians);
					    double z = r*Math.sin(radians);
					    loc.add(x,y,z);
					    player.getWorld().spawnParticle(Particle.CRIT_MAGIC,loc,0,0,0,0,1);
					    loc.subtract(x,y,z);
					}
					if(y < 0) {
						this.cancel();
					}
					r = r+0.08;
					y = y-0.2;
					if(i2==4) {
						p.teleport(base);
					}
				}
			}.runTaskTimer(UhcMain.getInstance(), 30*i, 3);
		}
		return true;
	}
	
	private static boolean compararLocations(Location l1, Location l2) {
		int l1X = l1.getBlockX();
		int l1Z = l1.getBlockZ();
		int l2X = l2.getBlockX();
		int l2Z = l2.getBlockZ();
		/*System.out.println("l1X: "+l1X
				+"\nl1Z: "+l1Z
				+"\nl2X: "+l2X
				+"\nl2Z: "+l2Z
				);*/
		return l1X == l2X && l1Z == l2Z;
	}
	
	@EventHandler
	public void blank(PlayerToggleSneakEvent event){
		String[] basee = UhcMain.getInstance().getConfig().getString("base").split(";");
		Location base = new Location(event.getPlayer().getWorld(),Integer.parseInt(basee[0]),Integer.parseInt(basee[1]),Integer.parseInt(basee[2]));
		if(event.isSneaking()) // Per que només es cridi un cop
			if(efecteParticules2(event.getPlayer())) {
				
			}
	}
	
	/*
	 * Efecte xulo partícules
	 *  
	*/  
	@EventHandler
	public void efecteParticules3(Player p){
		new BukkitRunnable(){
			double t = Math.PI/4;
			Location loc = p.getLocation();
			public void run(){
				t = t + 0.1*Math.PI;
				for (double theta = 0; theta <= 2*Math.PI; theta = theta + Math.PI/32){
					double x = t*Math.cos(theta);
					double y = 2*Math.exp(-0.1*t) * Math.sin(t) + 1.5;
					double z = t*Math.sin(theta);
					loc.add(x,y,z);
					p.getWorld().spawnParticle(Particle.FIREWORKS_SPARK,loc,0,0,0,0,1);
					loc.subtract(x,y,z);
					
					theta = theta + Math.PI/64;
					
					x = t*Math.cos(theta);
					y = 2*Math.exp(-0.1*t) * Math.sin(t) + 1.5;
					z = t*Math.sin(theta);
					//loc.add(x,y,z);
					//p.getWorld().spawnParticle(Particle.SPELL_WITCH,loc,0,0,0,0,1);
					//loc.subtract(x,y,z);
					
				}
				if (t > 20){
					this.cancel();
				}
			}
						
		}.runTaskTimer(UhcMain.getInstance(), 0, 1);
	}
=======
>>>>>>> 62233b12a59bca7914591d72ffe08fb120cce5a7

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
<<<<<<< HEAD
	     } else if(action == Action.LEFT_CLICK_AIR) {
	    	 EquipmentSlot e = event.getHand();
		     if (!e.equals(EquipmentSlot.HAND)) { // HAND és la mà dreta i OFF_HAND l'esquerre.  
		    	 return;
		     }
		     if(item != null && item.getType() == Material.ENDER_EYE) {
	        	 RaycastCommand.tpUllEnder(event.getPlayer());
	         }
=======
>>>>>>> 62233b12a59bca7914591d72ffe08fb120cce5a7
	     }

	}
	
	@EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Bukkit.broadcastMessage(UhcMain.color("§aBon dia tingui "+event.getPlayer().getName()));
<<<<<<< HEAD
        Comandos.skin(event.getPlayer());
=======
>>>>>>> 62233b12a59bca7914591d72ffe08fb120cce5a7
        //createScoreboard(event.getPlayer());
    }
	
}
