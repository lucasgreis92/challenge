package br.com.lgrapplications.southsystem.challenge.inputreader.adapter;

public enum DatRecordType {
    SALESMAN("001"),
    CLIENT("002"),
    SALE("003"),
    REPORT("004");

    private String codigo;

    DatRecordType(String codigo) {
        this.codigo = codigo;
    }

    public static DatRecordType findByCodigo(String codigo) {
        for (DatRecordType drt : DatRecordType.values()) {
            if (drt.getCodigo().equals(codigo)) {
                return drt;
            }
        }

        throw new RuntimeException("Unknown record type!");
    }

    public String getCodigo() {
        return codigo;
    }
}
