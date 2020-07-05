package br.com.lgrapplications.southsystem.challenge.inputreader.factory;

import br.com.lgrapplications.southsystem.challenge.inputreader.adapter.DatRecordConverter;
import br.com.lgrapplications.southsystem.challenge.inputreader.adapter.DatRecordConverterInterface;
import br.com.lgrapplications.southsystem.challenge.inputreader.adapter.DatRecordType;
import org.reflections.Reflections;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Set;

public class ConverterFactory {

    private static final String PATH_TO_CONVERTERS = "br.com.lgrapplications.southsystem.challenge";

    public static DatRecordConverterInterface build(DatRecordType type) {
        try {
            return (DatRecordConverterInterface) findConverter(type).newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar ", e);
        }
    }

    static Class<?>  findConverter(DatRecordType type) {
        Set<Class<?>> classes = new Reflections(PATH_TO_CONVERTERS)
                .getTypesAnnotatedWith(DatRecordConverter.class);
        for (Class<?> classe : classes) {
            for (Annotation annotation : classe.getAnnotations()) {
                if (annotation instanceof DatRecordConverter) {
                    if (((DatRecordConverter) annotation).type().equals(type)) {
                        if (!implementsInterface(classe, DatRecordConverterInterface.class)) {
                            throw new RuntimeException("Class does not implements DatRecordConverterInterface!");
                        }
                        return (Class) classe;
                    }
                }
            }
        }

        throw new RuntimeException("Type not implemented!");
    }

    public static boolean implementsInterface(Class clazz, Class intf) {
        assert intf.isInterface() : "Interface to check was not an interface";

        return intf.isAssignableFrom(clazz);
    }
}
