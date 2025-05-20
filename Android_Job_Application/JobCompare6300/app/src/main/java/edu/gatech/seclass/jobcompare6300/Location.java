package edu.gatech.seclass.jobcompare6300;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class Location implements Serializable {
    private String city;
    private String state;
    private float costOfLiving;

    private static final Map<String, Float> costOfLivingIndexes = new HashMap<>();

    public Location() {}
    public Location (String city, String state, float costOfLiving){
        this.city = city.trim();
        this.state = state.trim();
        setCostOfLiving(costOfLiving);
    }

    public String getCity(){
        return city;
    }
    public void setCity(String city){
        this.city = city.trim();
    }
    public String getState(){
        return state;

    }
    public void setState(String state){
        this.state = state.trim();
    }

    public float getCostOfLiving(){
        return costOfLiving;
    }
    public void setCostOfLiving(float costOfLiving) {
        String key = getLocationKey();
        this.costOfLiving = costOfLiving;
        costOfLivingIndexes.put(key, costOfLiving);
    }
    private void updateCostOfLiving() {
        String key = getLocationKey();
        if (costOfLivingIndexes.containsKey(key)) {
            this.costOfLiving = costOfLivingIndexes.get(key);
        }
    }
    @JsonIgnore
    public String getLocationKey() {
        return city + ", " + state; // Unique key for each location
    }

    @JsonIgnore
    public static String[] getCityStateFromKey(String key) {
        return key.split(", ");
    }

    @Override
    public String toString(){
        return city + ", " + state + " (Cost of Living: " + costOfLiving + ")";
    }

}
