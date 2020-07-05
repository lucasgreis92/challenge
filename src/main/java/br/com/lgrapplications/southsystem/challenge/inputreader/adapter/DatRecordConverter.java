package br.com.lgrapplications.southsystem.challenge.inputreader.adapter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DatRecordConverter {

    DatRecordType type();

}
