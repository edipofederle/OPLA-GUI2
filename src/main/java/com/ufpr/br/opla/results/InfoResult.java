/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ufpr.br.opla.results;

/**
 *
 * @author elf
 */
public class InfoResult {
    
    private Integer id;
    private String name; // INFO_PLANAME_N (where n is number of run)
    private String executionId;
    private String listOfConcerns; //separated by pipe |
    private Integer numberOfPackages;
    private Integer numberOfVariabilities;
    private Integer numberOfClasses;
    private Integer numberOfInterfaces;
    private Integer numberOfDependencies;
    private Integer numberOfAbstraction;
    private Integer numberOfAssociations;
    private Integer numberOfGeneralizations;
    private Integer numberOfassociationsClass;

    public String getListOfConcerns() {
        return listOfConcerns;
    }
    
    /**
    *  String listOfConcerns should be a string of values separated with pipes |.
    * 
    * Ex: concern1 | concern2 | concerns3
    * 
    * @param listOfConcerns 
    */
    public void setListOfConcerns(String listOfConcerns) {
        this.listOfConcerns = listOfConcerns;
    }

    public Integer getNumberOfPackages() {
        return numberOfPackages;
    }

    public void setNumberOfPackages(Integer numberOfPackages) {
        this.numberOfPackages = numberOfPackages;
    }

    public Integer getNumberOfVariabilities() {
        return numberOfVariabilities;
    }

    public void setNumberOfVariabilities(Integer numberOfVariabilities) {
        this.numberOfVariabilities = numberOfVariabilities;
    }

    public Integer getNumberOfClasses() {
        return numberOfClasses;
    }

    public void setNumberOfClasses(Integer numberOfClasses) {
        this.numberOfClasses = numberOfClasses;
    }

    public Integer getNumberOfInterfaces() {
        return numberOfInterfaces;
    }

    public void setNumberOfInterfaces(Integer numberOfInterfaces) {
        this.numberOfInterfaces = numberOfInterfaces;
    }

    public Integer getNumberOfDependencies() {
        return numberOfDependencies;
    }

    public void setNumberOfDependencies(Integer numberOfDependencies) {
        this.numberOfDependencies = numberOfDependencies;
    }

    public Integer getNumberOfAbstraction() {
        return numberOfAbstraction;
    }

    public void setNumberOfAbstraction(Integer numberOfAbstraction) {
        this.numberOfAbstraction = numberOfAbstraction;
    }

    public Integer getNumberOfAssociations() {
        return numberOfAssociations;
    }

    public void setNumberOfAssociations(Integer numberOfAssociations) {
        this.numberOfAssociations = numberOfAssociations;
    }

    public Integer getNumberOfGeneralizations() {
        return numberOfGeneralizations;
    }

    public void setNumberOfGeneralizations(Integer numberOfGeneralizations) {
        this.numberOfGeneralizations = numberOfGeneralizations;
    }

    public Integer getNumberOfassociationsClass() {
        return numberOfassociationsClass;
    }

    public void setNumberOfassociationsClass(Integer numberOfassociationsClass) {
        this.numberOfassociationsClass = numberOfassociationsClass;
    }  

    public String getName() {
        return name;
    }

    /**
     * name must be "plaName_runNumber"
     * 
     * Then his method will set name 
     * like: INFO_plaName_runNumber
     * 
     * @param name 
     */
    public void setName(String name) {
        this.name = "INFO_"+name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getExecutionId() {
        return executionId;
    }

    public void setExecutionId(String executionId) {
        this.executionId = executionId;
    }


    
}
