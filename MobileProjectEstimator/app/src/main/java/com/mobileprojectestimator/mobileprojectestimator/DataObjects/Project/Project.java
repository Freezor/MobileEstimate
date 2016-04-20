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
    private String title;
    private Bitmap image;
    private String iconName;
    private String creationDate;
    private String projectDescription;
    private String estimationMethod;//TODO: change with db
    private Context context;
    private InfluencingFactor influencingFactor;
    private ProjectProperties projectProperties;
    private ArrayList<EstimationItem> estimationItems;
    private int sumOfInfluences;
    private double influenceFactorRating;
    private double evaluatedPersonDays = 0.0;
    private int projectId;
    private String iconId;
    private boolean isDeleted;
    private boolean isTerminated;
    private double finalPersonDays;
    private double evaluatedPoints;
    private boolean selected;

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
        this.title = title;
        this.creationDate = creationDate;
        this.estimationMethod = estimationMethod;
        this.context = current;
        setInfluencingFactors();
        projectProperties = new ProjectProperties();
        initialiseEstimationItems(estimationMethod);
    }

    public Project(Context current, String title, String creationDate, String estimationMethod, int projectId)
    {
        this.projectId = projectId;
        this.title = title;
        this.creationDate = creationDate;
        this.estimationMethod = estimationMethod;
        this.context = current;
        isDeleted = false;
        isTerminated = false;
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

    public void initialiseEstimationItems(String estimationMethod)
    {
        if (estimationMethod.equals(context.getString(R.string.estimation_method_function_point)))
        {
            this.estimationItems = new ArrayList<>();
            this.estimationItems.add(new FunctionPointItem((context.getString(R.string.function_point_estimation_input_data)), 3, 4, 6));//0
            this.estimationItems.add(new FunctionPointItem((context.getString(R.string.function_point_estimation_requests)), 3, 4, 6));//1
            this.estimationItems.add(new FunctionPointItem((context.getString(R.string.function_point_estimation_output)), 4, 5, 7));//2
            this.estimationItems.add(new FunctionPointItem((context.getString(R.string.function_point_estimation_dataset)), 7, 10, 15));//3
            this.estimationItems.add(new FunctionPointItem((context.getString(R.string.function_point_estimation_reference_data)), 5, 7, 10));//4
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
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
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
        HashMap<String, String> valuesMap = new HashMap<>();
        valuesMap.putAll(this.influencingFactor.toHashMap());
        valuesMap.putAll(this.projectProperties.toHashMap());

        valuesMap.put(context.getString(R.string.project_hashmap_item_title), title);

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
                //TODO: Total Amount in Hashmap umwandeln und wieder zurück
                FunctionPointItem fpItem = (FunctionPointItem) item;
                valuesMap.put(String.format("%s%s", fpItem.getItemName(), context.getString(R.string.project_hash_suffix_simple)), String.valueOf(fpItem.getItemTotalAmountOfIndex(0)));
                valuesMap.put(String.format("%s%s", fpItem.getItemName(), context.getString(R.string.project_hash_suffix_medium)), String.valueOf(fpItem.getItemTotalAmountOfIndex(1)));
                valuesMap.put(String.format("%s%s", fpItem.getItemName(), context.getString(R.string.project_hash_suffix_complex)), String.valueOf(fpItem.getItemTotalAmountOfIndex(2)));
                valuesMap.put(String.format("%s%s", fpItem.getItemName(), context.getString(R.string.project_hash_suffix_total_amount)), String.valueOf(fpItem.getTotalAmount()));
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

        setEstimationItemsValue(objectHash);
    }

    /**
     * Set all saved estimation item values from the hash map
     *
     * @param objectHash
     */
    private void setEstimationItemsValue(HashMap<String, String> objectHash)
    {
        if (this.estimationMethod.equals(context.getString(R.string.estimation_method_function_point)))
        {
            //zwischenschritt neue arraylist erstellen und die alte damit ersetzen, da sonst nur werte in die arralist eingefügt werden
            ArrayList<EstimationItem> estimationItemsNew = new ArrayList<>();
            for (EstimationItem item : estimationItems)
            {
                FunctionPointItem fpItem = (FunctionPointItem) item;
                String simple = objectHash.get(String.format("%s%s", item.getItemName(), context.getString(R.string.project_hash_suffix_simple)));
                fpItem.getFunctionPointCategoryItems().get(0).setTotalItemCount(Integer.valueOf(simple));
                String medium = objectHash.get(String.format("%s%s", item.getItemName(), context.getString(R.string.project_hash_suffix_medium)));
                fpItem.getFunctionPointCategoryItems().get(1).setTotalItemCount(Integer.valueOf(medium));
                String complex = objectHash.get(String.format("%s%s", item.getItemName(), context.getString(R.string.project_hash_suffix_complex)));
                fpItem.getFunctionPointCategoryItems().get(2).setTotalItemCount(Integer.valueOf(complex));
                String totalAmount = objectHash.get(String.format("%s%s", fpItem.getItemName(), context.getString(R.string.project_hash_suffix_total_amount)));
                fpItem.setTotalAmount(Integer.valueOf(totalAmount));
                estimationItemsNew.add(fpItem);
            }
            estimationItems = estimationItemsNew;
        } else
        {

        }
    }

    /**
     * update a specific estimation item
     *
     * @param title
     * @param item
     * @return
     */
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

    /**
     * Returns the sum of an estimation item category
     *
     * @param title
     * @return
     */
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
        return -1;
    }

    /**
     * Returns the complete estimation item list after refreshing all items
     *
     * @return
     */
    public ArrayList<EstimationItem> getEstimationItems()
    {
        if (estimationItems != null && !estimationItems.isEmpty())
        {
            refreshItems();
            return estimationItems;
        } else
        {
            return new ArrayList<EstimationItem>();
        }
    }

    /**
     * Update the total Amount of an Item
     *
     * @param item
     */
    public void updateFunctionPointItem(FunctionPointItem item)
    {
        ArrayList<FunctionPointCategoryItem> category = item.getFunctionPointCategoryItems();
        FunctionPointItem fpItem = getFunctionPointEstimationItemByName(item.getItemName());
        fpItem.updateItem(0, category.get(0).getTotalItemCount());
        fpItem.updateItem(1, category.get(1).getTotalItemCount());
        fpItem.updateItem(2, category.get(2).getTotalItemCount());
    }

    /**
     * get the list with all estimation Itrems
     *
     * @return
     */
    public ArrayList<FunctionPointItem> getFunctionPointItems()
    {
        refreshItems();
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

    /**
     * Calls item.refresh for each estimation item
     */
    private void refreshItems()
    {
        if (estimationItems != null && !estimationItems.isEmpty())
        {
            for (EstimationItem item : this.estimationItems)
            {
                item.refresh();
            }
        }
    }

    /**
     * Update an functionPointEstimationItems value of simple medium and complex total amount
     *
     * @param itemName
     * @param simple
     * @param medium
     * @param complex
     */
    public void updateFunctionPointItem(String itemName, Integer simple, Integer medium, Integer complex)
    {
        FunctionPointItem fpItem = getFunctionPointEstimationItemByName(itemName);
        fpItem.updateItem(0, simple);
        fpItem.updateItem(1, medium);
        fpItem.updateItem(2, complex);
        this.estimationItems.set(getEstimationItemIndex(itemName), fpItem);
    }

    /**
     * Get the Index of an estimation Item by its Name
     *
     * @param itemName
     * @return
     */
    private int getEstimationItemIndex(String itemName)
    {
        for (EstimationItem _item : estimationItems)
        {
            if (_item.getItemName().equals(itemName))
                return estimationItems.indexOf(_item);
        }
        return -1;
    }

    /**
     * Returns the sum of all influences
     *
     * @return
     */
    public int getSumOfInfluences()
    {
        sumOfInfluences = 0;

        if (this.estimationMethod.equals(context.getString(R.string.estimation_method_function_point)))
        {
            sumOfInfluences = this.influencingFactor.getSumOfInfluences();
        }

        return sumOfInfluences;
    }


    public void setSumOfInfluences(int sumOfInfluences)
    {
        this.sumOfInfluences = sumOfInfluences;
    }

    /**
     * Returns the calculated influence rating of all influences
     *
     * @return
     */
    public double getFactorInfluenceRating()
    {
        influenceFactorRating = 0;

        if (this.estimationMethod.equals(context.getString(R.string.estimation_method_function_point)))
        {
            influenceFactorRating = this.influencingFactor.getFactorInfluenceRating();
        }

        return influenceFactorRating;
    }

    /**
     * evaluate the person Days for this project
     *
     * @return
     */
    public double getEvaluatedPersonDays()
    {
        return evaluatedPersonDays;
    }

    public void setEvaluatedPersonDays(double evaluatedPersonDays)
    {
        this.evaluatedPersonDays = evaluatedPersonDays;
    }

    public int getProjectId()
    {
        return projectId;
    }

    public void setProjectId(int projectId)
    {
        this.projectId = projectId;
    }


    public void setEstimationItems(ArrayList<EstimationItem> estimationItems)
    {
        this.estimationItems = estimationItems;
    }

    public void setIconId(String iconId)
    {
        this.iconId = iconId;
    }

    public String getIconId()
    {
        return iconId;
    }

    public void setInfluenceFactorRating(double influenceFactorRating)
    {
        this.influenceFactorRating = influenceFactorRating;
    }

    public boolean isDeleted()
    {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted)
    {
        this.isDeleted = isDeleted;
    }

    public boolean isTerminated()
    {
        return isTerminated;
    }

    public void setIsTerminated(boolean isTerminated)
    {
        this.isTerminated = isTerminated;
    }

    public double getFinalPersonDays()
    {
        return finalPersonDays;
    }

    public void setFinalPersonDays(double finalPersonDays)
    {
        this.finalPersonDays = finalPersonDays;
    }

    public void setEvaluatedPoints(double evaluatedPoints)
    {
        this.evaluatedPoints = evaluatedPoints;
    }

    public double getEvaluatedPoints()
    {
        double erg = (double) getTotalPoints() * getFactorInfluenceRating();
        erg = (double) Math.round(erg * 10000d) / 10000d;
        this.evaluatedPoints = erg;
        return this.evaluatedPoints;
    }

    /**
     * Calculate the total function points
     *
     * @return
     */
    public int getTotalPoints()
    {
        int totalPoints = 0;
        for (FunctionPointItem item : getFunctionPointItems())
        {
            totalPoints += item.getTotalAmount();
        }
        return totalPoints;
    }

    public void setSelected(boolean selected)
    {
        this.selected = selected;
    }

    public boolean isSelected()
    {
        return selected;
    }
}
