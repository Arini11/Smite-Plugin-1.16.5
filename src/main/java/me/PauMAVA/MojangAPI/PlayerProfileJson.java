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

import java.util.UUID;

public class PlayerProfileJson  extends PlayerProfile {

    String profileId;
    String profileName;

    Textures textures;

    public String getId() {
        return profileId;
    }

    public UUID getUUID() {
        return UUID.fromString(String.format("%s-%s-%s-%s-%s", profileId.substring(0,8), profileId.substring(8,12), profileId.substring(12,16), profileId.substring(16,20), profileId.substring(20)));
    }

    public String getName() {
        return profileName;
    }

    public Textures getTextures() {
        return textures;
    }

    @Override
    public String toString() {
        return "{\n" +
                "profileId: " + profileId + "\n" +
                "profileName: " + profileName + "\n" +
                "url: " + textures.getSkin().getUrl() + "\n" +
                "}";
    }
}


