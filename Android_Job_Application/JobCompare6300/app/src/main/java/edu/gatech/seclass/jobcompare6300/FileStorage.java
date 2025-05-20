package edu.gatech.seclass.jobcompare6300;



import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class FileStorage {

    private SharedPreferences sharedPref;
    private ObjectMapper mapper;
    private static final String Shared_Pref = "JobComparisonPreferences";
    private static final String Current_Job_Value = "CurrentJob";
    private static final String Weight_Value = "JobWeights";
    private static final String Job_Offer_Value = "JobOffers";
    private static final String Cost_Of_Living_Value = "CostOfLiving";


    public FileStorage(Context context) {
        this.sharedPref = context.getSharedPreferences(Shared_Pref, Context.MODE_PRIVATE);
        this.mapper = new ObjectMapper();
    }

    // Save the current job
    public void saveCurrJob(Job job) {
        try {
            String json = mapper.writeValueAsString(job);
            Log.d("fileStorage", "Saving Current Job JSON: " + json); // Log JSON before saving

            sharedPref.edit().putString(Current_Job_Value, json).apply();
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
    }

    // get the current job
    public Job getCurrJob() {

        String json = sharedPref.getString(Current_Job_Value, null);
        if (json != null) {
            try {
                Log.d("fileStorage", "Retrieved Current Job JSON: " + json); // Log the JSON
                return mapper.readValue(json, Job.class);
            } catch (JsonProcessingException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    //Save comparison settings
    public void saveComparSettings(ComparisonSettings settings) {
        try {
            String json = mapper.writeValueAsString(settings);
            Log.d("fileStorage", "Saving ComparisonSettings JSON: " + json); // Log JSON before saving

            sharedPref.edit().putString(Weight_Value, json).apply();
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();

        }
    }

    // Get comparison settings
    public ComparisonSettings getComparSettings() {
        String json = sharedPref.getString(Weight_Value, null);
        if (json != null) {
            try {
                Log.d("fileStorage", "Retrieved ComparisonSettings JSON: " + json);
                return mapper.readValue(json, ComparisonSettings.class);
            } catch (JsonProcessingException ex) {
                ex.printStackTrace();

            }

        }
        return new ComparisonSettings();
    }
    public void saveLocationData(Location location) {
        Map<String, Float> costOfLivingData = getCostOfLivingData();
        String key = location.getLocationKey();

        // Update cost of living if location exists, else add new one
        costOfLivingData.put(key, location.getCostOfLiving());

        try {
            String json = mapper.writeValueAsString(costOfLivingData);
            Log.d("fileStorage", "Saving Cost of Living Data: " + json);
            sharedPref.edit().putString(Cost_Of_Living_Value, json).apply();
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
    }

    public Location fetchLocation(String locationKey) {
        Map<String, Float> costOfLivingData = getCostOfLivingData();
        float costOfLiving = costOfLivingData.get(locationKey);
        String[] cityState = Location.getCityStateFromKey(locationKey);
        String city = cityState[0];
        String state = cityState[1];
        return new Location(city, state, costOfLiving);
    }

    public Map<String, Float> getCostOfLivingData() {
        String json = sharedPref.getString(Cost_Of_Living_Value, null);
        if (json != null) {
            try {
                Log.d("fileStorage", "Retrieved Cost of Living Data: " + json);
                return mapper.readValue(json, new TypeReference<Map<String, Float>>() {});
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return new HashMap<>();
    }

    public float getCostOfLivingForLocation(String city, String state) {
        Map<String, Float> costOfLivingData = getCostOfLivingData();
        String key = city + ", " + state;
        return costOfLivingData.getOrDefault(key, -1f); // Return -1 if not found
    }

    public float getCostOfLivingForLocation(String key) {
        Map<String, Float> costOfLivingData = getCostOfLivingData();
        return costOfLivingData.getOrDefault(key, -1f); // Return -1 if not found
    }

    public float getCostOfLivingForJob(Job job) {
        return this.getCostOfLivingForLocation(job.getLocationKey());
    }

    // Delete a location's cost of living data
    public void deleteLocationData(String city, String state) {
        Map<String, Float> costOfLivingData = getCostOfLivingData();
        String key = city + ", " + state;
        if (costOfLivingData.containsKey(key)) {
            costOfLivingData.remove(key);
            saveLocationData(new Location(city, state, 0)); // Reset to default
            Log.d("fileStorage", "Deleted Cost of Living Data for: " + key);
        }
    }

    public void saveNewJobOffers(Map<String, Job> jobOffers) {
        try {
            String json = mapper.writeValueAsString(jobOffers);
            Log.d("fileStorage", "Saving New Job Offers: " + json);
            sharedPref.edit().putString(Job_Offer_Value, json).apply();
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
    }
    public Map<String, Job> getJobOffersData() {
        String json = sharedPref.getString(Job_Offer_Value, null);
        if (json != null) {
            try {
                Log.d("fileStorage", "Retrieved Job Offers Data: " + json);
                return mapper.readValue(json, new TypeReference<Map<String, Job>>() {});
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return new HashMap<>(); // Return empty map if no data exists
    }

// Save a single job offer by its unique jobId
    public void saveJobOffer(String jobId, Job job) {
        Map<String, Job> jobOffers = getJobOffersData(); // Retrieve existing data
        jobOffers.put(jobId, job); // Add or update the job entry
        saveNewJobOffers(jobOffers); // Save the updated job offers map
    }

    // Retrieve a specific job offer using its jobId
    public Job getJobOffer(String jobId) {
        Map<String, Job> jobOffers = getJobOffersData(); // Retrieve existing data
        return jobOffers.getOrDefault(jobId, null); // Return the job if found, otherwise null
    }

}