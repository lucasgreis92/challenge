package br.com.lgrapplications.southsystem.challenge.inputreader.adapter.model;

import br.com.lgrapplications.southsystem.challenge.inputreader.adapter.DatRecordType;

import java.math.BigDecimal;

public class SalesmanRecord  extends DatRecord {

    private String cpf;

    private String name;

    private BigDecimal salary;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    @Override
    public DatRecordType getType() {
        return DatRecordType.SALESMAN;
    }
}
