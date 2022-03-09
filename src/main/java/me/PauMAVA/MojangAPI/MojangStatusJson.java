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

import com.google.gson.annotations.SerializedName;

public class MojangStatusJson {
    @SerializedName("minecraft.net")
    String MINECRAFT;
    @SerializedName("session.minecraft.net")
    String MINECRAFT_SESSION;
    @SerializedName("account.mojang.com")
    String MOJANG_ACCOUNTS;
    @SerializedName("auth.mojang.com")
    String MOJANG_AUTH;
    @SerializedName("skins.minecraft.net")
    String MINECRAFT_SKINS;
    @SerializedName("authserver.mojang.com")
    String MOJANG_AUTHSERVER;
    @SerializedName("sessionserver.mojang.com")
    String MOJANG_SESSIONSERVER;
    @SerializedName("api.mojang.com")
    String MOJANG_API;
    @SerializedName("textures.minecraft.net")
    String MINECRAFT_TEXTURES;
    @SerializedName("mojang.com")
    String MOJANG_WEB;
    @SerializedName("status.mojang.com")
    String MOJANG_STATUS;

    public String getMINECRAFT() {
        return MINECRAFT;
    }

    public String getMINECRAFT_SESSION() {
        return MINECRAFT_SESSION;
    }

    public String getMOJANG_ACCOUNTS() {
        return MOJANG_ACCOUNTS;
    }

    public String getMOJANG_AUTH() {
        return MOJANG_AUTH;
    }

    public String getMINECRAFT_SKINS() {
        return MINECRAFT_SKINS;
    }

    public String getMOJANG_AUTHSERVER() {
        return MOJANG_AUTHSERVER;
    }

    public String getMOJANG_SESSIONSERVER() {
        return MOJANG_SESSIONSERVER;
    }

    public String getMOJANG_API() {
        return MOJANG_API;
    }

    public String getMINECRAFT_TEXTURES() {
        return MINECRAFT_TEXTURES;
    }

    public String getMOJANG_WEB() {
        return MOJANG_WEB;
    }

    public String getMOJANG_STATUS() {
        return MOJANG_STATUS;
    }
}
