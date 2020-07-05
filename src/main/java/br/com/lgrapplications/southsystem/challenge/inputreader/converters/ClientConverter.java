package br.com.lgrapplications.southsystem.challenge.inputreader.converters;

import br.com.lgrapplications.southsystem.challenge.inputreader.adapter.DatRecordConverter;
import br.com.lgrapplications.southsystem.challenge.inputreader.adapter.DatRecordConverterInterface;
import br.com.lgrapplications.southsystem.challenge.inputreader.adapter.DatRecordType;
import br.com.lgrapplications.southsystem.challenge.inputreader.adapter.model.ClientRecord;
import br.com.lgrapplications.southsystem.challenge.service.FileUtilsService;

@DatRecordConverter(type = DatRecordType.CLIENT)
public class ClientConverter implements DatRecordConverterInterface<ClientRecord> {

    @Override
    public ClientRecord convert(String record) {
        try {
            if (record == null || record.isEmpty()) {
                throw new RuntimeException("Invalid record");
            }
            String[] values = record.split(FileUtilsService.findRecordSeparator());
            if (values.length < FileUtilsService.RECORD_LENGTH) {
                throw new RuntimeException("Invalid record");
            }
            ClientRecord clientRecord = new ClientRecord();
            clientRecord.setCnpj(values[0]);
            clientRecord.setName(values[1]);
            clientRecord.setBusinessArea(values[2]);
            return clientRecord;

        } catch (Exception ex) {
            throw new RuntimeException(String.format("Error loading client %s", record == null ? "" : record), ex);
        }
    }
}
