package br.com.lgrapplications.southsystem.challenge.inputreader.adapter.model;

import br.com.lgrapplications.southsystem.challenge.inputreader.adapter.DatRecordType;

public class ClientRecord extends DatRecord {

    private String cnpj;

    private String name;

    private String businessArea;

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBusinessArea() {
        return businessArea;
    }

    public void setBusinessArea(String businessArea) {
        this.businessArea = businessArea;
    }

    @Override
    public DatRecordType getType() {
        return DatRecordType.CLIENT;
    }
}
