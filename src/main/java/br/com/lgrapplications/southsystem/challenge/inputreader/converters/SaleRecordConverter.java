package br.com.lgrapplications.southsystem.challenge.inputreader.converters;

import br.com.lgrapplications.southsystem.challenge.inputreader.adapter.DatRecordConverter;
import br.com.lgrapplications.southsystem.challenge.inputreader.adapter.DatRecordConverterInterface;
import br.com.lgrapplications.southsystem.challenge.inputreader.adapter.DatRecordType;
import br.com.lgrapplications.southsystem.challenge.inputreader.adapter.model.SaleItemRecord;
import br.com.lgrapplications.southsystem.challenge.inputreader.adapter.model.SaleRecord;
import br.com.lgrapplications.southsystem.challenge.service.FileUtilsService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@DatRecordConverter(type = DatRecordType.SALE)
public class SaleRecordConverter implements DatRecordConverterInterface<SaleRecord> {

    @Override
    public SaleRecord convert(String record) {
        try {
            if (record == null || record.isEmpty()) {
                throw new RuntimeException("Invalid record");
            }

            String[] values = record.split(FileUtilsService.findRecordSeparator());
            if (values.length < FileUtilsService.RECORD_LENGTH) {
                throw new RuntimeException("Invalid record");
            }
            SaleRecord saleRecord = new SaleRecord();
            saleRecord.setId(values[0]);
            saleRecord.setItems(converItems(values[1]));
            saleRecord.setSalesmanName(values[2]);

            if (saleRecord.getItems().isEmpty()) {
                throw new RuntimeException("Invalid sale, no item present!");
            }

            return saleRecord;
        } catch (Exception ex) {
            throw new RuntimeException(String.format("Error loading sale %s", record == null ? "" : record), ex);
        }
    }

    private List<SaleItemRecord> converItems(String record) {

        return findItens(record)
                .stream().map(item -> convertItem(item))
                .collect(Collectors.toList());

    }

    private SaleItemRecord convertItem(String item) {
        try {
            if (item == null || item.isEmpty()) {
                throw new RuntimeException("Invalid item");
            }
            String[] itemArr = item.split(FileUtilsService.SALE_ITEM_SEPARATOR);
            if (itemArr.length < FileUtilsService.RECORD_LENGTH) {
                throw new RuntimeException("Invalid item");
            }
            SaleItemRecord saleItem = new SaleItemRecord();
            saleItem.setId(itemArr[0]);
            saleItem.setQuantity(BigDecimal.valueOf(Double.valueOf(itemArr[1])));
            saleItem.setPrice(BigDecimal.valueOf(Double.valueOf(itemArr[2])));
            return saleItem;
        } catch (Exception ex) {
            throw new RuntimeException(String.format("Error loading sale item %s", item == null ? "" : item), ex);
        }
    }

    private List<String> findItens(String record) {
        String val = record.substring(1,record.length()-1);
        return Arrays.asList(val.split(FileUtilsService.SALE_ITEMS_SEPARATOR));
    }

}
