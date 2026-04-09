package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class SleepUtils {

    private static final LocalTime NIGHT_START = LocalTime.of(0, 0);
    private static final LocalTime NIGHT_END = LocalTime.of(6, 0);
    private static final LocalTime DAY_START = LocalTime.of(12, 0);

    public static boolean isNightSleep(SleepingSession session) {
        LocalDateTime start = session.getStartSleep();
        LocalDateTime end = session.getEndSleep();

        LocalDate date = start.toLocalDate();

        LocalDateTime nightStart1 = date.atTime(NIGHT_START);
        LocalDateTime nightEnd1 = date.atTime(NIGHT_END);

        LocalDateTime nightStart2 = date.plusDays(1).atTime(NIGHT_START);
        LocalDateTime nightEnd2 = date.plusDays(1).atTime(NIGHT_END);

        return (start.isBefore(nightEnd1) && end.isAfter(nightStart1)) ||
                (start.isBefore(nightEnd2) && end.isAfter(nightStart2));
    }

    public static LocalDate getNightDate(LocalDateTime dateTime) {
        if (dateTime.toLocalTime().isBefore(DAY_START)) {
            return dateTime.toLocalDate().minusDays(1);
        } else {
            return dateTime.toLocalDate();
        }
    }
}