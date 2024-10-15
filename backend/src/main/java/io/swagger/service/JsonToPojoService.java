package io.swagger.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.tomcat.util.http.fileupload.impl.IOFileUploadException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.entity.Film;
import io.swagger.model.ScrapedFilm;

@Service
public class JsonToPojoService {
    public ArrayList<Film>readJsonFile(String filepath) throws StreamReadException, DatabindException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayList<ScrapedFilm> listOfFilms = new ArrayList<ScrapedFilm>();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("movie_data.json");
        
        if (inputStream == null) {
            throw new FileNotFoundException("File not found in resources");
        }

        try {
            listOfFilms = objectMapper.readValue(inputStream, new TypeReference<ArrayList<ScrapedFilm>>(){});
        } catch (IOFileUploadException e) {
            e.printStackTrace();
        }

        return new ArrayList<Film>(listOfFilms.stream().map(scrapedFilm -> {
            String runningTime = scrapedFilm.getRunningTime().replaceAll("\\D+", "");
            scrapedFilm.setRunningTime(runningTime);
            return new Film(scrapedFilm);}).toList());
    }

    public static int convertTimeToMinute(String timeString) {
        String[] parts = timeString.split(" ");
        return Integer.parseInt(parts[0]);
    }
}
