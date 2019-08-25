/*
 * Copyright (C) 2019 eccentric_nz
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package me.eccentric_nz.TARDIS.chameleon.coloured;

/**
 * A chameleon conversion is a repair procedure that technicians perform on TARDIS chameleon circuits. The Fourth Doctor
 * once said that the reason the TARDIS' chameleon circuit was stuck was because he had "borrowed" it from Gallifrey
 * before the chameleon conversion was completed.
 *
 * @author eccentric_nz
 */
public class PoliceBoxPreset {

    private String[][] blueprintData;
    private String[][] stainedData;
    private String[][] glassData;

    void setBlueprintData(String[][] blueprintData) {
        this.blueprintData = blueprintData;
    }

    void setStainedData(String[][] stainedData) {
        this.stainedData = stainedData;
    }

    void setGlassData(String[][] glassData) {
        this.glassData = glassData;
    }

    public String[][] getBlueprintData() {
        return blueprintData;
    }

    public String[][] getStainedData() {
        return stainedData;
    }

    public String[][] getGlassData() {
        return glassData;
    }
}
