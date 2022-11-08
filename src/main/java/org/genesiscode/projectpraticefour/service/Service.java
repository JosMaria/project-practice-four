package org.genesiscode.projectpraticefour.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Service {

    public static void main(String[] args) throws IOException {
        String route = "src/main/resources/sales-data.txt";
        List<String> lines = Files.readAllLines(Paths.get(route));
        lines.stream()
                .skip(2)
                .forEach(System.out::println);
    }
}
