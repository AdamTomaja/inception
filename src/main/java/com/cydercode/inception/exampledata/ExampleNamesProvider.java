package com.cydercode.inception.exampledata;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import static com.google.common.io.Resources.getResource;

@Component
public class ExampleNamesProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExampleNamesProvider.class);
    private Random random = new Random();
    private String[] files = {"female-names.txt", "male-names.txt"};
    private Map<String, List<String>> cache = new HashMap<>();

    @PostConstruct
    public void loadData() throws IOException {
        int count = 0;
        for (String file : files) {
            List<String> names = new ArrayList<>();
            String filepath = "example-data" + File.separator + file;
            LOGGER.info("Loading example data from: classpath:{}", filepath);
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(getResource(filepath).openStream()))) {
                String line = null;
                while ((line = reader.readLine()) != null) {
                    names.add(fixCase(line.split(" ")[0]));
                    count++;
                }
            }

            cache.put(file, names);
        }
        LOGGER.info("{} names loaded", count);
    }

    private String fixCase(String name) {
        StringBuilder sb = new StringBuilder(name.toLowerCase());
        sb.deleteCharAt(0);
        sb.insert(0, Character.toUpperCase(name.charAt(0)));
        return sb.toString();
    }

    public String getRandomName() {
        List<String> list = cache.get(files[random.nextInt(files.length)]);
        return list.get(random.nextInt(list.size()));
    }
}
