package com.mobileprojectestimator.mobileprojectestimator.DataObjects;

/**
 * Created by Oliver Fries on 28.10.2015.
 */
public class ProjectProperties {
    private String market;
    private String developmentKind;
    private String processMethology;
    private String programmingLanguage;
    private String platform;
    private String industrySector;

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getDevelopmentKind() {
        return developmentKind;
    }

    public void setDevelopmentKind(String developmentKind) {
        this.developmentKind = developmentKind;
    }

    public String getProcessMethology() {
        return processMethology;
    }

    public void setProcessMethology(String processMethology) {
        this.processMethology = processMethology;
    }

    public String getProgrammingLanguage() {
        return programmingLanguage;
    }

    public void setProgrammingLanguage(String programmingLanguage) {
        this.programmingLanguage = programmingLanguage;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public void setIndustrySector(String industrySector) {
        this.industrySector = industrySector;
    }

    public String getIndustrySector() {
        return industrySector;
    }
}
