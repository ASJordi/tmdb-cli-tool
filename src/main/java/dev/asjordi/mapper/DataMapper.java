package dev.asjordi.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dev.asjordi.model.ResponseBody;

import java.net.http.HttpResponse;
import java.util.Optional;

public class DataMapper {

    private final ObjectMapper mapper;

    public DataMapper() {
        this.mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public ResponseBody mapDataToObject(Optional<HttpResponse<String>> oResponse) {
        ResponseBody responseBody = null;

        try {
            if (oResponse.isPresent() && oResponse.get().statusCode() == 200) {
                responseBody = mapper.readValue(oResponse.get().body(), ResponseBody.class);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return responseBody;
    }

}
