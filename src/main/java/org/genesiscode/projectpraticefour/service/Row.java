package org.genesiscode.projectpraticefour.service;

public class Row {

    private double readerMagazine;
    private double timeMagazine;
    private double peopleMagazine;
    private double nationalMagazine;

    public Row(double readerMagazine, double timeMagazine, double peopleMagazine, double nationalMagazine) {
        this.readerMagazine = readerMagazine;
        this.timeMagazine = timeMagazine;
        this.peopleMagazine = peopleMagazine;
        this.nationalMagazine = nationalMagazine;
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
        String string =
            """
                {
                   readerMagazine: %s,
                   timeMagazine: %s,
                   peopleMagazine: %s,
                   nationalMagazine: %s
                }
            """;
        return String.format(string,  readerMagazine, timeMagazine, peopleMagazine, nationalMagazine);
    }
}
