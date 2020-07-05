package br.com.lgrapplications.southsystem.challenge.service;


import br.com.lgrapplications.southsystem.challenge.inputreader.adapter.model.DatRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;

@Service
public class DirectoryProcessorService {

    final static Logger LOG = LoggerFactory.getLogger(DirectoryProcessorService.class);

    @Autowired
    private FileUtilsService fileUtilsService;

    public List<DatRecord> readDirectory(File dir) {

        if (!dir.exists()) {
            throw new RuntimeException(String.format("%s does not exists", dir.getAbsolutePath()));
        }

        if (!dir.isDirectory()) {
            throw new RuntimeException(String.format("%s is not a directory", dir.getAbsolutePath()));
        }

        LOG.info(String.format("Reading folder %s", dir.getAbsolutePath()));

        final List<Future<List<DatRecord>>> threads = startThreads(dir.listFiles());

        waitThreads(threads);

        final List<DatRecord> linhas = findRecords(threads);

        LOG.info(String.format("Founded %s records", linhas.size()));

        return linhas;
    }

    public List<DatRecord> findRecords(List<Future<List<DatRecord>>> threads) {
        final List<DatRecord> linhas = new ArrayList<>();
        threads.forEach( thread -> {
            try {
                linhas.addAll(thread.get());
            } catch (Exception ex) {
                throw new RuntimeException("Error reading files", ex);
            }
        });

        return linhas;
    }

    public List<Future<List<DatRecord>>> startThreads(File[] files) {
        final List<Future<List<DatRecord>>> threads = new ArrayList<>();
        Arrays.asList(files)
                .stream()
                .filter(file -> {
                    return fileUtilsService.isDatFile(file);
                })
                .forEach( file -> {
                    threads.add(fileUtilsService.readFileAsync(file));
                });

        return threads;
    }

    public void waitThreads(List<Future<List<DatRecord>>> threads) {
        while (!isDone(threads)) {
            try {
                LOG.info("Waiting threads");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                LOG.error("Error", e);
            }
        }
    }

    public boolean isDone(List<Future<List<DatRecord>>> threads) {
        return !threads.stream()
                .filter( thread -> { return !thread.isDone(); })
                .findFirst().isPresent();
    }



}
