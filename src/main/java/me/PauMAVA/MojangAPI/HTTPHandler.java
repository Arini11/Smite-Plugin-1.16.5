/*
 * MojangAPI
 * Copyright (c) 2019  Pau Machetti Vallverd�
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package me.PauMAVA.MojangAPI;

import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class HTTPHandler {

    private MojangAPI api;

    HTTPHandler(MojangAPI api) {
        this.api = api;
    }

    HttpURLConnection getHTTPConnection(MojangService service, String... args) throws NullPointerException {
        try {
            StringBuilder sb = new StringBuilder("https://" + service.getKey());
            for (String s: args) {
                sb.append("/").append(s);
            }
            System.out.println(sb.toString());
            URL statusServer = new URL(sb.toString());
            HttpURLConnection conn = (HttpURLConnection) statusServer.openConnection();
            return conn;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    <T> Object fetchJSON(HttpURLConnection conn, Class<T> destinationClass) throws IOException, NullPointerException {
        conn.setRequestMethod("GET");
        conn.setAllowUserInteraction(false);
        conn.connect();
        int status = conn.getResponseCode();
        if(status == 200 || status == 201) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String nextString;
            while((nextString = reader.readLine()) != null) {
                builder.append(nextString).append("\n");
            }
            reader.close();
            System.out.println("Reformatted JSON: " + reformatJSON(builder, destinationClass));
            GsonBuilder gson = new GsonBuilder();
            return gson.create().fromJson(reformatJSON(builder, destinationClass), destinationClass);
        } else {
            return null;
        }
    }

    public boolean checkService(MojangService service) {
        try {
            URL statusServer = new URL("https://status.mojang.com/check");
            HttpURLConnection conn = (HttpURLConnection) statusServer.openConnection();
            MojangStatusJson data = (MojangStatusJson) fetchJSON(conn, MojangStatusJson.class);
            if(data == null) {
                return false;
            }
            String status = (String) data.getClass().getDeclaredField(service.name()).get(data);
            conn.disconnect();
            return status.equalsIgnoreCase("green") || status.equalsIgnoreCase("yellow");
        } catch (IOException | NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

    private <T> String reformatJSON(StringBuilder originalJSON, Class<T> destinationClass) {
        if(destinationClass.equals(MojangStatusJson.class)) {
            return originalJSON.toString().replace("{", "").replace("}", "").replace("[", "{").replace("]", "}");
        } else if(destinationClass.equals(UsernameToUUIDJson.class)) {
            return originalJSON.toString();
        } else if(destinationClass.equals(PlayerProfileJson.class)) {
            String section = StringUtils.substringBetween(originalJSON.toString(), "\"decoded\": {", "\"timestamp\":");
            return "{" + section.substring(0, section.lastIndexOf(",")) + "}";
        } else if (destinationClass.equals(RawPlayerProfileJson.class)) {
            return "{" + StringUtils.substringBetween(originalJSON.toString(), "\"raw\": {", "\"status\":").replace("],", "]") + "}";
        }
        return "";
    }
}
