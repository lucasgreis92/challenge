package br.com.lgrapplications.southsystem.challenge.inputreader.converters;

import br.com.lgrapplications.southsystem.challenge.inputreader.adapter.DatRecordConverter;
import br.com.lgrapplications.southsystem.challenge.inputreader.adapter.DatRecordConverterInterface;
import br.com.lgrapplications.southsystem.challenge.inputreader.adapter.DatRecordType;
import br.com.lgrapplications.southsystem.challenge.inputreader.adapter.model.SalesmanRecord;
import br.com.lgrapplications.southsystem.challenge.service.FileUtilsService;

import java.math.BigDecimal;
import java.util.Arrays;

@DatRecordConverter(type = DatRecordType.SALESMAN)
public class SalesmanConverter implements DatRecordConverterInterface<SalesmanRecord> {

    @Override
    public SalesmanRecord convert(String record) {
        try {
            if (record == null || record.isEmpty()) {
                throw new RuntimeException("Invalid record");
            }
            String[] values = record.split(FileUtilsService.findRecordSeparator());
            if (values.length < FileUtilsService.RECORD_LENGTH) {
                StringBuilder vals = new StringBuilder();
                Arrays.asList(values).forEach(val -> {vals.append(val);vals.append(";");});
                throw new RuntimeException(String.format("Invalid record size %s values %s", values.length, vals));
            }
            SalesmanRecord salesmanRecord = new SalesmanRecord();
            salesmanRecord.setCpf(values[0]);
            salesmanRecord.setName(values[1]);
            String salary = values[2];
            salesmanRecord.setSalary(BigDecimal.valueOf(Double.valueOf(salary)));
            return salesmanRecord;

        } catch (Exception ex) {
            throw new RuntimeException(String.format("Error loading salesman %s", record == null ? "" : record), ex);
        }
    }
}
