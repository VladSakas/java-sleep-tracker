package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.util.List;

public class AverageDuration implements Function {

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        long averageDuration = (long) sessions.stream()
                .mapToLong(SleepingSession::getDurationMinutes)
                .average()
                .orElse(0);

        return new SleepAnalysisResult("Средняя продолжительность сессии сна (минуты):", averageDuration);
    }
}