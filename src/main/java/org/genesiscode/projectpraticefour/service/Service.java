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
import java.util.stream.DoubleStream;

import static org.genesiscode.projectpraticefour.service.Decimal.extractDecimals;
import static org.genesiscode.projectpraticefour.service.Magazine.*;

public class Service {

    /* ========== INPUT DATA FOR THE USER ========== */
    private Double retailPriceReaderMagazine = 0.0, retailPriceTimeMagazine = 0.0, retailPricePeopleMagazine = 0.0, retailPriceNationalMagazine = 0.0,
            costOfGoodsReaderMagazine = 0.0, costOfGoodsTimeMagazine = 0.0, costOfGoodsPeopleMagazine = 0.0, costOfGoodsNationalMagazine = 0.0;
    private List<Row> salesDataList;
    private final ObservableList<RowTable> observableList;
    private final RowTable rowSalesVolume, rowRetailPrice, rowCostOfGoods, rowGrossProfit;

    private double grossProfitReader, grossProfitTime, grossProfitPeople, grossProfitNational = 0;
    private double salesVolumeReader, salesVolumeTime, salesVolumePeople, salesVolumeNational;
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

    public static void main(String[] args) {
        String route = "src/main/resources/sales-data.txt";
        List<String> lines = List.of();

        try {
            lines = Files.readAllLines(Paths.get(route));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        List<String> list = lines.stream()
                .skip(2)
                .toList();

        System.out.println(list.get((int) Math.floor(Math.random() * (list.size() + 1))));
    }

    private double getOneValueOfSalesVolume(Magazine magazine) {
        OptionComboBox option = OptionComboBox.FIRST;
        return optionSelect(magazine, option);
    }

    public void changeOption(OptionComboBox option) {
        rowSalesVolume.setAllField(
                optionSelect(READER_DIGEST, option),
                optionSelect(TIME, option),
                optionSelect(PEOPLE, option),
                optionSelect(NATIONAL_GEOGRAPHIC, option)
        );

        salesVolumeReader = rowSalesVolume.getReaderMagazine();
        salesVolumeTime = rowSalesVolume.getTimeMagazine();
        salesVolumePeople = rowSalesVolume.getPeopleMagazine();
        salesVolumeNational = rowSalesVolume.getNationalMagazine();
    }

    private double optionSelect(Magazine magazine, OptionComboBox option) {
        ToDoubleFunction<Row> toDouble = row ->
                switch (magazine) {
                    case READER_DIGEST -> row.getReaderMagazine();
                    case TIME -> row.getTimeMagazine();
                    case PEOPLE -> row.getPeopleMagazine();
                    case NATIONAL_GEOGRAPHIC -> row.getNationalMagazine();
                };

        double result = switch (option) {
            case FIRST ->
                    salesDataList.stream()
                            .mapToDouble(toDouble)
                            .findFirst()
                            .orElseThrow(IllegalArgumentException::new);

            case ANY -> getValueAny(magazine);

            case AVERAGE ->
                    salesDataList.stream()
                            .mapToDouble(toDouble)
                            .average()
                            .orElseThrow(IllegalArgumentException::new);
        };

        return Decimal.extractDecimals(2, result);

    }

    private double getValueAny(Magazine magazine) {
        Row row = salesDataList.get((int) Math.floor(Math.random() * (salesDataList.size() + 1)));
        return switch (magazine) {
            case READER_DIGEST -> row.getReaderMagazine();
            case TIME -> row.getTimeMagazine();
            case PEOPLE -> row.getPeopleMagazine();
            case NATIONAL_GEOGRAPHIC -> row.getNationalMagazine();
        };
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
    }

    public void start() {
        rowGrossProfit.setAllField(
                extractDecimals(2, salesVolumeReader * (this.retailPriceReaderMagazine - this.costOfGoodsReaderMagazine)),
                extractDecimals(2, salesVolumeTime * (this.retailPriceTimeMagazine - this.costOfGoodsTimeMagazine)),
                extractDecimals(2, salesVolumePeople * (this.retailPricePeopleMagazine - this.costOfGoodsPeopleMagazine)),
                extractDecimals(2, salesVolumeNational * (this.retailPriceNationalMagazine - this.costOfGoodsNationalMagazine))
        );

        grossProfitReader = rowGrossProfit.getReaderMagazine();
        grossProfitTime = rowGrossProfit.getTimeMagazine();
        grossProfitPeople = rowGrossProfit.getPeopleMagazine();
        grossProfitNational = rowGrossProfit.getNationalMagazine();

        totalGrossProfit = extractDecimals(2, rowGrossProfit.getReaderMagazine() + rowGrossProfit.getTimeMagazine()
                + rowGrossProfit.getPeopleMagazine() + rowGrossProfit.getNationalMagazine());
    }

    public ObservableList<RowTable> getObservableList() {
        return observableList;
    }

    public double getSalesVolumeReader() {
        return salesVolumeReader;
    }

    public double getSalesVolumeTime() {
        return salesVolumeTime;
    }

    public double getSalesVolumePeople() {
        return salesVolumePeople;
    }

    public double getSalesVolumeNational() {
        return salesVolumeNational;
    }

    public double getTotalGrossProfit() {
        return totalGrossProfit;
    }
}
