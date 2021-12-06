package de.tech26.robotfactory.domain;

public class StockUnit {

    private String code;

    private String  part;

    private Integer availableCount;


    public StockUnit(String code, Integer availableCount) {
        this.code = code;
        this.availableCount = availableCount;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getAvailableCount() {
        return availableCount;
    }

    public void setAvailableCount(Integer availableCount) {
        this.availableCount = availableCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StockUnit stockUnit = (StockUnit) o;

        return code.equals(stockUnit.code);
    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }
}
