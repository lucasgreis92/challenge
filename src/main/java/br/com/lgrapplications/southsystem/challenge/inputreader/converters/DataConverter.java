package br.com.lgrapplications.southsystem.challenge.inputreader.converters;

import br.com.lgrapplications.southsystem.challenge.inputreader.adapter.DatRecordType;
import br.com.lgrapplications.southsystem.challenge.inputreader.adapter.model.DatRecord;
import br.com.lgrapplications.southsystem.challenge.inputreader.factory.ConverterFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DataConverter {


    public List<DatRecord> convert(List<String> records) {
        return records.stream()
                .map(record ->
                        ConverterFactory.build(findDatRecordType(record))
                                .convert(record.substring(4).trim()))
                .collect(Collectors.toList());
    }

    public DatRecordType findDatRecordType(String record) {
        return DatRecordType.findByCodigo(record.substring(0,3));
    }

}
