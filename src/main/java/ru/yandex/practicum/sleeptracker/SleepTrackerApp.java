package ru.yandex.practicum.sleeptracker;

import ru.yandex.practicum.sleeptracker.functions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SleepTrackerApp {

    private static final List<Function> functions = new ArrayList<>();
    private static final SleepLogLoader loader = new SleepLogLoader();

    public static void main(String[] args) {

        addAnalysisFunction(new TotalSessionsCount());
        addAnalysisFunction(new MinDuration());
        addAnalysisFunction(new MaxDuration());
        addAnalysisFunction(new AverageDuration());
        addAnalysisFunction(new BadQualityCount());
        addAnalysisFunction(new SleeplessNights());
        addAnalysisFunction(new ChronotypeDetect());

        try {
            List<SleepingSession> sessions = loader.load("sleep_log.txt");

            functions.stream()
                    .map(f -> f.apply(sessions))
                    .forEach(System.out::println);

        } catch (IOException e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
    }

    public static void addAnalysisFunction(Function function) {
        functions.add(function);
    }
}