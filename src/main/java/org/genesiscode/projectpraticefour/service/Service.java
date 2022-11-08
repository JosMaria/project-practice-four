package org.genesiscode.projectpraticefour.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.genesiscode.projectpraticefour.service.Magazine.*;

public class Service {

    private List<Row> readSalesData() throws IOException {
        String route = "src/main/resources/sales-data.txt";
        List<String> lines = Files.readAllLines(Paths.get(route));

        return lines.stream()
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
        return rows.stream()
                .mapToDouble(row -> getValuesGivenMagazine(row, magazine))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    private double getValuesGivenMagazine(Row row, Magazine magazine) {
         return switch (magazine) {
            case READER_DIGEST -> row.getReaderMagazine();
            case TIME -> row.getTimeMagazine();
            case PEOPLE -> row.getPeopleMagazine();
            case NATIONAL_GEOGRAPHIC -> row.getNationalMagazine();
        };
    }

    public static void main(String[] args) throws IOException {
        Service service = new Service();
        List<Row> lines = service.readSalesData();
        double averageReader = service.getAverageSalesVolumeOfReader(lines, READER_DIGEST);
        double averageTimes = service.getAverageSalesVolumeOfReader(lines, TIME);
        double averagePeople = service.getAverageSalesVolumeOfReader(lines, PEOPLE);
        double averageNational = service.getAverageSalesVolumeOfReader(lines, NATIONAL_GEOGRAPHIC);

        /* ========== INPUT DATA ========== */
        double retailPriceReaderMagazine = 4.95;
        double retailPriceTimeMagazine = 7.95;
        double retailPricePeopleMagazine = 3.95;
        double retailPriceNationalMagazine = 5.95;

        double costOfGoodsReaderMagazine = 2.20;
        double costOfGoodsTimeMagazine = 3.80;
        double costOfGoodsPeopleMagazine = 1.95;
        double costOfGoodsNationalMagazine = 2.40;

        Information informationReader = new Information(averageReader, retailPriceReaderMagazine, costOfGoodsReaderMagazine, 0);
        Information informationTimes = new Information(averageTimes, retailPriceTimeMagazine, costOfGoodsTimeMagazine, 0);
        Information informationPeople = new Information(averagePeople, retailPricePeopleMagazine, costOfGoodsPeopleMagazine, 0);
        Information informationNational = new Information(averageNational, retailPriceNationalMagazine, costOfGoodsNationalMagazine, 0);
        System.out.println(informationReader);
        System.out.println(informationTimes);
        System.out.println(informationPeople);
        System.out.println(informationNational);
    }
}
