package com.mobileprojectestimator.mobileprojectestimator.DataObjects;

import android.content.res.Resources;

import com.mobileprojectestimator.mobileprojectestimator.R;

import java.util.ArrayList;

/**
 * Created by Oliver Fries on 25.10.2015.
 *
 * Influence Factor Items for a function point estimation
 */
public class FunctionPointInfluenceFactor extends InfluencingFactor{


    /**
     * Function Point Influence Factor Constructor.
     * Need to set a Name for this factor set.
     */
    public FunctionPointInfluenceFactor(){
        setFactorItems();
    }

    /**
     * Influence Factor with Name
     * @param name
     */
    public FunctionPointInfluenceFactor(String name){
        this.setName(name);
        setFactorItems();
    }

    @Override
    /**
     * Set the Influence Factor Items for the Function Point estimation method.
     */
    void setFactorItems() {
        this.addFactorItem(new FunctionPointInfluenceFactorItem(Resources.getSystem().getString(R.string.function_point_influence_factor_item_integration), 0, 5));
        this.addFactorItem(new FunctionPointInfluenceFactorItem(Resources.getSystem().getString(R.string.function_point_influence_factor_item_local_data), 0, 5));
        this.addFactorItem(new FunctionPointInfluenceFactorItem(Resources.getSystem().getString(R.string.function_point_influence_factor_item_transaction_rate), 0, 5));

        ArrayList<FunctionPointInfluenceFactorItem> subItems = new ArrayList<>();
        subItems.add(new FunctionPointInfluenceFactorItem(Resources.getSystem().getString(R.string.function_point_influence_factor_item_arithmetic_operation), 0, 10));
        subItems.add(new FunctionPointInfluenceFactorItem(Resources.getSystem().getString(R.string.function_point_influence_factor_item_control_procedure), 0, 5));
        subItems.add(new FunctionPointInfluenceFactorItem(Resources.getSystem().getString(R.string.function_point_influence_factor_item_exception_regulation), 0, 10));
        subItems.add(new FunctionPointInfluenceFactorItem(Resources.getSystem().getString(R.string.function_point_influence_factor_item_logic), 0, 5));
        this.addFactorItem(new FunctionPointInfluenceFactorItem(Resources.getSystem().getString(R.string.function_point_influence_factor_item_processing_logic), 0, 0, true, subItems));

        this.addFactorItem(new FunctionPointInfluenceFactorItem(Resources.getSystem().getString(R.string.function_point_influence_factor_item_reusability), 0, 5));
        this.addFactorItem(new FunctionPointInfluenceFactorItem(Resources.getSystem().getString(R.string.function_point_influence_factor_item_stock_conversion), 0, 5));
        this.addFactorItem(new FunctionPointInfluenceFactorItem(Resources.getSystem().getString(R.string.function_point_influence_factor_item_adaptability), 0, 5));
    }

}
