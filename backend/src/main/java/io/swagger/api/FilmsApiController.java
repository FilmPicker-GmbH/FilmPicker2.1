package io.swagger.api;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.model.Film;
import io.swagger.model.InlineResponse200;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@jakarta.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-08-31T15:00:15.128234763Z[GMT]")
@RestController
@RequestMapping("/v1")
//@CrossOrigin(origins = "http://localhost:4200") // Allow CORS for this specific controller
public class FilmsApiController implements FilmsApi {

    private static final Logger log = LoggerFactory.getLogger(FilmsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    public FilmsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Film> addFilm(
            @Parameter(in = ParameterIn.PATH, description = "The film ID", required = true, schema = @Schema(description = "Schema description for add film" )) @PathVariable String id
            , @Parameter(in = ParameterIn.DEFAULT, description = "The film details", required = true, schema = @Schema(description = "Schema description for add film" )) @Valid @RequestBody Film body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<Film>(objectMapper.readValue("{\n  \"moodType\" : \"LIGHT, WHOLESOME\",\n  \"length\" : 142,\n  \"id\" : \"42\",\n  \"title\" : \"The Shawshank Redemption\"\n}", Film.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<Film>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Film>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> deleteFilm(@Parameter(in = ParameterIn.PATH, description = "The film ID", required = true, schema = @Schema(description = "Schema description for delete film" )) @PathVariable String id) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Film> getFilm(@Parameter(in = ParameterIn.PATH, description = "The film ID", required = true, schema = @Schema(description = "Schema description for get film" )) @PathVariable String id) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<Film>(objectMapper.readValue("{\n  \"moodType\" : \"LIGHT, WHOLESOME\",\n  \"length\" : 142,\n  \"id\" : \"42\",\n  \"title\" : \"The Shawshank Redemption\"\n}", Film.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<Film>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Film>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<InlineResponse200> getFilms(@Parameter(in = ParameterIn.QUERY, description = "The page offset", schema = @Schema(defaultValue = "0")) @Valid @RequestParam(required = false, defaultValue = "0") Integer offset
            , @Parameter(in = ParameterIn.QUERY, description = "The page limit", schema = @Schema(defaultValue = "5")) @Valid @RequestParam(required = false, defaultValue = "5") Integer limit) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<InlineResponse200>(objectMapper.readValue("\"\"", InlineResponse200.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<InlineResponse200>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<InlineResponse200>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Film> updateFilm(@Parameter(in = ParameterIn.PATH, description = "The film ID", required = true, schema = @Schema(description = "Schema description for update film" )) @PathVariable String id
            , @Parameter(in = ParameterIn.DEFAULT, description = "The film details", required = true, schema = @Schema(description = "Schema description for update film")) @Valid @RequestBody Film body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<Film>(objectMapper.readValue("{\n  \"moodType\" : \"LIGHT, WHOLESOME\",\n  \"length\" : 142,\n  \"id\" : \"42\",\n  \"title\" : \"The Shawshank Redemption\"\n}", Film.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<Film>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Film>(HttpStatus.NOT_IMPLEMENTED);
    }

}
