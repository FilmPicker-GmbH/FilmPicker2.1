package io.swagger;

import java.io.Serial;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openapitools.jackson.nullable.JsonNullableModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.databind.Module;

import io.swagger.configuration.LocalDateConverter;
import io.swagger.configuration.LocalDateTimeConverter;
import io.swagger.entity.Film;
import io.swagger.service.FilmService;
import io.swagger.service.JsonToPojoService;

@SpringBootApplication
@ComponentScan(basePackages = { "io.swagger", "io.swagger.api", "io.swagger.configuration" })
public class Swagger2SpringBoot implements CommandLineRunner {

    private static final Logger logger = LogManager.getLogger(Swagger2SpringBoot.class);

    @Autowired
    private JsonToPojoService jtpService;

    @Autowired
    private FilmService filmService;

    @Override
    public void run(String... arg0) throws Exception {
        String jsonFilepath = "scrapper/movie_data.json";
        ArrayList<Film> listOfFilms = jtpService.readJsonFile(jsonFilepath);

        // Debug modus
        for (Film film: listOfFilms) {
            logger.info(film.toString());
        }

        filmService.addFilms((ArrayList<Film>) listOfFilms);

        logger.info("list length : ", listOfFilms.size());

        if (arg0.length > 0 && arg0[0].equals("exitcode")) {
            throw new ExitException();
        }
    }

    public static void main(String[] args) throws Exception {
        new SpringApplication(Swagger2SpringBoot.class).run(args);
    }

    @Bean
    public Module jsonNullableModule() {
        return new JsonNullableModule();
    }

    @Configuration
    static class CustomDateConfig implements WebMvcConfigurer {
        @Override
        public void addFormatters(FormatterRegistry registry) {
            registry.addConverter(new LocalDateConverter("yyyy-MM-dd"));
            registry.addConverter(new LocalDateTimeConverter("yyyy-MM-dd'T'HH:mm:ss.SSS"));
        }
    }

    static class ExitException extends RuntimeException implements ExitCodeGenerator {
        @Serial
        private static final long serialVersionUID = 1L;

        @Override
        public int getExitCode() {
            return 10;
        }

    }
}
