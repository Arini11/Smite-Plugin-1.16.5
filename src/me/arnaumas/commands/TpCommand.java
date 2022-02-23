package me.arnaumas.commands;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.arnaumas.UhcMain;

import org.bukkit.World;

public class TpCommand {
	private static CommandSender staticSender;
	private static World world;
	
	public static void run(CommandSender sender) {
		staticSender = sender;
		if (!(sender instanceof Player)) {
			sender.sendMessage("[ERROR] No es pot executar des de consola.");
			return;
		}
		Player p = (Player) sender;
		world = p.getWorld();
		sender.sendMessage( "§4Fiuuum" );
		try {
				 
			for(double i=0;i<3;i=i+0.05) {
				new BukkitRunnable() {					 
					@SuppressWarnings("deprecation")
					@Override
					public void run() { 
						p.teleport(new Location(world,p.getLocation().getX(),
								p.getLocation().getY(),
								p.getLocation().getZ() - 0.05,
								p.getLocation().getYaw(),
								p.getLocation().getPitch()));
						p.spawnParticle(Particle.SNOWBALL,p.getLocation(),6,0.3,0.3,0.3,50);
				    }
				}.runTaskLater(UhcMain.getInstance(), (long) (5+ 2*i));				
			}
			sender.sendMessage( "§4Fiuuum" );
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void ulti(CommandSender sender) {
		staticSender = sender;
		if (!(sender instanceof Player)) {
			sender.sendMessage("[ERROR] No es pot executar des de consola.");
			return;
		}
		Player p = (Player) sender;
		world = p.getWorld();
		sender.sendMessage( "§4ULTI" );
		try {
			List<Location> coords = getCoordsSetblocks(p.getLocation(), 'N', 5);
			animacio(coords);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param coords LLista de posicions on es farà l'animació
	 */
	static void animacio(List<Location> coords) {
		new BukkitRunnable() {
			/*
			 * -1 = no s'ha executat
			 *  1 = s'ha executat
			 *  0 = s'ha acabat d'executar
			 */
			int estat = -1;
			@Override
			public void run() {
				
				if(estat == 0) {
					this.cancel();
					return;
				} else if(estat == -1) {
					estat = 1;
					// setblock del material
					for(int i=0;i<coords.size();i++) {
						final int i2 = i;					
						new BukkitRunnable() {
							@Override
							public void run(){
								Location locAnt = coords.get(i2);
								// Setblocks
								//coords.get(i2).getBlock().setType(Material.STONE);
								// locAnt ha de ser -1, no ho puc manternir aquí pq es reestableix tota la estona
								
								locAnt.setZ(coords.get(i2).getZ() + 1);
								//locAnt.getBlock().setType(Material.GLASS);
								
								// Partícules
								world.spawnParticle(Particle.LAVA,coords.get(i2),15,0.3,0.3,0.3,50);
								// Mirar si exsiteix un mob
								for(Entity e: world.getNearbyEntities(coords.get(i2), 4, 4, 4)) {
									if(e instanceof LivingEntity) {
										if(i2 == coords.size()-1) {
											((LivingEntity) e).damage(999);
										}
										if(compararLocations(e.getLocation(),coords.get(i2))){
											((LivingEntity) e).damage(999);
										}
									}
								}
								if(i2 == coords.size()-1) {
									//coords.get(4).getBlock().setType(Material.GLOWSTONE);
									/*
									Bukkit.broadcastMessage("i2: "+4);
									Bukkit.broadcastMessage("coords: "+coords.get(4));
									Bukkit.broadcastMessage("matnr: "+coords.get(4).getBlock().getType());
									Bukkit.broadcastMessage("0-> "+coords.get(0).toString());
									Bukkit.broadcastMessage("1->"+coords.get(1).toString());
									Bukkit.broadcastMessage("2->"+coords.get(2).toString());
									Bukkit.broadcastMessage("3->"+coords.get(3).toString());
									Bukkit.broadcastMessage("4->"+coords.get(4).toString());
									*/
								}
							}

							private boolean compararLocations(Location l1, Location l2) {
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
							
						}.runTaskLater(UhcMain.getInstance(), 2+i2*3);
						
					}
				} else if(estat == 1) {
					estat = 0;
				}
		    }

		}.runTaskTimer(UhcMain.getInstance(), 0, 45); // 20 ticks = 1 segon
	}
	
	/**
	 * 
	 * @param l1 Coords inicials (jugador)
	 * @param f N, S, W, E (direcció on s'està mirant)
	 * @param n Allargada en nº de blocs de l'animació 
	 * @return
	 */
	private static List<Location> getCoordsSetblocks(Location l1, char f, int n) {
		List<Location> coords = new ArrayList<Location>();
		double x,y,z;
		y = l1.getY() + 1;
		if(f == 'N') {
			//restar Z
			z = l1.getZ() - 1; // -1 per què no comenci just al bloc del jugador
			while(n-- > 0) {
				coords.add(new Location(world, l1.getX(), y, z));
				z--;
			}
		} else if(f == 'S') {
			//sumar Z
			z = l1.getZ();
			while(n-- > 0) {
				coords.add(new Location(world, l1.getX(), y, z--));
			}
		} else if(f == 'E') {
			//sumar X
			x = l1.getX();
			while(n-- > 0) {
				coords.add(new Location(world, x++, y, l1.getZ()));
			}
		}else if(f == 'W') {
			//restar X
			x = l1.getX();
			while(n-- > 0) {
				coords.add(new Location(world, x--, y, l1.getZ()));
			}
		}
		
		//Bukkit.broadcastMessage("coords originals"+coords.toString());
		return coords;
		
	}
}
