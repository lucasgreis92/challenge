package br.com.lgrapplications.southsystem.challenge.service;

import br.com.lgrapplications.southsystem.challenge.inputreader.adapter.DatRecordType;
import br.com.lgrapplications.southsystem.challenge.inputreader.factory.ConverterFactory;
import br.com.lgrapplications.southsystem.challenge.inputreader.adapter.model.DatRecord;
import org.springframework.stereotype.Service;

@Service
public class ConverterService {

    public DatRecord convert(String record, DatRecordType type) {
        return ConverterFactory
                .build(type)
                .convert(record);
    }
}
