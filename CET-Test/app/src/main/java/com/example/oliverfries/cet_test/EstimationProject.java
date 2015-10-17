package com.example.oliverfries.cet_test;

/**
 * Created by Oliver Fries on 17.10.2015.
 */
public class EstimationProject {

    public String get_estimationMethod() {
        return _estimationMethod;
    }

    public void set_estimationMethod(String _estimationMethod) {
        this._estimationMethod = _estimationMethod;
    }

    private String _estimationMethod;
    private String _projectName;

    public String get_projectName() {
        return _projectName;
    }

    public void set_projectName(String _projectName) {
        this._projectName = _projectName;
    }

    public int get_influencingFactorsId() {
        return _influencingFactorsId;
    }

    public void set_influencingFactorsId(int _influencingFactorsId) {
        this._influencingFactorsId = _influencingFactorsId;
    }

    public ProjectProperties get_projectProperties() {
        return _projectProperties;
    }

    public void set_projectProperties(ProjectProperties _projectProperties) {
        this._projectProperties = _projectProperties;
    }

    public String get_notes() {
        return _notes;
    }

    public void set_notes(String _notes) {
        this._notes = _notes;
    }

    private int _influencingFactorsId;
    private ProjectProperties _projectProperties;
    private String _notes;
}
