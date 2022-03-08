package me.arnaumas.altres;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class GetPlayerSkin {

    /**
     * Change the player skin.
     *
     * @param uuid            The targeted player's uuid.
     * @return playertextures The property of the player's skin.
     */

    public Property byUUID(String uuid) {

        Property playertextures = null;

        // Do the request
        try {

            URL url = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid + "?unsigned=false");
            InputStreamReader reponse = new InputStreamReader(url.openStream());
            JsonObject jsonproperty = new JsonParser().parse(reponse).getAsJsonObject().get("properties").getAsJsonArray().get(0).getAsJsonObject();
            String texture = jsonproperty.get("value").getAsString();
            String signature = jsonproperty.get("signature").getAsString();

            playertextures = new Property("textures", texture, signature);

        } catch (IOException exception){

            System.err.println(exception);

        }

        return playertextures;
    }

    /**
     * Change the player skin.
     *
     * @param name            The targeted player's name.
     * @return playertextures The property of the player's skin.
     */

    public Property byName(String name) {

        String uuid = "";

        // Do the first request
        try {

            URL url1 = new URL("https://api.mojang.com/users/profiles/minecraft/" + name);
            InputStreamReader reponse1 = new InputStreamReader(url1.openStream());
            uuid = new JsonParser().parse(reponse1).getAsJsonObject().get("id").getAsString();

        } catch (IOException exception){

            System.err.println(exception);

        }

        Property playertextures = null;

        // Do the second request
        try {

            URL url2 = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid + "?unsigned=false");
            InputStreamReader reponse2 = new InputStreamReader(url2.openStream());
            JsonObject jsonproperty = new JsonParser().parse(reponse2).getAsJsonObject().get("properties").getAsJsonArray().get(0).getAsJsonObject();
            String texture = jsonproperty.get("value").getAsString();
            String signature = jsonproperty.get("signature").getAsString();

            playertextures = new Property("textures", texture, signature);

        } catch (IOException exception){

            System.err.println(exception);

        }

        return playertextures;

    }

    /**
     * Change the player skin.
     *
     * @param player          The targeted player's uuid.
     * @return playertextures The property of the player's skin.
     */

    public Property byObject(Player player){

        // Init the player's connection
        GameProfile profile = ((CraftPlayer) player).getHandle().getProfile();

        // Get the skin's textures
        Property property = profile.getProperties().get("textures").iterator().next();

        return new Property("textures", property.getValue(), property.getSignature());
    }

}
