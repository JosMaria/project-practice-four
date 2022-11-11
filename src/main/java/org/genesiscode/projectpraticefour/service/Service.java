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

import static org.genesiscode.projectpraticefour.service.Magazine.*;

public class Service {

    /* ========== INPUT DATA FOR THE USER ========== */
    private Double retailPriceReaderMagazine = 0.0, retailPriceTimeMagazine = 0.0, retailPricePeopleMagazine = 0.0, retailPriceNationalMagazine = 0.0, costOfGoodsReaderMagazine = 0.0, costOfGoodsTimeMagazine = 0.0, costOfGoodsPeopleMagazine = 0.0, costOfGoodsNationalMagazine = 0.0;
    private List<Row> salesDataList;
    private final ObservableList<RowTable> observableList;
    private final RowTable rowSalesVolume, rowRetailPrice, rowCostOfGoods, rowGrossProfit;

    private Double grossProfitReader = 0.0, grossProfitTime = 0.0, grossProfitPeople = 0.0, grossProfitNational = 0.0;
    private double salesVolumeReader, salesVolumeTime, salesVolumePeople, salesVolumeNational;
    private double totalGrossProfit = 0;

    public Service() {
        readFileOfSalesData();
        // Initialize the rows
        this.salesVolumeReader = getOneValueOfSalesVolume(READER_DIGEST);
        this.salesVolumeTime = getOneValueOfSalesVolume(TIME);
        this.salesVolumePeople = getOneValueOfSalesVolume(PEOPLE);
        this.salesVolumeNational = getOneValueOfSalesVolume(NATIONAL_GEOGRAPHIC);

        rowSalesVolume = new RowTable("Sales Volume",
                salesVolumeReader, salesVolumeTime,
                salesVolumePeople, salesVolumeNational);
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
    //        double totalGrossProfit = grossProfitReader + grossProfitTime + grossProfitPeople + grossProfitNational;

    private List<Information> getListOfInformation() {
        /* ========== VALUES CALCULATED ========== */
        double averageReader = getOneValueOfSalesVolume(READER_DIGEST);
        double averageTime = getOneValueOfSalesVolume(TIME);
        double averagePeople = getOneValueOfSalesVolume(PEOPLE);
        double averageNational = getOneValueOfSalesVolume(NATIONAL_GEOGRAPHIC);

        double grossProfitReader = averageReader * (retailPriceReaderMagazine - costOfGoodsReaderMagazine);
        double grossProfitTime = averageTime * (retailPriceTimeMagazine - costOfGoodsTimeMagazine);
        double grossProfitPeople = averagePeople * (retailPricePeopleMagazine - costOfGoodsPeopleMagazine);
        double grossProfitNational = averageNational * (retailPriceNationalMagazine - costOfGoodsNationalMagazine);

        return List.of(
                new Information(averageReader, retailPriceReaderMagazine, costOfGoodsReaderMagazine, grossProfitReader),
                new Information(averageTime, retailPriceTimeMagazine, costOfGoodsTimeMagazine, grossProfitTime),
                new Information(averagePeople, retailPricePeopleMagazine, costOfGoodsPeopleMagazine, grossProfitPeople),
                new Information(averageNational, retailPriceNationalMagazine, costOfGoodsNationalMagazine, grossProfitNational)
        );
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
                 salesVolumeReader * (this.retailPriceReaderMagazine - this.costOfGoodsReaderMagazine),
                salesVolumeTime * (this.retailPriceTimeMagazine - this.costOfGoodsTimeMagazine),
                salesVolumePeople * (this.retailPricePeopleMagazine - this.costOfGoodsPeopleMagazine),
                salesVolumeNational * (this.retailPriceNationalMagazine - this.costOfGoodsNationalMagazine));

        totalGrossProfit =
                rowGrossProfit.getReaderMagazine() + rowGrossProfit.getTimeMagazine()
                        + rowGrossProfit.getPeopleMagazine() + rowGrossProfit.getNationalMagazine();
    }

    private void convertMatrix(List<Information> informationList) {
        List<List<Double>> list = List.of(List.of(), List.of(), List.of(), List.of());
        int index = 0;
        for (Information inf : informationList) {
            List<Double> element = list.get(index);
            element.addAll(List.of(inf.getSalesVolume(), inf.getRetailPrice(), inf.getCostOfGoods(), inf.getGrossProfit()));
            index++;
        }

        for (List<Double> element : list) {
            for (double value : element) {
                System.out.printf("%s  ", value);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {


        /*String[][] matrix = {
                {"sv", "rp", "cp", "gp"},
                {"sv", "rp", "cp", "gp"},
                {"sv", "rp", "cp", "gp"},
                {"sv", "rp", "cp", "gp"}
        };

        List<List<String>> matrixInput = List.of();

        String[][] result = new String[matrix.length][matrix[0].length];

        for (int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix[i].length; j++) {
                result[j][i] = matrix[i][j];
            }
        }

        for (int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix[i].length; j++) {
                System.out.print(result[i][j] + "  ");
            }
            System.out.println();
        }*/

    }
}
