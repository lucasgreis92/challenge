package br.com.lgrapplications.southsystem.challenge.service;

import br.com.lgrapplications.southsystem.challenge.inputreader.adapter.DatRecordType;
import br.com.lgrapplications.southsystem.challenge.inputreader.adapter.model.*;
import br.com.lgrapplications.southsystem.challenge.model.Report;
import br.com.lgrapplications.southsystem.challenge.model.Sale;
import br.com.lgrapplications.southsystem.challenge.model.SaleItem;
import br.com.lgrapplications.southsystem.challenge.model.Salesman;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SaleService {

    final static Logger LOG = LoggerFactory.getLogger(SaleService.class);

    public Report buildReport(List<DatRecord> records) {

        LOG.info("Building report");
        List<Salesman> salesmen = convertSalesmanList(findSalesman(records));

        return new Report(
                findSales(records).stream()
                        .map(saleR -> convertSaleItem(saleR, salesmen))
                        .collect(Collectors.toList()),
                salesmen,
                findClients(records)
        );
    }

    public Sale convertSaleItem(SaleRecord saleRecord,
                                List<Salesman> salesmen ) {

        Sale sale = new Sale();
        sale.setId(saleRecord.getId());
        salesmen.stream()
                .filter(salesman -> salesman.getName().equals(saleRecord.getSalesmanName()))
                .findFirst()
                .ifPresent(salesman -> {
                    sale.setSalesman(salesman);
                    salesman.getSales().add(sale);
                });

        if (sale.getSalesman() == null) {
            throw new RuntimeException(String.format("Saleman %s not found", saleRecord.getSalesmanName()));
        }

        sale.setItems(convertSaleItemList(saleRecord.getItems()));
        sale.setTotal(sale.getItems()
                .stream()
                .map(item -> item.getTotal())
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        if (sale.getSalesman() != null) {
            sale.getSalesman()
                    .setTotalSales(
                            sale.getSalesman().getTotalSales().add(sale.getTotal()));
        }
        return sale;
    }

    public List<SaleItem> convertSaleItemList(List<SaleItemRecord> items) {
        return items.stream()
                .map(item -> convertSaleItem(item))
                .collect(Collectors.toList());
    }

    public SaleItem convertSaleItem(SaleItemRecord item) {
        SaleItem saleItem = new SaleItem();
        saleItem.setId(item.getId());
        saleItem.setPrice(item.getPrice());
        saleItem.setQuantity(item.getQuantity());
        saleItem.setTotal(item.getPrice().multiply(item.getQuantity()));
        return saleItem;
    }



    public List<Salesman> convertSalesmanList(List<SalesmanRecord> recors) {
        return recors.stream()
                .map(record -> convertSaleItem(record))
                .collect(Collectors.toList());
    }

    public Salesman convertSaleItem(SalesmanRecord record) {
        Salesman salesman = new Salesman();
        salesman.setCpf(record.getCpf());
        salesman.setName(record.getName());
        salesman.setSalary(record.getSalary());
        salesman.setSales(new ArrayList<>());
        salesman.setTotalSales(BigDecimal.ZERO);
        return salesman;
    }

    public List<ClientRecord> findClients(List<DatRecord> records) {

        return records.stream()
                .filter( record -> DatRecordType.CLIENT.equals(record.getType()))
                .map(record -> (ClientRecord) record)
                .collect(Collectors.toList());
    }

    public List<SalesmanRecord> findSalesman(List<DatRecord> records) {

        return records.stream().filter( record -> DatRecordType.SALESMAN.equals(record.getType()))
                .map(record -> (SalesmanRecord) record)
                .collect(Collectors.toList());
    }

    public List<SaleRecord> findSales(List<DatRecord> records) {

        return records.stream().filter( record -> DatRecordType.SALE.equals(record.getType()))
                .map(record -> (SaleRecord) record)
                .collect(Collectors.toList());
    }
}
