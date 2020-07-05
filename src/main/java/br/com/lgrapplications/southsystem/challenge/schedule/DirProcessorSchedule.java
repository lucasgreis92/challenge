package br.com.lgrapplications.southsystem.challenge.schedule;

import br.com.lgrapplications.southsystem.challenge.events.publisher.DirProcessorEventPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Component
public class DirProcessorSchedule {

    @Autowired
    private DirProcessorEventPublisher dirProcessorEventPublisher;

    final static Logger LOG = LoggerFactory.getLogger(DirProcessorSchedule.class);

    private static final String DIR_IN = "data" + File.separator + "in";

    private static final String DIR_OUT = "data" + File.separator + "out";

    private static final String FILE_OUT = DIR_OUT +  File.separator + "challenge.done.dat";

    private static final String LOCK_FILE = "data" + File.separator + "out" +  File.separator + "key.lock";

    @PostConstruct
    public void init() {
        if (isLocked()) {
            unlock();
        }
    }

    @Scheduled(cron = "*/10 * *  * * ?")
    public void processar() {
        if (!isLocked()) {
            createDirOut();
            lock();
            dirProcessorEventPublisher.publish(getDirIn(), getFileOut());
            LOG.info("Event published!");
            unlock();
        }
    }


    public void createDirOut() {
        if (!getDirOut().exists()) {
            if (!getDirOut().mkdirs()) {
                throw new RuntimeException("Impossible to create outputdir !");
            }
        }
    }

    public File getDirOut() {
        String path = System.getProperty("user.home");
        if (!path.endsWith(File.separator)) {
            path += File.separator;
        }
        path += DIR_OUT;
        return new File(path);
    }

    public File getDirIn() {
        String path = System.getProperty("user.home");
        if (!path.endsWith(File.separator)) {
            path += File.separator;
        }
        path += DIR_IN;
        return new File(path);
    }

    public File getFileOut() {
        String path = System.getProperty("user.home");
        if (!path.endsWith(File.separator)) {
            path += File.separator;
        }
        path += FILE_OUT;
        return new File(path);
    }

    public void lock() {
        try {
            LOG.info("locking process");
            write(findKeyLock());
        } catch (Exception ex) {
            throw new RuntimeException("Impossible to lock", ex);
        }
    }

    private void write(File outputFile) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
        writer.write("KeyLock File");
        writer.close();

    }

    public void unlock() {
        LOG.info("unlocking process");
        if(!findKeyLock().delete()) {
            throw new RuntimeException("Impossible to unlock");
        }
    }

    public boolean isLocked() {
        return findKeyLock().exists();
    }

    public File findKeyLock() {
        String path = System.getProperty("user.home");
        if (!path.endsWith(File.separator)) {
            path += File.separator;
        }
        path += LOCK_FILE;
        return new File(path);
    }
}
