package br.com.lgrapplications.southsystem.challenge.inputreader.adapter;

import br.com.lgrapplications.southsystem.challenge.inputreader.adapter.model.DatRecord;

public interface DatRecordConverterInterface<T extends DatRecord> {

    T convert(String record);
}
