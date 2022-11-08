package org.genesiscode.projectpraticefour.service;

public class Information {

    private String title;
    private double salesVolume;
    private double retailPrice;
    private double costOfGoods;
    private double grossProfit;

    public Information(double salesVolume, double retailPrice, double costOfGoods, double grossProfit) {
        this.salesVolume = salesVolume;
        this.retailPrice = retailPrice;
        this.costOfGoods = costOfGoods;
        this.grossProfit = grossProfit;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getSalesVolume() {
        return salesVolume;
    }

    public void setSalesVolume(double salesVolume) {
        this.salesVolume = salesVolume;
    }

    public double getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(double retailPrice) {
        this.retailPrice = retailPrice;
    }

    public double getCostOfGoods() {
        return costOfGoods;
    }

    public void setCostOfGoods(double costOfGoods) {
        this.costOfGoods = costOfGoods;
    }

    public double getGrossProfit() {
        return grossProfit;
    }

    public void setGrossProfit(double grossProfit) {
        this.grossProfit = grossProfit;
    }

    @Override
    public String toString() {
        String row =
            """
                {
                    salesVolume: %s,
                    retailPrice: %s,
                    costOfGoods: %s,
                    grossProfit: %s
                }
            """;
        return String.format(row, salesVolume, retailPrice, costOfGoods, grossProfit);
    }
}
