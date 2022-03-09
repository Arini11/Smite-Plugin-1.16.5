package me.arnaumas.match;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.arnaumas.altres.Range;

public class RandomTeleports {
	
	private final List<Character> relacioCostats = Arrays.asList('N','S','O','E');
	private static final Integer spawnableRadius = 1500 - 20; // -20 ???
	private List<String> teams;
	private int x, z;
	private int equips;
	// Cada equip a quin costat va
	Map<String, Character> costatEquip = new HashMap<String, Character>();
	// Cada equip quines coordenades de spawn ha de tenir
	Map<String, int[]> coords = new HashMap<String, int[]>();
	// Cada costat quants equips ha de tenir
	Map<Character, Integer> nEquipsCostat = new HashMap<Character, Integer>();
	
	enum Costat {
		  NORD,
		  EST,
		  SUD,
		  OEST
		} 

	
	public RandomTeleports() {
		teams = TeamsManager.getInstance().getEquipsLlista();
		this.x = 3000;
		this.z = 3000;
		this.equips = 9;
	}
	
	public void prova() {
		teamsPerCostat();
		assignarCoords();
		//De moment nom�s imprimir per xat
		for(String s : TeamsManager.getInstance().getEquipsLlista()) {
			tpEquips(s);
		}
	}
	
	private void tpEquips(String equip) {
		int x = coords.get(equip)[0];
		int z = coords.get(equip)[1];
		List<String> companys = TeamsManager.getInstance().getCompanysFull(equip);
		if(companys.isEmpty()) {
			return;
		}
		for(String nomJugador: companys) {
			Bukkit.getLogger().info("["+equip+"] Jugador "+nomJugador+" teletransportat a x:"+x+" z:"+z);
			Player p = Bukkit.getServer().getPlayer(nomJugador);
			if(p != null) {
				p.setHealth(20);
				p.setFoodLevel(20);
				p.getInventory().clear();
				p.setGameMode(GameMode.SURVIVAL);
				p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 1000, 99));
				p.teleport(new Location(p.getWorld(),x, 120,z));
			}
		}
		return;
	}

	private void assignarCoords() {
		//Assignar equips a costats
		for (Entry<String, Character> entrada : costatEquip.entrySet()) {
			if(nEquipsCostat.get(new Character('N')) > 0) {
				costatEquip.put(entrada.getKey(), 'N');
				nEquipsCostat.put('N', nEquipsCostat.get('N')-1);
			} else if(nEquipsCostat.get(new Character('S')) > 0) {
				costatEquip.put(entrada.getKey(), 'S');
				nEquipsCostat.put('S', nEquipsCostat.get('S')-1);
			} else if(nEquipsCostat.get(new Character('E')) > 0) {
				costatEquip.put(entrada.getKey(), 'E');
				nEquipsCostat.put('E', nEquipsCostat.get('E')-1);
			} else if(nEquipsCostat.get(new Character('O')) > 0) {
				costatEquip.put(entrada.getKey(), 'O');
				nEquipsCostat.put('O', nEquipsCostat.get('O')-1);
			}
			//Assignar coord a equips
			Range spawnRange = getSpawnRange();
			switch(costatEquip.get(entrada.getKey())) {
				/*
				 * Costat n --> +x +z Nord
				 * Costat e --> +x -z Est
				 * Costat s --> -x +z Sud
				 * Costat o --> -x -z Oest
				 */
				  case 'N':
					  coords.put(entrada.getKey(), new int[] {spawnRange.getRandomInteger(),spawnableRadius});
				    break;
				  case 'E':
					  coords.put(entrada.getKey(), new int[] {spawnableRadius,spawnRange.getRandomInteger()});
				    break;
				  case 'S':
					  coords.put(entrada.getKey(), new int[] {spawnRange.getRandomInteger(),-spawnableRadius});
					  break;
				  case 'O':
					  coords.put(entrada.getKey(), new int[] {-spawnableRadius,spawnRange.getRandomInteger()});
					  break;
				  default:
				    Bukkit.getLogger().severe("ERROR RandomTeleports assignarCoords()");
			}
       }	
	}
	
	private static Range getSpawnRange() {
		return new Range(-spawnableRadius, spawnableRadius);
	}

	private void teamsPerCostat() {
		// Relaci� de cada equip amb un costat
		/*
		 * { equip1 , O } Equip1 Oest
		 * { equip2 , S } Equip2 Sud
		 */
	
		//Inicialitzar
		for(String s : teams) {
			costatEquip.put(s, null);
		}
		nEquipsCostat.put('N', 0);
		nEquipsCostat.put('S', 0);
		nEquipsCostat.put('E', 0);
		nEquipsCostat.put('O', 0);
		//;
		int compt = 0;
		while(equips > 0) {
			//nEquipsPerCostat.set(compt%4, nEquipsPerCostat.get(compt%4)+1);
			Character c = relacioCostats.get(compt%4);
			nEquipsCostat.put(c, nEquipsCostat.get(c)+1);
			equips--;
			compt++;
		}
		System.out.println(nEquipsCostat.get(new Character('N')));
		System.out.println(nEquipsCostat.get(new Character('S')));
		System.out.println(nEquipsCostat.get(new Character('E')));
		System.out.println(nEquipsCostat.get(new Character('O')));
		
	}
	
}
