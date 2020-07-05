package br.com.lgrapplications.southsystem.challenge.events.publisher;

import br.com.lgrapplications.southsystem.challenge.events.model.DirProcessorEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class DirProcessorEventPublisher {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void publish(File inputFile, File outputFile) {
        DirProcessorEvent event = new DirProcessorEvent(this, inputFile, outputFile);
        applicationEventPublisher.publishEvent(event);
    }
}
