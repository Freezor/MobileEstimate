package com.mobileprojectestimator.mobileprojectestimator.DataObjects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import com.mobileprojectestimator.mobileprojectestimator.R;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by Oliver Fries on 25.10.2015.
 * <p/>
 * Data Class with all data params a project needs
 */
public class Project implements Serializable
{
    /**
     * Constructs a new instance of {@code Object}.
     */
    public Project()
    {
        super();
    }

    /**
     * Creates and returns a copy of this {@code Object}. The default
     * implementation returns a so-called "shallow" copy: It creates a new
     * instance of the same class and then copies the field values (including
     * object references) from this instance to the new instance. A "deep" copy,
     * in contrast, would also recursively clone nested objects. A subclass that
     * needs to implement this kind of cloning should call {@code super.clone()}
     * to create the new instance and then create deep copies of the nested,
     * mutable objects.
     *
     * @return a copy of this object.
     * @throws CloneNotSupportedException if this object's class does not implement the {@code
     *                                    Cloneable} interface.
     */
    @Override
    protected Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }

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
    public Project(Context current, String title, String creationDate, String estimationMethod)
    {
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
    public Project(Context current)
    {
        this.context = current;
        projectProperties = new ProjectProperties();
    }

    public Context getContext()
    {
        return this.context;
    }

    private void setInfluencingFactors()
    {
        projectProperties = new ProjectProperties();
    }


    public String getTitle()
    {
        return Title;
    }

    public void setTitle(String title)
    {
        Title = title;
    }

    public void setImage(Bitmap img)
    {
        this.image = img;
    }

    public Bitmap getImage()
    {
        return image;
    }

    public String getCreationDate()
    {
        return creationDate;
    }

    public void setCreationDate(String creationDate)
    {
        this.creationDate = creationDate;
    }

    public String getEstimationMethod()
    {
        return estimationMethod;
    }

    public void setEstimationMethod(String estimationMethod)
    {
        this.estimationMethod = estimationMethod;
    }

    public InfluencingFactor getInfluencingFactor()
    {
        return influencingFactor;
    }

    public void setInfluencingFactor(InfluencingFactor factor){
        this.influencingFactor = factor;
    }

    public void setInfluencingFactor(HashMap<String, String> objectHash)
    {
        if(this.estimationMethod.equals(context.getString(R.string.estimation_method_function_point))){
            this.influencingFactor.setValuesFromHashMap(objectHash,InfluencingFactor.FUNCTIONPOINTFACTORS);
        } else if(this.estimationMethod.equals(context.getString(R.string.estimation_method_cocomo)))
        {
            this.influencingFactor.setValuesFromHashMap(objectHash,InfluencingFactor.COCOMOFACTORS);
        }
    }

    public ProjectProperties getProjectProperties()
    {
        return projectProperties;
    }

    public void setProjectProperties(ProjectProperties projectProperties)
    {
        this.projectProperties = projectProperties;
    }

    public String getProjectDescription()
    {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription)
    {
        this.projectDescription = projectDescription;
    }

    public String getIconName()
    {
        return iconName;
    }

    public void setIconName(String iconName)
    {
        this.iconName = iconName;
    }

    /**
     * Generate a HashMap from the project Object for sending this project between intents
     *
     * @return
     */
    public HashMap<String, String> toHashMap()
    {
        //TODO: change use of gson to hashmap

        HashMap<String, String> valuesMap = new HashMap<>();
        valuesMap.putAll(this.influencingFactor.toHashMap());
        valuesMap.putAll(this.projectProperties.toHashMap());

        valuesMap.put(context.getString(R.string.project_hashmap_item_title), Title);

        Bitmap immagex = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        valuesMap.put(context.getString(R.string.project_hashmap_item_image), Base64.encodeToString(b, Base64.DEFAULT));

        valuesMap.put(context.getString(R.string.project_hashmap_item_iconname), iconName);
        valuesMap.put(context.getString(R.string.project_hashmap_item_creation_date), creationDate);
        valuesMap.put(context.getString(R.string.project_hashmap_item_description), projectDescription);
        valuesMap.put(context.getString(R.string.project_hashmap_item_estimation_method), estimationMethod);

        return valuesMap;
    }

    /**
     * Generate a Project from the project Hashmap
     *
     * @param objectHash
     */
    public void toObjectFromHashMap(HashMap<String, String> objectHash)
    {
        //TODO: change use of gson to hashmap
        this.setTitle(objectHash.get(context.getString(R.string.project_hashmap_item_title)));
        this.setIconName(objectHash.get(context.getString(R.string.project_hashmap_item_iconname)));
        this.setCreationDate(objectHash.get(context.getString(R.string.project_hashmap_item_creation_date)));
        this.setProjectDescription(objectHash.get(context.getString(R.string.project_hashmap_item_description)));
        this.setEstimationMethod(objectHash.get(context.getString(R.string.project_hashmap_item_estimation_method)));
        byte[] decodedByte = Base64.decode(objectHash.get(context.getString(R.string.project_hashmap_item_image)), 0);
        this.setImage(BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length));

        setInfluencingFactor(objectHash);
        //setProjectProperties(gson.fromJson(objectHash.get(context.getString(R.string.project_hashmap_item_properties)), ProjectProperties.class));
    }
}
