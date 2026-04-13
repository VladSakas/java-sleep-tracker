package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.Chronotype;
import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.LocalTime;
import java.util.List;

public class ChronotypeDetect implements Function {

    private static final LocalTime OWL_START = LocalTime.of(23, 0);
    private static final LocalTime OWL_END = LocalTime.of(9, 0);
    private static final LocalTime LARK_START = LocalTime.of(22, 0);
    private static final LocalTime LARK_END = LocalTime.of(7, 0);

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {

        long owlCount = sessions.stream()
                .filter(SleepUtils::isNightSleep)
                .filter(this::isOwl)
                .count();

        long larkCount = sessions.stream()
                .filter(SleepUtils::isNightSleep)
                .filter(this::isLark)
                .count();

        String result;
        if (owlCount > larkCount) {
            result = Chronotype.OWL.getName();
        } else if (larkCount > owlCount) {
            result = Chronotype.LARK.getName();
        } else {
            result = Chronotype.DOVE.getName();
        }

        return new SleepAnalysisResult("Хронотип пользователя: ", result);
    }

    private boolean isOwl(SleepingSession session) {
        LocalTime start = session.getStartSleep().toLocalTime();
        LocalTime end = session.getEndSleep().toLocalTime();
        return start.isAfter(OWL_START) && end.isAfter(OWL_END);
    }

    private boolean isLark(SleepingSession session) {
        LocalTime start = session.getStartSleep().toLocalTime();
        LocalTime end = session.getEndSleep().toLocalTime();
        return start.isBefore(LARK_START) && end.isBefore(LARK_END);
    }

}