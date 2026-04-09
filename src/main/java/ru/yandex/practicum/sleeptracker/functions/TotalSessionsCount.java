package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.util.List;

public class TotalSessionsCount implements Function {

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        int count = sessions.size();
        return new SleepAnalysisResult("Общее количество сессий сна: ", count);
    }
}