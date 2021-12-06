package de.tech26.robotfactory.domain;

public class StockItemId {

    private String catalogCode;

    private Integer serialNumber;

    public StockItemId(String code, Integer serialNumber) {
        this.catalogCode = code;
        this.serialNumber = serialNumber;
    }

    public String getCatalogCode() {
        return catalogCode;
    }

    public void setCatalogCode(String catalogCode) {
        this.catalogCode = catalogCode;
    }

    public Integer getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(Integer serialNumber) {
        this.serialNumber = serialNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StockItemId that = (StockItemId) o;

        if (!catalogCode.equals(that.catalogCode)) {
            return false;
        }
        return serialNumber.equals(that.serialNumber);
    }

    @Override
    public int hashCode() {
        int result = catalogCode.hashCode();
        result = 31 * result + serialNumber.hashCode();
        return result;
    }
}
