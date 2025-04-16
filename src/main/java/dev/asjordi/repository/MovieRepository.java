package dev.asjordi.repository;

import dev.asjordi.mapper.DataMapper;
import dev.asjordi.model.Movie;
import dev.asjordi.request.Endpoint;
import dev.asjordi.request.RequestManager;

import java.util.List;

public class MovieRepository {

    private DataMapper mapper;

    public MovieRepository() {
        this.mapper = new DataMapper();
    }

    public List<Movie> getMovies(Endpoint e) {
        var data = mapper.mapDataToObject(new RequestManager(e).sendRequest());
        return data.getResults();
    }

}
