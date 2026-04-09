package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.util.List;

@FunctionalInterface
public interface Function {
    SleepAnalysisResult apply(List<SleepingSession> sessions);
}
