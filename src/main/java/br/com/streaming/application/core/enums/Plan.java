package br.com.streaming.application.core.enums;


import java.math.BigDecimal;

public enum Plan {
    BASICO(new BigDecimal("19.90")),
    PREMIUM(new BigDecimal("39.90")),
    FAMILIA(new BigDecimal("59.90"));

    private final BigDecimal price;

    Plan(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }
    
    public static boolean isValid(String planName) {
        for (Plan p : Plan.values()) {
            if (p.name().equalsIgnoreCase(planName)) {
                return true;
            }
        }
        return false;
    }
}