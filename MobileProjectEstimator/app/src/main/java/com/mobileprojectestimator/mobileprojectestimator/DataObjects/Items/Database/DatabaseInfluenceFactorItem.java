package com.mobileprojectestimator.mobileprojectestimator.DataObjects.Items.Database;

/**
 * Created by Oliver Fries on 15.12.2015, 13:00.
 * Project: MobileProjectEstimator
 */
@SuppressWarnings("JavaDoc")
public class DatabaseInfluenceFactorItem
{
    private int _id;
    private String _name;
    private int _estimationMethodId;
    private int _influenceFactorId;

    /**
     * Base COnstructor creates an Empty Influence Factor
     */
    public DatabaseInfluenceFactorItem()
    {
        this._id = 0;
        this._name = "";
        this._estimationMethodId = 0;
        this._influenceFactorId = 0;
    }

    /**
     * Construct with the Items to create
     *
     * @param id
     * @param name
     * @param estimationMethodId
     * @param influenceFactorId
     */
    public DatabaseInfluenceFactorItem(int id, String name, int estimationMethodId, int influenceFactorId)
    {
        this._id = id;
        this._name = name;
        this._estimationMethodId = estimationMethodId;
        this._influenceFactorId = influenceFactorId;
    }

    public int get_id()
    {
        return _id;
    }

    public void set_id(int _id)
    {
        this._id = _id;
    }

    public String get_name()
    {
        return _name;
    }

    public void set_name(String _name)
    {
        this._name = _name;
    }

    @SuppressWarnings("unused")
    public int get_estimationMethodId()
    {
        return _estimationMethodId;
    }

    public void set_estimationMethodId(int _estimationMethodId)
    {
        this._estimationMethodId = _estimationMethodId;
    }

    public int get_influenceFactorId()
    {
        return _influenceFactorId;
    }

    public void set_influenceFactorId(int _influenceFactorId)
    {
        this._influenceFactorId = _influenceFactorId;
    }

}
