package dev.asjordi;

import dev.asjordi.logging.LoggerConfig;
import dev.asjordi.model.Movie;
import dev.asjordi.repository.MovieRepository;
import dev.asjordi.request.Endpoint;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CommandLine {

    private static final Logger LOGGER = LoggerConfig.getLogger();

    public  static void processArguments(String ... args) {
        if (args.length == 0) {
            System.out.println("No arguments provided. Use --type with one of the following options: playing, popular, top, upcoming.");
            System.out.println("For help, use the --h command.");
            LOGGER.log(Level.INFO, () -> "No arguments provided. Exiting.");
            return;
        }

        for (int i = 0; i < args.length; i++) {
            if ("--h".equals(args[i])) {
                displayHelp();
                LOGGER.log(Level.INFO, () -> "Displaying help information.");
                return;
            }

            if ("--type".equals(args[i]) && i + 1 < args.length) {
                String type = args[i + 1];
                switch (type) {
                    case "playing" -> {
                        System.out.println("\nMovies that are currently in theatres.\n");
                        var movies = new MovieRepository().getMovies(Endpoint.PLAYING);
                        movies.sort(Comparator.comparing(Movie::getTitle));
                        movies.forEach(System.out::println);
                        LOGGER.log(Level.INFO, () -> "Displaying movies that are currently in theatres.");
                    }
                    case "popular" -> {
                        System.out.println("Movies ordered by popularity.\n");
                        var movies = new MovieRepository().getMovies(Endpoint.POPULAR);
                        movies.sort(Comparator.comparing(Movie::getPopularity).reversed());
                        movies.forEach(System.out::println);
                        LOGGER.log(Level.INFO, () -> "Displaying movies ordered by popularity.");
                    }
                    case "top" -> {
                        System.out.println("\nMovies ordered by ratings.\n");
                        var movies = new MovieRepository().getMovies(Endpoint.TOP);
                        movies.sort(Comparator.comparing(Movie::getVoteAverage).reversed());
                        movies.forEach(System.out::println);
                        LOGGER.log(Level.INFO, () -> "Displaying movies ordered by ratings.");
                    }
                    case "upcoming" -> {
                        System.out.println("\nMovies that are being released soon.\n");
                        var movies = new MovieRepository().getMovies(Endpoint.UPCOMING);
                        LocalDate today = LocalDate.now();

                        var upcomingMovies = movies.stream()
                                .filter(movie -> movie.getReleaseDate().getMonth() == today.getMonth() &&
                                        movie.getReleaseDate().getYear() == today.getYear())
                                .sorted(Comparator.comparing(Movie::getReleaseDate))
                                .toList();

                        if (upcomingMovies.isEmpty()) {
                            System.out.println("No upcoming movies found.");
                            LOGGER.log(Level.INFO, () -> "No upcoming movies found.");
                        }
                        else {
                            upcomingMovies.forEach(System.out::println);
                            LOGGER.log(Level.INFO, () -> "Displaying upcoming movies.");
                        }
                    }
                    default -> {
                        System.out.println("Invalid type: " + type + ".");
                        displayHelp();
                        LOGGER.log(Level.INFO, () -> "Invalid type provided: " + type + ". Displaying help information.");
                    }
                }
                i++;
            }
        }
    }

    private static void displayHelp() {
        System.out.println("TMDB CLI Tool");
        System.out.println("Usage:");
        System.out.println("  --type <option>   Specify the type of movies to process. Options: playing, popular, top, upcoming, current-month.");
        System.out.println("  --h               Display this help message.");
    }

}
