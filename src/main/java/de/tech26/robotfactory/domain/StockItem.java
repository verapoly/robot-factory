package de.tech26.robotfactory.domain;

public class StockItem {

    private StockItemId stockItemPk;

    private StockItemStatus status;


    public StockItem(String code, Integer serialNumber) {
        this.stockItemPk = new StockItemId(code, serialNumber);
        this.status = StockItemStatus.AVAILABLE;
    }

    public StockItem(String code, Integer serialNumber, StockItemStatus status) {
        this.stockItemPk = new StockItemId(code, serialNumber);
        this.status = status;

    }

    public StockItemId getStockItemPk() {
        return stockItemPk;
    }

    public void setStockItemPk(StockItemId stockItemPk) {
        this.stockItemPk = stockItemPk;
    }

    public StockItemStatus getStatus() {
        return status;
    }

    public void setStatus(StockItemStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StockItem stockItem = (StockItem) o;

        return stockItemPk.equals(stockItem.stockItemPk);
    }

    @Override
    public int hashCode() {
        return stockItemPk.hashCode();
    }
}
