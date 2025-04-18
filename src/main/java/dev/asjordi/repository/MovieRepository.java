package dev.asjordi.repository;

import dev.asjordi.logging.LoggerConfig;
import dev.asjordi.mapper.DataMapper;
import dev.asjordi.model.Movie;
import dev.asjordi.request.Endpoint;
import dev.asjordi.request.RequestManager;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MovieRepository {

    private static final Logger LOGGER = LoggerConfig.getLogger();
    private DataMapper mapper;

    public MovieRepository() {
        this.mapper = new DataMapper();
    }

    public List<Movie> getMovies(Endpoint e) {
        var data = mapper.mapDataToObject(new RequestManager(e).sendRequest());
        LOGGER.log(Level.INFO, () -> String.format("Returning %d movies.", data.getResults().size()));
        return data.getResults();
    }

}
