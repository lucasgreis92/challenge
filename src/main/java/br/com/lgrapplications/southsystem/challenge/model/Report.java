package br.com.lgrapplications.southsystem.challenge.model;

import br.com.lgrapplications.southsystem.challenge.inputreader.adapter.model.ClientRecord;

import java.util.List;

public class Report {

    private List<Sale> sales;

    private List<Salesman> salesmen;

    private List<ClientRecord> clients;

    public Report() {
    }

    public Report(List<Sale> sales, List<Salesman> salesmen, List<ClientRecord> clients) {
        this.sales = sales;
        this.salesmen = salesmen;
        this.clients = clients;
    }

    public List<Sale> getSales() {
        return sales;
    }

    public void setSales(List<Sale> sales) {
        this.sales = sales;
    }

    public List<Salesman> getSalesmen() {
        return salesmen;
    }

    public void setSalesmen(List<Salesman> salesmen) {
        this.salesmen = salesmen;
    }

    public List<ClientRecord> getClients() {
        return clients;
    }

    public void setClients(List<ClientRecord> clients) {
        this.clients = clients;
    }
}
