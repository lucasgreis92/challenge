package br.com.lgrapplications.southsystem.challenge.events.model;

import org.springframework.context.ApplicationEvent;

import java.io.File;

public class DirProcessorEvent extends ApplicationEvent {

    private File inputFile;
    private File outputFile;

    public DirProcessorEvent(Object source, File inputFile, File outputFile) {
        super(source);
        this.inputFile = inputFile;
        this.outputFile = outputFile;
    }

    public File getInputFile() {
        return inputFile;
    }

    public File getOutputFile() {
        return outputFile;
    }
}
