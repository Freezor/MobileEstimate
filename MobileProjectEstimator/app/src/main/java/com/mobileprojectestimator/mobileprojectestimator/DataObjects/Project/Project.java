package com.mobileprojectestimator.mobileprojectestimator.DataObjects.Project;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Items.Estimation.EstimationItem;
import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Items.Estimation.FunctionPointCategoryItem;
import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Items.Estimation.FunctionPointItem;
import com.mobileprojectestimator.mobileprojectestimator.R;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Oliver Fries on 25.10.2015.
 * <p/>
 * Data Class with all data params a project needs
 */
public class Project implements Serializable
{
    private String Title;
    private Bitmap image;
    private String iconName;
    private String creationDate;
    private String projectDescription;
    private String estimationMethod;//TODO: change with db
    private Context context;
    private InfluencingFactor influencingFactor;
    private ProjectProperties projectProperties;
    private ArrayList<EstimationItem> estimationItems;

    /**
     * Constructs a new instance of {@code Object}.
     */
    public Project()
    {
        super();
    }

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
        initialiseEstimationItems(estimationMethod);
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

    private void initialiseEstimationItems(String estimationMethod)
    {
        if (estimationMethod.equals(context.getString(R.string.estimation_method_function_point)))
        {
            this.estimationItems = new ArrayList<>();
            this.estimationItems.add(new FunctionPointItem((context.getString(R.string.function_point_estimation_input_data)), 3, 4, 6));
            this.estimationItems.add(new FunctionPointItem((context.getString(R.string.function_point_estimation_requests)), 3, 4, 6));
            this.estimationItems.add(new FunctionPointItem((context.getString(R.string.function_point_estimation_output)), 4, 5, 7));
            this.estimationItems.add(new FunctionPointItem((context.getString(R.string.function_point_estimation_dataset)), 7, 10, 15));
            this.estimationItems.add(new FunctionPointItem((context.getString(R.string.function_point_estimation_reference_data)), 5, 7, 10));
        } else if (estimationMethod.equals(context.getString(R.string.estimation_method_cocomo)))
        {
            this.estimationItems = new ArrayList<>();
        }
    }

    /**
     * Returns the estimation item at the position in the array list
     *
     * @param index
     * @return
     */
    public EstimationItem getEstimationItemByIndex(int index)
    {
        return this.estimationItems.get(index);
    }

    /**
     * Returns the estimation item whith equal name
     *
     * @param name
     * @return
     */
    public EstimationItem getEstimationItemByName(String name)
    {
        for (EstimationItem item : estimationItems)
        {
            if (item.getItemName().equals(name))
            {
                return item;
            }
        }
        return null;
    }

