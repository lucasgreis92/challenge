package br.com.lgrapplications.southsystem.challenge.inputreader.adapter.model;

import java.math.BigDecimal;

public class SaleItemRecord {

    private String id;

    private BigDecimal quantity;

    private BigDecimal price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
