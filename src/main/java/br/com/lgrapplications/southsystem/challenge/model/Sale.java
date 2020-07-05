package br.com.lgrapplications.southsystem.challenge.model;

import br.com.lgrapplications.southsystem.challenge.inputreader.adapter.model.SaleItemRecord;
import br.com.lgrapplications.southsystem.challenge.inputreader.adapter.model.SalesmanRecord;

import java.math.BigDecimal;
import java.util.List;

public class Sale {

    private String id;

    private Salesman salesman;

    private List<SaleItem> items;

    private BigDecimal total;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Salesman getSalesman() {
        return salesman;
    }

    public void setSalesman(Salesman salesman) {
        this.salesman = salesman;
    }

    public List<SaleItem> getItems() {
        return items;
    }

    public void setItems(List<SaleItem> items) {
        this.items = items;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
