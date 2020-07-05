package br.com.lgrapplications.southsystem.challenge.events.listener;

import br.com.lgrapplications.southsystem.challenge.events.model.DirProcessorEvent;
import br.com.lgrapplications.southsystem.challenge.service.DirectoryProcessorService;
import br.com.lgrapplications.southsystem.challenge.service.ReportService;
import br.com.lgrapplications.southsystem.challenge.service.SaleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class DirProcessorEventListener implements ApplicationListener<DirProcessorEvent> {

    final static Logger LOG = LoggerFactory.getLogger(DirProcessorEventListener.class);

    @Autowired
    private DirectoryProcessorService directoryProcessorService;

    @Autowired
    private SaleService saleService;

    @Autowired
    private ReportService reportService;

    @Override
    public void onApplicationEvent(DirProcessorEvent dirProcessorEvent) {
        LOG.info("In processor has started!");
        try {
            reportService
                    .writeReport(dirProcessorEvent.getOutputFile(),
                    saleService.buildReport(
                            directoryProcessorService.readDirectory(dirProcessorEvent.getInputFile()))
            );

        } catch(Exception ex) {
            LOG.error(String.format("Error processing folder %s", dirProcessorEvent.getInputFile().getAbsolutePath()), ex);
        } finally {
            LOG.info("In processor has finished!");
        }

    }
}