    public FunctionPointItem getFunctionPointEstimationItemByName(String name)
    {
        return (FunctionPointItem) getEstimationItemByName(name);
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

    public Bitmap getImage()
    {
        return image;
    }

    public void setImage(Bitmap img)
    {
        this.image = img;
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

    public void setInfluencingFactor(HashMap<String, String> objectHash)
    {
        //Create new item with sample estimation method that the methods on the object work fine
        this.influencingFactor = new InfluencingFactor(getContext(), InfluencingFactor.FUNCTIONPOINTFACTORS);
        //Select the creation of the influencing factor on the estimation method
        if (this.estimationMethod.equals(context.getString(R.string.estimation_method_function_point)))
        {
            this.influencingFactor.setValuesFromHashMap(objectHash, InfluencingFactor.FUNCTIONPOINTFACTORS);
        } else if (this.estimationMethod.equals(context.getString(R.string.estimation_method_cocomo)))
        {
            this.influencingFactor.setValuesFromHashMap(objectHash, InfluencingFactor.COCOMOFACTORS);
        }
    }

    public void setInfluencingFactor(InfluencingFactor factor)
    {
        this.influencingFactor = factor;
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

        Bitmap imaged = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imaged.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        valuesMap.put(context.getString(R.string.project_hashmap_item_image), Base64.encodeToString(b, Base64.DEFAULT));

        valuesMap.put(context.getString(R.string.project_hashmap_item_iconname), iconName);
        valuesMap.put(context.getString(R.string.project_hashmap_item_creation_date), creationDate);
        valuesMap.put(context.getString(R.string.project_hashmap_item_description), projectDescription);
        valuesMap.put(context.getString(R.string.project_hashmap_item_estimation_method), estimationMethod);

        valuesMap.putAll(estimationItemsToHashMap());

        return valuesMap;
    }

    private Map<? extends String, ? extends String> estimationItemsToHashMap()
    {
        HashMap<String, String> valuesMap = new HashMap<>();

        if (this.estimationMethod.equals(context.getString(R.string.estimation_method_function_point)))
        {
            for (EstimationItem item : estimationItems)
            {
                FunctionPointItem fpItem = (FunctionPointItem) item;
                valuesMap.put(String.format("%s%s", fpItem.getItemName(), context.getString(R.string.project_hash_suffix_simple)), String.valueOf(fpItem.getItemTotalAmountOfIndex(0)));
                valuesMap.put(String.format("%s%s", fpItem.getItemName(), context.getString(R.string.project_hash_suffix_medium)), String.valueOf(fpItem.getItemTotalAmountOfIndex(1)));
                valuesMap.put(String.format("%s%s", fpItem.getItemName(), context.getString(R.string.project_hash_suffix_complex)), String.valueOf(fpItem.getItemTotalAmountOfIndex(2)));
            }
        } else
        {

        }
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
        this.projectProperties.setPropertyValues(objectHash);

        initialiseEstimationItems(this.estimationMethod);

        //setEstimationItemsValue(objectHash);
    }

    private void setEstimationItemsValue(HashMap<String, String> objectHash)
    {
        if (this.estimationMethod.equals(context.getString(R.string.estimation_method_function_point)))
        {
            for (EstimationItem item : estimationItems)
            {
                FunctionPointItem fpItem = (FunctionPointItem) item;
                String simple = objectHash.get(String.format("%s%s", item.getItemName(), context.getString(R.string.project_hash_suffix_simple)));
                fpItem.getFunctionPointCategoryItems().get(0).setTotalItemCount(Integer.valueOf(simple));
                String medium = objectHash.get(String.format("%s%s", item.getItemName(), context.getString(R.string.project_hash_suffix_medium)));
                fpItem.getFunctionPointCategoryItems().get(1).setTotalItemCount(Integer.valueOf(medium));
                String complex = objectHash.get(String.format("%s%s", item.getItemName(), context.getString(R.string.project_hash_suffix_complex)));
                fpItem.getFunctionPointCategoryItems().get(2).setTotalItemCount(Integer.valueOf(complex));
                estimationItems.add(estimationItems.indexOf(item),fpItem);
            }
        } else
        {

        }
    }

    public boolean updateEstimationItem(String title, FunctionPointItem item)
    {
        for (EstimationItem estimationItem : estimationItems)
        {
            if (estimationItem.getItemName().equals(title))
            {
                estimationItems.set(estimationItems.indexOf(estimationItem), item);
                return true;
            }
        }
        return false;
    }

    public int getEstimationItemSum(String title)
    {
        for (EstimationItem estimationItem : estimationItems)
        {
            if (estimationItem.getItemName().equals(title))
            {
                if (estimationMethod.equals(context.getString(R.string.estimation_method_function_point)))
                {
                    FunctionPointItem item = (FunctionPointItem) estimationItem;
                    return item.getTotalAmount();
                } else
                {
                    return -1;
                }
            }
        }
        return 0;
    }

    public ArrayList<EstimationItem> getEstimationItems()
    {
        return estimationItems;
    }

    public void updateFunctionPointItem(FunctionPointItem item)
    {
        ArrayList<FunctionPointCategoryItem> category = item.getFunctionPointCategoryItems();
        FunctionPointItem fpItem = getFunctionPointEstimationItemByName(item.getItemName());
        fpItem.updateItem(0, category.get(0).getTotalItemCount());
        fpItem.updateItem(1, category.get(1).getTotalItemCount());
        fpItem.updateItem(2, category.get(2).getTotalItemCount());
    }

    public ArrayList<FunctionPointItem> getFunctionPointItems()
    {
        if (estimationItems == null)
        {
            initialiseEstimationItems(this.estimationMethod);
        }

        ArrayList<FunctionPointItem> items = new ArrayList<>();
        for (EstimationItem item : estimationItems)
        {
            items.add((FunctionPointItem) item);
        }

        return (items);
    }

    public void updateFunctionPointItem(String itemName, Integer simple, Integer medium, Integer complex)
    {
        FunctionPointItem fpItem = getFunctionPointEstimationItemByName(itemName);
        fpItem.updateItem(0, simple);
        fpItem.updateItem(1, medium);
        fpItem.updateItem(2, complex);
    }
}
