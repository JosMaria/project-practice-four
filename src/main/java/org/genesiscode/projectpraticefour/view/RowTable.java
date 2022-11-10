package org.genesiscode.projectpraticefour.view;

public class RowTable {

    private String value;
    private Double readerMagazine;
    private Double timeMagazine;
    private Double peopleMagazine;
    private Double nationalMagazine;

    public RowTable(String value, double readerMagazine, double timeMagazine, double peopleMagazine, double nationalMagazine) {
        this.value = value;
        this.readerMagazine = readerMagazine;
        this.timeMagazine = timeMagazine;
        this.peopleMagazine = peopleMagazine;
        this.nationalMagazine = nationalMagazine;
    }

    public void setAllField(double readerMagazine, double timeMagazine, double peopleMagazine, double nationalMagazine) {
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

    public void setReaderMagazine(double readerMagazine) {
        this.readerMagazine = readerMagazine;
    }

    public double getTimeMagazine() {
        return timeMagazine;
    }

    public void setTimeMagazine(double timeMagazine) {
        this.timeMagazine = timeMagazine;
    }

    public double getPeopleMagazine() {
        return peopleMagazine;
    }

    public void setPeopleMagazine(double peopleMagazine) {
        this.peopleMagazine = peopleMagazine;
    }

    public double getNationalMagazine() {
        return nationalMagazine;
    }

    public void setNationalMagazine(double nationalMagazine) {
        this.nationalMagazine = nationalMagazine;
    }

    @Override
    public String toString() {
        return "RowTable{" +
                "value='" + value + '\'' +
                ", readerMagazine=" + readerMagazine +
                ", timeMagazine=" + timeMagazine +
                ", peopleMagazine=" + peopleMagazine +
                ", nationalMagazine=" + nationalMagazine +
                '}';
    }
}
