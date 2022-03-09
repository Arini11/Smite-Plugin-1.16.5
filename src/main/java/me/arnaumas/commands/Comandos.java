package me.arnaumas.commands;

import java.util.Random;

import me.arnaumas.UhcMain;
import net.minecraft.server.v1_16_R3.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_16_R3.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;



public class Comandos {
	
	public static void skin(CommandSender sender) {
		Player p = (Player) sender;
				
		//Init the player's connection
        GameProfile profile = ((CraftPlayer)p).getHandle().getProfile();
        PlayerConnection connection = ((CraftPlayer)p).getHandle().playerConnection;

        //  Send the packets
        connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, ((CraftPlayer)p).getHandle()));
        profile.getProperties().removeAll("textures");
        profile.getProperties().put("textures", getSkin(UhcMain.getInstance().getConfig().getString("mode")));
        connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, ((CraftPlayer)p).getHandle()));
	}
	
	private static Property getSkin(String mode) {
        //  Choose a random skin from the config
        Random r = new Random();
        int num = r.nextInt(2); // [0-1]
        switch (num) {
            //  Load the skin
            case 0:
            	Bukkit.broadcastMessage("Hercules");
            	return new Property("textures", UhcMain.getInstance().getConfig().get("skin_textures." + mode + ".hercules.texture_value").toString(), UhcMain.getInstance().getConfig().get("skin_textures." + mode + ".hercules.texture_signature").toString());
            default: 
            	Bukkit.broadcastMessage("Cupido");
            	return new Property("textures", UhcMain.getInstance().getConfig().get("skin_textures." + mode + ".cupido.texture_value").toString(), UhcMain.getInstance().getConfig().get("skin_textures." + mode + ".cupido.texture_signature").toString());
        }
    }
}
