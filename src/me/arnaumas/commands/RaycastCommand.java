package me.arnaumas.commands;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;

import org.bukkit.block.BlockFace;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import me.arnaumas.UhcMain;

import me.arnaumas.events.EventsFacade;



import org.bukkit.World;

public class RaycastCommand {
	private static CommandSender staticSender;
	private static World world;
	

	public static void tpUllEnder(CommandSender sender) {
		try {
		staticSender = sender;
		Player p = (Player) sender;
		world = p.getWorld();

			Vector dir = p.getLocation().getDirection().normalize();
			Location loc = p.getLocation();
			double t = 0;
			while(true) {
				t += 0.5;
				double x = dir.getX() * t;
				double y = dir.getY() * t + 1.5;
				double z = dir.getZ() * t;
				loc.add(x,y,z);
				if(loc.getBlock().getType() != Material.AIR) {
					EventsFacade f = new EventsFacade();
					f.efecteParticules1(p);
					p.teleport(loc.add(new Vector(0,0.5,0)));
					p.playSound(loc, Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
					f.efecteParticules1(p);
					return;
				}
				loc.subtract(x,y,z);
			}

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	

	public static void proves(CommandSender sender) {
		try {
		staticSender = sender;
		Player p = (Player) sender;
		world = p.getWorld();
		new BukkitRunnable() {
			Vector dir = p.getLocation().getDirection().normalize();
			Location loc = p.getLocation();
			double t = 0;
			public void run() {
				t += 0.5;
				double x = dir.getX() * t;
				double y = dir.getY() * t + 1.5;
				double z = dir.getZ() * t;
				loc.add(x,y,z);
				world.spawnParticle(Particle.VILLAGER_HAPPY,loc,10,0,0,0,5);
				
				for(Entity e : loc.getChunk().getEntities()) {
					if(e.getLocation().distance(loc) < 2.0) {
						if(!e.equals(p)) {
							System.out.println("");
							e.setFireTicks(20*5);
							world.spawnParticle(Particle.EXPLOSION_NORMAL,loc,10,0,0,0,5);
						}
					}
				}
				
				/*loc.getBlock().setType(Material.BLACK_STAINED_GLASS);
				new BukkitRunnable() {
					public void run() {
						loc.getBlock().setType(Material.AIR);
					}
				}.runTaskLater(UhcMain.getInstance(), 2);
				*/
				loc.subtract(x,y,z);
				if(t > 20) {
					this.cancel();
				}
			}
		}.runTaskTimer(UhcMain.getInstance(), 0, 1);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void run(CommandSender sender) {
		staticSender = sender;
		if (!(sender instanceof Player)) {
			sender.sendMessage("[ERROR] No es pot executar des de consola.");
			return;
		}
		Player p = (Player) sender;
		world = p.getWorld();
		try {
				 /*new BukkitRunnable() {

					 BlockFace fAnt = p.getFacing();
					 
					@SuppressWarnings("deprecation")
					@Override
					public void run() {
				    	 
							//sender.sendMessage("iep");
							//sender.sendMessage("fAnt: "+fAnt.toString());
							BlockFace f = p.getFacing(); 
							//sender.sendMessage("f: "+f.toString());
							//if(f != fAnt) {
							if(f == BlockFace.NORTH_WEST)
								sender.sendMessage(f.toString());
								//fAnt = f;
							//}
				    }

				}.runTaskTimerAsynchronously(UhcMain.getInstance(), 0, 5);
				*/
		} catch(Exception e) {
			e.printStackTrace();
		}
		double distancia = 0; // Distancia entre jugador i destí raycast
		Location l1 = p.getLocation(); // Coords jugador
		Location l2; // Coords blocs que s'aniran comprovant fins a trobar un que no sigui aire
		double x,z; // X i Z del jugador, per fer més clar el codi
		BlockFace facing = p.getFacing(); // Direcció a on està mirant el jugador
		Material mat = Material.AIR; // Material del bloc que s'està mirant
		boolean trobat = false; // Flag
		if(facing == BlockFace.NORTH) {
			List<Location> coords = getCoordsSetblocks(l1, 'N', 5);
			animacio(coords);
			//restar Z
			// RAYCAST
			z = l1.getZ();
			while(!trobat){
				l2 = new Location(world,l1.getX(),l1.getY(),z--);
				mat = l2.getBlock().getType();
				if(mat != Material.AIR ) {
					trobat = true;
					distancia = l1.distance(l2);
				}
			}
		} else if(facing == BlockFace.SOUTH) {
			//sumar Z
		} else if(facing == BlockFace.EAST) {
			//sumar X
		}else if(facing == BlockFace.WEST) {
			//restar X
		}
		
		//sender.sendMessage("Bloc: "+mat);
		//sender.sendMessage("Distància: "+distancia);
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
			boolean dmgFet = false;
			@SuppressWarnings("deprecation")
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
							@SuppressWarnings("deprecation")
							@Override
							public void run(){
								// Setblocks
								if(i2 == coords.size()-1)
									coords.get(i2).getBlock().setType(Material.STONE_BRICKS);
								else 
									coords.get(i2).getBlock().setType(Material.STONE_BRICK_SLAB);
								// Partícules
								world.spawnParticle(Particle.LAVA,coords.get(i2),6,0.3,0.3,0.3,50);
								// Mirar si exsiteix un mob
								for(Entity e: world.getNearbyEntities(coords.get(i2), 10, 5, 10)) {
									if(e instanceof LivingEntity) {
										if(compararLocations(e.getLocation(),coords.get(i2))){
											//System.out.println(e.toString());
											if(compararLocations(e.getLocation(),coords.get(coords.size()-1))) {
												e.teleport(new Location(world, e.getLocation().getX(),
														e.getLocation().getY() + 3.5,
														e.getLocation().getZ() + 5));
												
											}
											if(!dmgFet) {
												((LivingEntity) e).damage(4);
												dmgFet = true;
											}
										}
									}
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
					// reset del material a aire
					/*
					 * A TENIR EN COMPTE!!!
					 * Hauria de guardar-me en un hash map o alguna altra cosa
					 * els blocs que hi havia originalment per poder-los restablir
					 * en acabar l'animació. Perquè si no em carregaré el
					 * mapa posant aire on abans potser hi habia uns blocs.
					 */
					for(int i=0;i<coords.size();i++) {
						final int i2 = i;
						new BukkitRunnable() {
							@SuppressWarnings("deprecation")
							@Override
							public void run(){
								coords.get(i2).getBlock().setType(Material.AIR);
							}
							
						}.runTaskLater(UhcMain.getInstance(), 2+i2*3);
					}
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
		double x,z;
		if(f == 'N') {
			//restar Z
			z = l1.getZ() - 1; // -1 per què no comenci just al bloc del jugador
			while(n-- > 0) {
				coords.add(new Location(world, l1.getX(), l1.getY(), z--));
			}
		} else if(f == 'S') {
			//sumar Z
			z = l1.getZ();
			while(n-- > 0) {
				coords.add(new Location(world, l1.getX(), l1.getY(), z--));
			}
		} else if(f == 'E') {
			//sumar X
			x = l1.getX();
			while(n-- > 0) {
				coords.add(new Location(world, x++, l1.getY(), l1.getZ()));
			}
		}else if(f == 'W') {
			//restar X
			x = l1.getX();
			while(n-- > 0) {
				coords.add(new Location(world, x--, l1.getY(), l1.getZ()));
			}
		}
		
		return coords;
		
	}
}
