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
    double retailPriceReaderMagazine = 4.95;
    double retailPriceTimeMagazine = 7.95;
    double retailPricePeopleMagazine = 3.95;
    double retailPriceNationalMagazine = 5.95;

    double costOfGoodsReaderMagazine = 2.20;
    double costOfGoodsTimeMagazine = 3.80;
    double costOfGoodsPeopleMagazine = 1.95;
    double costOfGoodsNationalMagazine = 2.40;
    private List<Row> salesDataList;

    public Service() {
        try {
            readFileOfSalesData();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void readFileOfSalesData() throws IOException {
        String route = "src/main/resources/sales-data.txt";
        List<String> lines = Files.readAllLines(Paths.get(route));

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

    private double getAverageSalesVolumeOfReader(List<Row> rows, Magazine magazine) {
        ToDoubleFunction<Row> toDouble = row ->
                switch (magazine) {
                    case READER_DIGEST -> row.getReaderMagazine();
                    case TIME -> row.getTimeMagazine();
                    case PEOPLE -> row.getPeopleMagazine();
                    case NATIONAL_GEOGRAPHIC -> row.getNationalMagazine();
                };

        return rows.stream()
                .mapToDouble(toDouble)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public ObservableList<RowTable> getObservableList() {
        ObservableList<RowTable> list = FXCollections.observableArrayList();
        RowTable salesVolumeRow = new RowTable(0, 0, 0, 0);
        RowTable retailsPriceRow = new RowTable(0, 0, 0, 0);
        RowTable costOfGoodsRow = new RowTable(0, 0, 0, 0);
        RowTable grossProfitRow = new RowTable(0, 0, 0, 0);
        salesVolumeRow.setValue("Sales Volume");
        retailsPriceRow.setValue("Retails Price");
        costOfGoodsRow.setValue("Cost of Goods");
        grossProfitRow.setValue("Gross Profit");

        list.addAll(List.of(salesVolumeRow, retailsPriceRow, costOfGoodsRow, grossProfitRow));
        return list;
    }
    //        double totalGrossProfit = grossProfitReader + grossProfitTime + grossProfitPeople + grossProfitNational;

    private List<Information> getListOfInformation() {
        /* ========== VALUES CALCULATED ========== */
        double averageReader = getAverageSalesVolumeOfReader(salesDataList, READER_DIGEST);
        double averageTime = getAverageSalesVolumeOfReader(salesDataList, TIME);
        double averagePeople = getAverageSalesVolumeOfReader(salesDataList, PEOPLE);
        double averageNational = getAverageSalesVolumeOfReader(salesDataList, NATIONAL_GEOGRAPHIC);

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

    public void loadValues(double retailPriceReaderMagazine, double retailPriceTimeMagazine, double retailPricePeopleMagazine,
                            double retailPriceNationalMagazine, double costOfGoodsReaderMagazine, double costOfGoodsTimeMagazine,
                            double costOfGoodsPeopleMagazine, double costOfGoodsNationalMagazine) {
        this.retailPriceReaderMagazine = retailPriceReaderMagazine;
        this.retailPriceTimeMagazine = retailPriceTimeMagazine;
        this.retailPricePeopleMagazine = retailPricePeopleMagazine;
        this.retailPriceNationalMagazine = retailPriceNationalMagazine;

        this.costOfGoodsReaderMagazine = costOfGoodsReaderMagazine;
        this.costOfGoodsTimeMagazine = costOfGoodsTimeMagazine;
        this.costOfGoodsPeopleMagazine = costOfGoodsPeopleMagazine;
        this.costOfGoodsNationalMagazine = costOfGoodsNationalMagazine;
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
