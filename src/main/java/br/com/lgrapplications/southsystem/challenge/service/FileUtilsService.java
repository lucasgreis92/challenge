package br.com.lgrapplications.southsystem.challenge.service;

import br.com.lgrapplications.southsystem.challenge.inputreader.adapter.model.DatRecord;
import br.com.lgrapplications.southsystem.challenge.inputreader.converters.DataConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Future;

@Service
public class FileUtilsService {

    private static final String DAT_EXTENSION = ".DAT";

    public static final String SALE_ITEM_SEPARATOR = "-";

    public static final String SALE_ITEMS_SEPARATOR = ",";

    public static final String CHARSET_NAME = "UTF-8"; //"ISO-8859-1";

    public static final int RECORD_LENGTH = 3;

    final static Logger LOG = LoggerFactory.getLogger(FileUtilsService.class);

    @Autowired
    private DataConverter dataConverter;

    @Async
    public Future<List<DatRecord>> readFileAsync(File file) {
        return new AsyncResult<>(dataConverter.convert(readFile(file)));
    }

    public List<String> readFile(File file) {
        try {
            LOG.info(String.format("Reading file %s ", file.getName()));

            if (!isDatFile(file)) {
                throw new RuntimeException(String.format("%s is not a .dat file!", file.getAbsolutePath()));
            }

            List<String> linhas = new ArrayList<>();
            Scanner myReader = new Scanner(file,CHARSET_NAME);
            try {
                while (myReader.hasNextLine()) {
                    linhas.add(myReader.nextLine());
                }
            } finally {
                myReader.close();
            }
            return linhas;
        } catch (FileNotFoundException ex) {
            throw new RuntimeException("File not found!", ex);
        }
    }

    public boolean isDatFile(File file) {
        return file.isFile()
                && file
                .getAbsolutePath()
                .toUpperCase()
                .endsWith(DAT_EXTENSION);
    }

    public static String findRecordSeparator(){
        try {
            return new String(new byte[]{-61, -89}, "UTF-8");//"ç";
        } catch(Exception ex) {
            throw new RuntimeException("error", ex);
        }
    }
    public void write(File outputFile, String content) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
        writer.write(new String(content.getBytes(),CHARSET_NAME));
        writer.close();
    }
}
