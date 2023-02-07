package by.korzun.spotify_clone_rsoi.util;

import by.korzun.spotify_clone_rsoi.domain.TrackHistory;
import by.korzun.spotify_clone_rsoi.service.TrackHistoryService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.time.YearMonth;
import java.util.List;
import java.util.Scanner;

public class ConsoleApplicationAssembler {
    private static ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    private static Scanner commandScanner = new Scanner(System.in);

    private static List<TrackHistory> allTrackHistory;
    private static TrackHistoryService trackHistoryService;

    static {
        try {
            allTrackHistory = objectMapper.readValue(
                    ClassLoader.getSystemClassLoader().getResourceAsStream("init.json"),
                    new TypeReference<List<TrackHistory>>(){});
        } catch (Exception e) {
            System.err.println("Error caught when using ObjectMapper: " + e.getMessage());
        }
        trackHistoryService = new TrackHistoryService(allTrackHistory);
    }

    public static void runApplication() {
        int isRunning = 1;

        System.out.println("Welcome to my Spotify clone! What's interesting to you?");
        while (isRunning == 1) {
            System.out.println("1 - a playlist of top tracks of this month");
            System.out.println("2 - a playlist of top tracks of previous month");
            System.out.println("3 - a playlist generated by us ;)");
            System.out.println("4 - quit");

            switch (commandScanner.nextInt()) {
                case 1 -> showInConsoleTopTracksOfTheMonth();
                case 2 -> showInConsoleTopTracksOfPreviousMonth();
                case 4 -> System.exit(0);
            }
        }
    }

    private static void showInConsoleTopTracksOfPreviousMonth() {
        trackHistoryService.getPlaylistOfTopTracksOfTheMonth(YearMonth.now().minusMonths(1L))
                .getTracks()
                .forEach(System.out::println);
    }

    private static void showInConsoleTopTracksOfTheMonth() {
        trackHistoryService.getPlaylistOfTopTracksOfTheMonth(YearMonth.now())
                .getTracks()
                .forEach(System.out::println);
    }
}
