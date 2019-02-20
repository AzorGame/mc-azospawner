package io.github.azorimor.azospawner.utils;

import org.bukkit.Material;

import java.util.HashMap;

public class RecipeValues {

    private String[] rows;

    private HashMap<Character, Material> values;

    public RecipeValues(String row1, String row2, String row3) {
        this.rows = new String[3];

        rows[0] = row1.replace('_',' ');
        rows[1] = row2.replace('_',' ');
        rows[2] = row3.replace('_',' ');

        this.values = new HashMap<Character, Material>();
    }

    public void addKeyPair(Character key, String materialValue){
        try {
            values.put(key, Material.valueOf(materialValue));
        } catch (IllegalArgumentException e) {
            System.err.println("Please use a valid materialname in your config.yml file.");
            System.err.println("Following material is invalid: "+materialValue);
            System.err.println("A crafting recipes will be disabled.");
        }
    }

    public String getFirstRow(){
        return rows[0];
    }
    public String getSecondRow(){
        return rows[1];
    }
    public String getThirdRow(){
        return rows[2];
    }

    public HashMap<Character, Material> getValues() {
        return values;
    }

    public String[] getRows() {
        return rows;
    }

}
