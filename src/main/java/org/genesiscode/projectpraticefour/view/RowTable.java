package org.genesiscode.projectpraticefour.view;

public class RowTable {

    private String value;
    private double readerMagazine;
    private double timeMagazine;
    private double peopleMagazine;
    private double nationalMagazine;

    public RowTable(double readerMagazine, double timeMagazine, double peopleMagazine, double nationalMagazine) {
        this.readerMagazine = readerMagazine;
        this.timeMagazine = timeMagazine;
        this.peopleMagazine = peopleMagazine;
        this.nationalMagazine = nationalMagazine;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public double getReaderMagazine() {
        return readerMagazine;
    }

    public double getTimeMagazine() {
        return timeMagazine;
    }

    public double getPeopleMagazine() {
        return peopleMagazine;
    }

    public double getNationalMagazine() {
        return nationalMagazine;
    }
}
