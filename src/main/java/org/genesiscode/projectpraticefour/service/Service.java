package org.genesiscode.projectpraticefour.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.genesiscode.projectpraticefour.view.RowTable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.function.ToDoubleFunction;

import static org.genesiscode.projectpraticefour.service.Decimal.extractDecimals;
import static org.genesiscode.projectpraticefour.service.Magazine.*;

public class Service {

    /* ========== INPUT DATA FOR THE USER ========== */
    private Double retailPriceReaderMagazine = 0.0, retailPriceTimeMagazine = 0.0, retailPricePeopleMagazine = 0.0, retailPriceNationalMagazine = 0.0, costOfGoodsReaderMagazine = 0.0, costOfGoodsTimeMagazine = 0.0, costOfGoodsPeopleMagazine = 0.0, costOfGoodsNationalMagazine = 0.0;
    private List<Row> salesDataList;
    private final ObservableList<RowTable> observableList;
    private final RowTable rowSalesVolume, rowRetailPrice, rowCostOfGoods, rowGrossProfit;

    private Double grossProfitReader = 0.0, grossProfitTime = 0.0, grossProfitPeople = 0.0, grossProfitNational = 0.0;
    private final double salesVolumeReader, salesVolumeTime, salesVolumePeople, salesVolumeNational;
    private double totalGrossProfit = 0;

    public Service() {
        readFileOfSalesData();
        // Initialize the rows
        this.salesVolumeReader = getOneValueOfSalesVolume(READER_DIGEST);
        this.salesVolumeTime = getOneValueOfSalesVolume(TIME);
        this.salesVolumePeople = getOneValueOfSalesVolume(PEOPLE);
        this.salesVolumeNational = getOneValueOfSalesVolume(NATIONAL_GEOGRAPHIC);

        rowSalesVolume = new RowTable("Sales Volume", salesVolumeReader, salesVolumeTime, salesVolumePeople, salesVolumeNational);
        rowRetailPrice = new RowTable("Retail Price", retailPriceReaderMagazine, retailPriceTimeMagazine, retailPricePeopleMagazine, retailPriceNationalMagazine);
        rowCostOfGoods = new RowTable("Cost of Goods", costOfGoodsReaderMagazine, costOfGoodsTimeMagazine, costOfGoodsPeopleMagazine, costOfGoodsNationalMagazine);
        rowGrossProfit = new RowTable("Gross Profit", grossProfitReader, grossProfitTime, grossProfitPeople, grossProfitNational);

        observableList = FXCollections.observableArrayList();
        observableList.addAll(List.of(rowSalesVolume, rowRetailPrice, rowCostOfGoods, rowGrossProfit));
    }

    public ObservableList<RowTable> getObservableList() {
        return observableList;
    }

    public double getTotalGrossProfit() {
        return totalGrossProfit;
    }

    private void readFileOfSalesData() {
        String route = "src/main/resources/sales-data.txt";
        List<String> lines = List.of();
        try {
            lines = Files.readAllLines(Paths.get(route));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        salesDataList = lines.stream()
                .skip(2)
                .map(this::create)
                .toList();
    }

    private Row create(String line) {
        List<String> values = Arrays.stream(line.split(" "))
                .filter(value -> !value.isBlank())
                .toList();

        return new Row(
                Double.parseDouble(values.get(0)),
                Double.parseDouble(values.get(1)),
                Double.parseDouble(values.get(2)),
                Double.parseDouble(values.get(3))
        );
    }

    private double getOneValueOfSalesVolume(Magazine magazine) {
        ToDoubleFunction<Row> toDouble = row ->
                switch (magazine) {
                    case READER_DIGEST -> row.getReaderMagazine();
                    case TIME -> row.getTimeMagazine();
                    case PEOPLE -> row.getPeopleMagazine();
                    case NATIONAL_GEOGRAPHIC -> row.getNationalMagazine();
                };

        return salesDataList.stream()
                .mapToDouble(toDouble)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public void loadValues(Double retailPriceReaderMagazine, Double retailPriceTimeMagazine, Double retailPricePeopleMagazine,
                            Double retailPriceNationalMagazine, Double costOfGoodsReaderMagazine, Double costOfGoodsTimeMagazine,
                            Double costOfGoodsPeopleMagazine, Double costOfGoodsNationalMagazine) {
        this.retailPriceReaderMagazine = retailPriceReaderMagazine;
        this.retailPriceTimeMagazine = retailPriceTimeMagazine;
        this.retailPricePeopleMagazine = retailPricePeopleMagazine;
        this.retailPriceNationalMagazine = retailPriceNationalMagazine;

        this.costOfGoodsReaderMagazine = costOfGoodsReaderMagazine;
        this.costOfGoodsTimeMagazine = costOfGoodsTimeMagazine;
        this.costOfGoodsPeopleMagazine = costOfGoodsPeopleMagazine;
        this.costOfGoodsNationalMagazine = costOfGoodsNationalMagazine;
        rowRetailPrice.setAllField(retailPriceReaderMagazine, retailPriceTimeMagazine, retailPricePeopleMagazine, retailPriceNationalMagazine);
        rowCostOfGoods.setAllField(costOfGoodsReaderMagazine, costOfGoodsTimeMagazine, costOfGoodsPeopleMagazine, costOfGoodsNationalMagazine);

        rowGrossProfit.setAllField(
                extractDecimals(2, salesVolumeReader * (this.retailPriceReaderMagazine - this.costOfGoodsReaderMagazine)),
                extractDecimals(2, salesVolumeTime * (this.retailPriceTimeMagazine - this.costOfGoodsTimeMagazine)),
                extractDecimals(2, salesVolumePeople * (this.retailPricePeopleMagazine - this.costOfGoodsPeopleMagazine)),
                extractDecimals(2, salesVolumeNational * (this.retailPriceNationalMagazine - this.costOfGoodsNationalMagazine))
        );

        totalGrossProfit = extractDecimals(2, rowGrossProfit.getReaderMagazine() + rowGrossProfit.getTimeMagazine()
                + rowGrossProfit.getPeopleMagazine() + rowGrossProfit.getNationalMagazine());
    }
}
