package ru.yandex.practicum.sleeptracker;

import ru.yandex.practicum.sleeptracker.functions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SleepTrackerApp {

    private static final List<Function> functions = new ArrayList<>();
    private static final SleepLogLoader loader = new SleepLogLoader();

    public static void main(String[] args) {

        addAnalysisFunctions(List.of(
                new TotalSessionsCount(),
                new MinDuration(),
                new MaxDuration(),
                new AverageDuration(),
                new BadQualityCount(),
                new SleeplessNights(),
                new ChronotypeDetect()
        ));

        try {
            String filePath;
            if (args.length > 0) {
                filePath = args[0];
            } else {
                filePath = "sleep_log.txt";
            }
            List<SleepingSession> sessions = loader.load(filePath);

            functions.stream()
                    .map(f -> f.apply(sessions))
                    .forEach(System.out::println);

        } catch (IOException e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
    }

    public static void addAnalysisFunctions(List<Function> newFunctions) {
        functions.addAll(newFunctions);
    }
}