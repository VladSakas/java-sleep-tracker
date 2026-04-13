package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

public class SleeplessNights implements Function {

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {

        if (sessions.isEmpty()) {
            return new SleepAnalysisResult("Количество бессонных ночей: ", 0L);
        }

        LocalDateTime firstSessionStart = sessions.getFirst().getStartSleep();
        LocalDateTime lastSessionEnd = sessions.getLast().getEndSleep();

        LocalDate firstNight = SleepUtils.getNightDate(firstSessionStart);
        LocalDate lastNight = SleepUtils.getNightDate(lastSessionEnd);

        long nightsWithSleep = sessions.stream()
                .filter(SleepUtils::isNightSleep)
                .map(session -> SleepUtils.getNightDate(session.getStartSleep()))
                .collect(Collectors.toSet())
                .size();

        long totalNights = Period.between(firstNight, lastNight).getDays() + 1;

        long sleeplessNights = totalNights - nightsWithSleep;

        return new SleepAnalysisResult("Количество бессонных ночей: ", sleeplessNights);
    }
}
