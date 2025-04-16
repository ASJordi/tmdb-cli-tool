package dev.asjordi.request;

public enum Endpoint {

    PLAYING("https://api.themoviedb.org/3/movie/now_playing?language=en-US"),
    POPULAR("https://api.themoviedb.org/3/movie/popular?language=en-US"),
    TOP("https://api.themoviedb.org/3/movie/top_rated?language=en-US"),
    UPCOMING("https://api.themoviedb.org/3/movie/upcoming?language=en-US");

    private String url;

    Endpoint(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

}
