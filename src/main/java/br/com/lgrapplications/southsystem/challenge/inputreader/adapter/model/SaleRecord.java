package br.com.lgrapplications.southsystem.challenge.inputreader.adapter.model;

import br.com.lgrapplications.southsystem.challenge.inputreader.adapter.DatRecordType;
import java.util.List;

public class SaleRecord extends DatRecord {

    private String id;

    private List<SaleItemRecord> items;

    private String salesmanName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<SaleItemRecord> getItems() {
        return items;
    }

    public void setItems(List<SaleItemRecord> items) {
        this.items = items;
    }

    public String getSalesmanName() {
        return salesmanName;
    }

    public void setSalesmanName(String salesmanName) {
        this.salesmanName = salesmanName;
    }

    @Override
    public DatRecordType getType() {

        return DatRecordType.SALE;
    }
}
