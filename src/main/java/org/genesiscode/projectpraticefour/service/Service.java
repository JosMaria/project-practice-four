package org.genesiscode.projectpraticefour.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

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

    public static void main(String[] args) throws IOException {
        Service service = new Service();
        List<Row> lines = service.readSalesData();
        System.out.println(lines.size());
    }
}
