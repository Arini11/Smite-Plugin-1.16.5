package me.arnaumas.match;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Skull;
import org.bukkit.entity.Player;

public class DeathManager {

	private Player p;
	private World  world;
	private String deathCause;
	
	public DeathManager(Player p, World world, String deathCause) {
		this.p = p;
		this.world = world;
		this.deathCause = deathCause;
	}
	
	public Player getPlayer() {
		return this.p;
	}
	
	public String getPlayerName() {
		return this.p.getName();
	}
	
	public World getPlayerWorld() {
		return this.world;
	}
	
	public String getDeathCause() {
		return this.deathCause;
	}
	
	public List<Integer> getPlayerCoords() {
		
		Player p = this.p;
		Location location = p.getLocation();
		Integer[] arr = {location.getBlockX(), location.getBlockY(), location.getBlockZ()};
		return Arrays.asList(arr);
		
	}
	
	public void spawnTumba() {
		
    	List<Integer> coords = getPlayerCoords();
    	
		Location baseLocation = new Location(world, coords.get(0), coords.get(1) - 1, coords.get(2));
		Location fenceLocation = new Location(world, coords.get(0), coords.get(1), coords.get(2));
		Location skullLocation = new Location(world, coords.get(0), coords.get(1) + 1, coords.get(2));
		
		Block baseBlock = baseLocation.getBlock();
		baseBlock.setType(Material.GOLD_BLOCK);
		Block fenceBlock = fenceLocation.getBlock();
		fenceBlock.setType(Material.NETHER_BRICK_FENCE);
		Block skullBlock = skullLocation.getBlock();
		skullBlock.setType(Material.PLAYER_HEAD);
		
        BlockState state = skullBlock.getState();
        Skull skull = (Skull) state;
        UUID uuid = p.getUniqueId();
        skull.setOwningPlayer(Bukkit.getServer().getOfflinePlayer(uuid));
        skull.update();
        
    }
	
	public void setPlayerGamemode(GameMode gm) {
		p.setGameMode(gm);
	}
    
    
    public String color(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
}
