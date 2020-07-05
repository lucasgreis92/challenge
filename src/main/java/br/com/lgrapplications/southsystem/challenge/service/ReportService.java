package br.com.lgrapplications.southsystem.challenge.service;

import br.com.lgrapplications.southsystem.challenge.inputreader.adapter.DatRecordType;
import br.com.lgrapplications.southsystem.challenge.model.Report;
import br.com.lgrapplications.southsystem.challenge.model.Sale;
import br.com.lgrapplications.southsystem.challenge.model.Salesman;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.File;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReportService {

    final static Logger LOG = LoggerFactory.getLogger(ReportService.class);

    @Autowired
    private FileUtilsService fileUtilsService;

    public void writeReport(File outputFile, Report report) {

        validateReport(report);

        Integer clientsQuantity = report.getClients().size();
        Integer salesmenQuantity = report.getSalesmen().size();

        Optional<Sale> expensiveSale = report.getSales().stream()
                .sorted(Comparator.comparing(Sale::getTotal).reversed()).findFirst();

        Optional<Salesman> worstSeller = report.getSalesmen().stream()
                .sorted(Comparator.comparing(Salesman::getTotalSales))
                .findFirst();

        try {
            LOG.info("wrinting report");
            fileUtilsService.write(outputFile,
                    findContent(clientsQuantity,
                            salesmenQuantity,
                            expensiveSale,
                            worstSeller));
            LOG.info(String.format("report successfully writed, file path %s", outputFile.getAbsolutePath()));
        } catch(Exception ex) {
            throw new RuntimeException("Error writing file");
        }
    }

    private void validateReport(Report report) {

        LOG.info("validating report");

        report.getSalesmen()
                .forEach( salesman -> {
                    if (1 < report.getSalesmen().stream()
                            .filter( salesman1 -> salesman.getName().equals(salesman1.getName()))
                            .collect(Collectors.toList()).size()) {

                        throw new RuntimeException(String.format("doubled salesman %s", salesman.getName()));
                    }
                });
        report.getSales()
                .forEach(sale -> {
                    if (1 < report.getSales().stream()
                            .filter(sale1 -> sale.getId().equals(sale1.getId()))
                            .collect(Collectors.toList()).size()) {
                        throw new RuntimeException(String.format("doubled sale %s", sale.getId()));
                    }
                });
    }



    private String findContent(Integer clientsQuantity,
                               Integer salesmenQuantity,
                               Optional<Sale> expensiveSale,
                               Optional<Salesman> worstSeller) {

        StringBuilder sb = new StringBuilder();
        try {
            sb.append(DatRecordType.REPORT.getCodigo());
            sb.append(FileUtilsService.findRecordSeparator());
            sb.append(clientsQuantity);
            sb.append(FileUtilsService.findRecordSeparator());
            sb.append(salesmenQuantity);
            sb.append(FileUtilsService.findRecordSeparator());
            expensiveSale.ifPresent(sale -> sb.append(sale.getId()));
            sb.append(FileUtilsService.findRecordSeparator());
            worstSeller.ifPresent(salesman -> sb.append(salesman.getName()));
        } catch (Exception ex) {
            throw new RuntimeException("Error creating output content!");
        }
        return sb.toString();
    }
}
