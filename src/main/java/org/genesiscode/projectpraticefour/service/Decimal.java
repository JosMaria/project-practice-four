package org.genesiscode.projectpraticefour.service;

import java.text.DecimalFormat;

import static java.lang.Double.parseDouble;

public class Decimal {

    public static double extractDecimals(int limit, double number) {
        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setMaximumFractionDigits(limit);
        return parseDouble(decimalFormat.format(number)
                        .replace(".", "")
                        .replace(',', '.')
        );
    }
}
