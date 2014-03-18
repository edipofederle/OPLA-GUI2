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
public class InfoResults {
    
    private String name; // INFO_PLANAME_N (where n is number of run)
    private String listOfConcerns; //separated by pipe |
    private int numberOfPackages;
    private int numberOfVariabilities;
    private int numberOfClasses;
    private int numberOfInterfaces;
    private int numberOfDependencies;
    private int numberOfAbstraction;
    private int numberOfAssociations;
    private int numberOfGeneralizations;
    private int numberOfassociationsClass;

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

    public int getNumberOfPackages() {
        return numberOfPackages;
    }

    public void setNumberOfPackages(int numberOfPackages) {
        this.numberOfPackages = numberOfPackages;
    }

    public int getNumberOfVariabilities() {
        return numberOfVariabilities;
    }

    public void setNumberOfVariabilities(int numberOfVariabilities) {
        this.numberOfVariabilities = numberOfVariabilities;
    }

    public int getNumberOfClasses() {
        return numberOfClasses;
    }

    public void setNumberOfClasses(int numberOfClasses) {
        this.numberOfClasses = numberOfClasses;
    }

    public int getNumberOfInterfaces() {
        return numberOfInterfaces;
    }

    public void setNumberOfInterfaces(int numberOfInterfaces) {
        this.numberOfInterfaces = numberOfInterfaces;
    }

    public int getNumberOfDependencies() {
        return numberOfDependencies;
    }

    public void setNumberOfDependencies(int numberOfDependencies) {
        this.numberOfDependencies = numberOfDependencies;
    }

    public int getNumberOfAbstraction() {
        return numberOfAbstraction;
    }

    public void setNumberOfAbstraction(int numberOfAbstraction) {
        this.numberOfAbstraction = numberOfAbstraction;
    }

    public int getNumberOfAssociations() {
        return numberOfAssociations;
    }

    public void setNumberOfAssociations(int numberOfAssociations) {
        this.numberOfAssociations = numberOfAssociations;
    }

    public int getNumberOfGeneralizations() {
        return numberOfGeneralizations;
    }

    public void setNumberOfGeneralizations(int numberOfGeneralizations) {
        this.numberOfGeneralizations = numberOfGeneralizations;
    }

    public int getNumberOfassociationsClass() {
        return numberOfassociationsClass;
    }

    public void setNumberOfassociationsClass(int numberOfassociationsClass) {
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
    
}
