package com.mobileprojectestimator.mobileprojectestimator.DataObjects.Project;

import android.content.Context;

import com.mobileprojectestimator.mobileprojectestimator.DataObjects.Items.Estimation.EstimationItem;

import java.util.ArrayList;

/**
 * Created by Oliver Fries on 28.01.2016, 11:00.
 * Project: MobileProjectEstimator
 */
public class RelatedProject extends Project
{
    private double relatedPercentage;

    /**
     * Create an empty project
     *
     * @param current
     */
    @SuppressWarnings("JavaDoc")
    public RelatedProject(Context current)
    {
        super(current);
        relatedPercentage = 0.0;
    }

    public double getRelatedPercentage()
    {
        return relatedPercentage;
    }

    public void setRelatedPercentage(double relatedPercentage)
    {
        this.relatedPercentage = relatedPercentage;
    }

    public void convertFromProject(Project project)
    {
        this.setProjectId(project.getProjectId());
        this.setEstimationMethod(project.getEstimationMethod());
        this.setEvaluatedPersonDays(project.getEvaluatedPersonDays());
        this.setInfluencingFactor(project.getInfluencingFactor());
        this.setTitle(project.getTitle());
        this.setEstimationItems(new ArrayList<EstimationItem>());
        if(project.getEstimationItems()!=null&& !project.getEstimationItems().isEmpty()){
            this.setEstimationItems(project.getEstimationItems());
        }
        this.setImage(project.getImage());
        this.setCreationDate(project.getCreationDate());
        this.setFinalPersonDays(project.getFinalPersonDays());
        this.setEvaluatedPoints(project.getEvaluatedPoints());
        this.setIconId(project.getIconId());
        this.setIconName(project.getIconName());
        this.setInfluencingFactor(project.getInfluencingFactor());
        this.setIsDeleted(project.isDeleted());
        this.setIsTerminated(project.isTerminated());
        this.setProjectDescription(project.getProjectDescription());
        this.setProjectProperties(project.getProjectProperties());
        this.setSumOfInfluences(project.getSumOfInfluences());
    }
}
