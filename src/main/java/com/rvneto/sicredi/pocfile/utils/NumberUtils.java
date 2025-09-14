package com.rvneto.sicredi.pocfile.utils;

import java.math.BigDecimal;

public class NumberUtils {

    public static BigDecimal generateRandomValue() {
        double min = 1.00;
        double max = 10000.00;
        double randomValue = min + (Math.random() * (max - min));
        return BigDecimal.valueOf(Math.round(randomValue * 100.0) / 100.0);
    }

}
