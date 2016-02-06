package it.tony.services;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import it.tony.domain.CSVElement;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import org.mockito.internal.util.StringJoiner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Service;


import java.io.BufferedWriter;
import java.io.File;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by casinan on 05/02/2016.
 */
@Service
@PropertySource("classpath:goeuro.properties")
public class ExportServiceImpl implements ExportService {
    private static final Logger logger =Logger.getLogger(ExportServiceImpl.class);

    @Value("${locationUri}")
    public String locationUri;

    @Value("${csvFilePath}")
    public String csvFilePath;

    public ExportServiceImpl(){};

    public void invoke(String cityName) throws Exception{
        logger.info("reading json data from url "+locationUri+cityName);
        String json = IOUtils.toString(new URL(locationUri+cityName).openStream());
        JsonParser parser = new JsonParser();
        logger.info("parsing json");
        JsonElement jsonElement= parser.parse(json);
        List<CSVElement> elements = buildList(jsonElement);
        logger.info("creating csv string");

        String csv = StringUtils.join(new String[]{"_id", "name", "type", "latitude", "logitude"}, ",").
                concat("\n");
        for(CSVElement element : elements){
            csv = csv.concat(element.toString()).concat("\n");
        }
        // delete file if exists
        File oldExport = new File(csvFilePath);
        if (oldExport.exists()) {
            oldExport.delete();
        }
        Charset charset = Charset.forName("UTF-8");
        logger.info("export data to file "+csvFilePath);
        logger.info("setting charset UTF-8 for file "+csvFilePath);
        PrintWriter writer = new PrintWriter(csvFilePath, "UTF-8");
        writer.write(csv, 0, csv.length());
        writer.close();
    }

    private List<CSVElement> buildList(JsonElement element){
        CSVElement csvElement;
        List<CSVElement> elements = new ArrayList<CSVElement>();
        if(element.isJsonArray()){
            for(JsonElement subElement : element.getAsJsonArray()){
                if(subElement.isJsonObject()){
                    csvElement = new CSVElement();
                    JsonObject object = (JsonObject) subElement;
                    if(object.has("_id")){
                        csvElement.setId(object.get("_id").getAsLong());
                    }
                    if(object.has("name")){
                        csvElement.setName(object.get("name").getAsString());
                    }
                    if(object.has("type")){
                        csvElement.setType(object.get("type").getAsString());
                    }
                    if(object.has("geo_position") && object.get("geo_position").isJsonObject() ){
                        JsonObject geo = object.getAsJsonObject("geo_position");
                        if(geo.has("latitude") && geo.has("longitude")){
                            csvElement.setLatitude(geo.get("latitude").getAsDouble());
                            csvElement.setLongitude(geo.get("longitude").getAsDouble());
                            elements.add(csvElement);
                        }
                    }
                }
            }
        }
        return elements;
    }
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}

