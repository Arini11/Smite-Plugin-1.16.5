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

public enum MojangService {

    MINECRAFT("minecraft.net"),
    MINECRAFT_SESSION("session.minecraft.net"),
    MOJANG_ACCOUNTS("account.mojang.com"),
    MOJANG_AUTHSERVER("authserver.mojang.com"),
    MOJANG_SESSIONSERVER("sessionserver.mojang.com"),
    MOJANG_API("api.minetools.eu"),
    MINECRAFT_TEXTURES("textures.minecraft.net"),
    MOJANG_WEB("mojang.com"),
    MOJANG_STATUS("status.mojang.com");

    private final String service;

    MojangService(String service) {
        this.service = service;
    }

    public String getKey() {
        return this.service;
    }

}
