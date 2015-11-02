package com.mobileprojectestimator.mobileprojectestimator.DataObjects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by Oliver Fries on 25.10.2015.
 */
public class Project implements Serializable {

    private String Title;
    private Bitmap image;
    private String iconName;
    private String creationDate;
    private String projectDescription;
    private String estimationMethod;//TODO: change with db
    private Context context;
    private InfluencingFactor influencingFactor;
    private ProjectProperties projectProperties;

    /**
     * Create a Project with already known title, creation Date and estimation Type
     * Mostly for test purpose
     *
     * @param current
     * @param title
     * @param creationDate
     * @param estimationMethod
     */
    public Project(Context current, String title, String creationDate, String estimationMethod) {
        this.Title = title;
        this.creationDate = creationDate;
        this.estimationMethod = estimationMethod;
        this.context = current;
        setInfluencingFactors();
        projectProperties = new ProjectProperties();
    }

    /**
     * Create an empty project
     *
     * @param current
     */
    public Project(Context current) {
        this.context = current;
        projectProperties = new ProjectProperties();
    }

    public Context getContext() {
        return this.context;
    }

    private void setInfluencingFactors() {
        projectProperties = new ProjectProperties();
    }


    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setImage(Bitmap img) {
        this.image = img;
    }

    public Bitmap getImage() {
        return image;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getEstimationMethod() {
        return estimationMethod;
    }

    public void setEstimationMethod(String estimationMethod) {
        this.estimationMethod = estimationMethod;
    }

    public InfluencingFactor getInfluencingFactor() {
        return influencingFactor;
    }

    public void setInfluencingFactor(InfluencingFactor influencingFactor) {
        this.influencingFactor = influencingFactor;
    }

    public ProjectProperties getProjectProperties() {
        return projectProperties;
    }

    public void setProjectProperties(ProjectProperties projectProperties) {
        this.projectProperties = projectProperties;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public HashMap<String,String> toHashMap(){
        HashMap<String,String> valuesMap = new HashMap<>();
        Gson gson = new Gson();
        String infFactors = gson.toJson(this.influencingFactor);
        valuesMap.put("INFLUENCINGFACTORS",infFactors);
        String properties = gson.toJson(this.projectProperties);
        valuesMap.put("PROPERTIES",properties);

        valuesMap.put("TITLE", Title);

        Bitmap immagex = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        valuesMap.put("IMAGE", Base64.encodeToString(b, Base64.DEFAULT));

        valuesMap.put("ICONNAME",iconName);
        valuesMap.put("CREATIONDATE",creationDate);
        valuesMap.put("PROJECTDESCRIPTION",projectDescription);
        valuesMap.put("ESTIMATIONMETHOD",estimationMethod);

        return valuesMap;
    }

    public void toObjectFromHashMap(HashMap<String,String> objectHash){
        Gson gson = new Gson();
        setInfluencingFactor(gson.fromJson(objectHash.get("INFLUENCINGFACTORS"), FunctionPointFactor.class));
        setProjectProperties(gson.fromJson(objectHash.get("PROPERTIES"), ProjectProperties.class));
        this.setTitle(objectHash.get("TITLE"));
        this.setIconName(objectHash.get("ICONNAME"));
        this.setCreationDate(objectHash.get("CREATIONDATE"));
        this.setProjectDescription(objectHash.get("PROJECTDESCRIPTION"));
        this.setEstimationMethod(objectHash.get("ESTIMATIONMETHOD"));
        byte[] decodedByte = Base64.decode(objectHash.get("IMAGE"), 0);
        this.setImage(BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length));
    }
}
