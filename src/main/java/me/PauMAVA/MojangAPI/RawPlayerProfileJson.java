/*
 * MojangAPI
 * Copyright (c) 2019  Pau Machetti Vallverdú
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

import java.util.List;
import java.util.UUID;

public class RawPlayerProfileJson extends PlayerProfile {

    private String id;
    private String name;
    private List<RawPlayerProfileProperty> properties;

    public String getId() {
        return id;
    }

    public UUID getUUID() {
        return UUID.fromString(String.format("%s-%s-%s-%s-%s", id.substring(0,8), id.substring(8,12), id.substring(12,16), id.substring(16,20), id.substring(20)));
    }

    public String getName() {
        return name;
    }

    public List<RawPlayerProfileProperty> getProperties() {
        return properties;
    }
}
