package com.mobileprojectestimator.mobileprojectestimator.DataObjects;

/**
 * Created by Oliver Fries on 28.10.2015.
 * <p/>
 * Data Class for the project properties for each project.
 */
public class ProjectProperties
{
    /**
     * The market of the project
     */
    private String market;
    /**
     * the kind of the development
     */
    private String developmentKind;
    /**
     * what methology will be uses
     */
    private String processMethology;
    /**
     * the used programming language
     */
    private String programmingLanguage;
    /**
     * the target platform
     */
    private String platform;
    /**
     * The sector for which the project is developed
     */
    private String industrySector;

    public String getMarket()
    {
        return market;
    }

    public void setMarket(String market)
    {
        this.market = market;
    }

    public String getDevelopmentKind()
    {
        return developmentKind;
    }

    public void setDevelopmentKind(String developmentKind)
    {
        this.developmentKind = developmentKind;
    }

    public String getProcessMethology()
    {
        return processMethology;
    }

    public void setProcessMethology(String processMethology)
    {
        this.processMethology = processMethology;
    }

    public String getProgrammingLanguage()
    {
        return programmingLanguage;
    }

    public void setProgrammingLanguage(String programmingLanguage)
    {
        this.programmingLanguage = programmingLanguage;
    }

    public String getPlatform()
    {
        return platform;
    }

    public void setPlatform(String platform)
    {
        this.platform = platform;
    }

    public void setIndustrySector(String industrySector)
    {
        this.industrySector = industrySector;
    }

    public String getIndustrySector()
    {
        return industrySector;
    }
}
