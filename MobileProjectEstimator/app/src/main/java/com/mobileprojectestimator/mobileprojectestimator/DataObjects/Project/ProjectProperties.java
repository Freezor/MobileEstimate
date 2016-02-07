package com.mobileprojectestimator.mobileprojectestimator.DataObjects.Project;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Oliver Fries on 28.10.2015.
 * <p/>
 * Data Class for the project properties for each project.
 */
public class ProjectProperties
{
    //Static String Variables for acessing values in HashMap
    private static final String MARKET_STRING = "MARKET";
    private static final String DEVELOPMENT_STRING = "DEVELOPMENT";
    private static final String PROCESSMETHGOLOGY_STRING = "PROCESSMETHGOLOGY";
    private static final String PROGRAMMINGLANGUAGE_STRING = "PROGRAMMINGLANGUAGE";
    private static final String PLATFORM_STRING = "PLATFORM";
    private static final String INDUSTRYSECTOR_STRING = "INDUSTRYSECTOR";
    private static final String ARCHITECUTRE_STRING = "ARCHITECTURE";

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

    private String architecture;

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

    public String getIndustrySector()
    {
        return industrySector;
    }

    public void setIndustrySector(String industrySector)
    {
        this.industrySector = industrySector;
    }

    /**
     * Generates a HashMap<String,String> with all Property Values
     *
     * @return
     */
    public Map<? extends String, ? extends String> toHashMap()
    {
        HashMap<String, String> objectHash = new HashMap<>();

        objectHash.put(MARKET_STRING, this.market);
        objectHash.put(DEVELOPMENT_STRING, this.developmentKind);
        objectHash.put(PROCESSMETHGOLOGY_STRING, this.processMethology);
        objectHash.put(PROGRAMMINGLANGUAGE_STRING, this.programmingLanguage);
        objectHash.put(PLATFORM_STRING, this.platform);
        objectHash.put(INDUSTRYSECTOR_STRING, this.industrySector);
        objectHash.put(ARCHITECUTRE_STRING, this.architecture);
        return objectHash;
    }

    /**
     * Set all property values from the object hash. Returns false when something went wrong
     *
     * @param objectMap
     * @return
     */
    public void setPropertyValues(HashMap<String, String> objectMap)
    {
        try
        {
            this.market = objectMap.get(MARKET_STRING);
            this.developmentKind = objectMap.get(DEVELOPMENT_STRING);
            this.processMethology = objectMap.get(PROCESSMETHGOLOGY_STRING);
            this.programmingLanguage = objectMap.get(PROGRAMMINGLANGUAGE_STRING);
            this.platform = objectMap.get(PLATFORM_STRING);
            this.industrySector = objectMap.get(INDUSTRYSECTOR_STRING);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public String getArchitecture()
    {
        return architecture;
    }

    public void setArchitecture(String architecture)
    {
        this.architecture = architecture;
    }
}
