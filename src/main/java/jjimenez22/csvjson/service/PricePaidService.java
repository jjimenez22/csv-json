package jjimenez22.csvjson.service;

import jjimenez22.csvjson.model.PricePaid;
import jjimenez22.csvjson.repository.PricePaidRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Profile("!test")
public class PricePaidService implements ApplicationListener<ContextRefreshedEvent> {

    private static final String FILE_URL = "http://prod.publicdata.landregistry.gov.uk.s3-website-eu-west-1.amazonaws.com/pp-monthly-update-new-version.csv";

    private final Logger logger = LoggerFactory.getLogger(PricePaidService.class);

    private final PricePaidRepository repository;

    public PricePaidService(PricePaidRepository repository) {
        this.repository = repository;
    }

    private List<List<String>> _read(File dataFile) throws IOException {
        logger.info("Reading data ........");
        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(dataFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                List<String> values = Stream.of(line.split(","))
                        .map(s -> s.length() > 2 ? s.substring(1, s.length()-1) : "") // remove "
                        .collect(Collectors.toList());
                records.add(values);
            }
        }
        return records;
    }

    private List<PricePaid> buildEntities(List<List<String>> records) {
        logger.info("Building entities ........");
        return records.stream().map(PricePaid::new).collect(Collectors.toList());
    }

    public void init() throws Exception {
        File dataFile = new File("./data.csv");

        if (!dataFile.exists()) {
            logger.info("Downloading data ........");
            InputStream in = new URL(FILE_URL).openStream();
            Files.copy(in, dataFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }

        repository.deleteAll();
        repository.saveAll(buildEntities(_read(dataFile)));
   }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        try {
            init();
        } catch (Exception e) {
            logger.error("Failed to read and process data:", e);
        }
    }
}
