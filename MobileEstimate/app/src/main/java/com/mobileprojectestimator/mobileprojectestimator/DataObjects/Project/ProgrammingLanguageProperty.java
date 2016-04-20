package com.mobileprojectestimator.mobileprojectestimator.DataObjects.Project;

/**
 * Created by Oliver Fries on 28.01.2016, 15:52.
 * Project: MobileProjectEstimator
 */
public class ProgrammingLanguageProperty
{

    private int id;
    private int imperative;
    private int objectoriented;
    private int functional;
    private int procedural;
    private int generic;
    private int reflective;
    private int eventdriven;
    private int failsafe;
    private int typeSafety;
    private int typeExpression;
    private int typeCompatibility;
    private int TypeChecking;

    public ProgrammingLanguageProperty(){
        id = 0;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getImperative()
    {
        return imperative;
    }

    public void setImperative(int imperative)
    {
        this.imperative = imperative;
    }

    public int getObjectoriented()
    {
        return objectoriented;
    }

    public void setObjectoriented(int objectoriented)
    {
        this.objectoriented = objectoriented;
    }

    public int getFunctional()
    {
        return functional;
    }

    public void setFunctional(int functional)
    {
        this.functional = functional;
    }

    public int getProcedural()
    {
        return procedural;
    }

    public void setProcedural(int procedural)
    {
        this.procedural = procedural;
    }

    public int getGeneric()
    {
        return generic;
    }

    public void setGeneric(int generic)
    {
        this.generic = generic;
    }

    public int getReflective()
    {
        return reflective;
    }

    public void setReflective(int reflective)
    {
        this.reflective = reflective;
    }

    public int getEventdriven()
    {
        return eventdriven;
    }

    public void setEventdriven(int eventdriven)
    {
        this.eventdriven = eventdriven;
    }

    public int getFailsafe()
    {
        return failsafe;
    }

    public void setFailsafe(int failsafe)
    {
        this.failsafe = failsafe;
    }

    public int getTypeSafety()
    {
        return typeSafety;
    }

    public void setTypeSafety(int typeSafety)
    {
        this.typeSafety = typeSafety;
    }

    public int getTypeExpression()
    {
        return typeExpression;
    }

    public void setTypeExpression(int typeExpression)
    {
        this.typeExpression = typeExpression;
    }

    public int getTypeCompatibility()
    {
        return typeCompatibility;
    }

    public void setTypeCompatibility(int typeCompatibility)
    {
        this.typeCompatibility = typeCompatibility;
    }

    public int getTypeChecking()
    {
        return TypeChecking;
    }

    public void setTypeChecking(int typeChecking)
    {
        TypeChecking = typeChecking;
    }

}
