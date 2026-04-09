package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import ru.yandex.practicum.sleeptracker.functions.*;

import java.time.LocalDateTime;
import java.util.List;

public class SleepTrackerAppTest {
    private final TotalSessionsCount totalSessions = new TotalSessionsCount();
    private final MinDuration minDuration = new MinDuration();
    private final MaxDuration maxDuration = new MaxDuration();
    private final AverageDuration averageDuration = new AverageDuration();
    private final BadQualityCount badQuality = new BadQualityCount();
    private final SleeplessNights sleeplessNightsFunction = new SleeplessNights();
    private final ChronotypeDetect chronotypeDetect = new ChronotypeDetect();

    // ===================== TEST TotalSessionsCount =====================

    // Пустой список вернет 0
    @Test
    public void shouldReturnZeroWithEmptyList() {
        SleepAnalysisResult result = totalSessions.apply(List.of());

        assertEquals(0, result.getValue());
        assertEquals("Общее количество сессий сна: ", result.getDescription());
    }

    // Верно считает количество сессий в списке и корректно выводит результат
    @Test
    public void shouldReturnCorrectSessionsCount() {
        List<SleepingSession> sessions = createTestSessions();
        SleepAnalysisResult result = totalSessions.apply(sessions);

        assertEquals(3, result.getValue());
        assertEquals("Общее количество сессий сна: ", result.getDescription());
    }

    // ===================== TEST MinDuration =====================

    // Пустой список вернет 0
    @Test
    public void minDurationShouldReturnZeroForEmptyList() {
        SleepAnalysisResult result = minDuration.apply(List.of());
        assertEquals(0L, result.getValue());
    }

    // Самая короткая сессия: с 23:00 до 05:00 = 360 минут
    @Test
    public void minDurationShouldReturnCorrectValue() {
        List<SleepingSession> sessions = createTestSessions();
        SleepAnalysisResult result = minDuration.apply(sessions);

        assertEquals(360L, result.getValue());
    }

    // ===================== TEST MaxDuration =====================

    // Пустой список вернет 0
    @Test
    public void maxDurationShouldReturnZeroForEmptyList() {
        SleepAnalysisResult result = maxDuration.apply(List.of());
        assertEquals(0L, result.getValue());
    }

    // Самая длинная сессия: с 01:00 до 12:00 = 660 минут
    @Test
    public void maxDurationShouldReturnCorrectValue() {
        List<SleepingSession> sessions = createTestSessions();
        SleepAnalysisResult result = maxDuration.apply(sessions);
        assertEquals(660L, result.getValue());
    }

    // ===================== TEST AverageDuration =====================

    // Пустой список вернет 0
    @Test
    public void averageDurationShouldReturnZeroForEmptyList() {
        SleepAnalysisResult result = averageDuration.apply(List.of());
        assertEquals(0L, result.getValue());
    }

    // Среднее значение по списку (630 + 360 + 660) / 3 = 550 минут
    @Test
    public void averageDurationShouldReturnCorrectValue() {
        List<SleepingSession> sessions = createTestSessions();
        SleepAnalysisResult result = averageDuration.apply(sessions);

        assertEquals(550L, result.getValue());
    }

    // ===================== TEST BadQualityCount =====================

    // Пустой список вернёт 0
    @Test
    public void badQualityCountShouldReturnZeroForEmptyList() {
        SleepAnalysisResult result = badQuality.apply(List.of());
        assertEquals(0L, result.getValue());
    }

    // Корректно считает BAD quality
    @Test
    public void badQualityCountShouldReturnCorrectValue() {
        List<SleepingSession> sessions = createTestSessions();
        SleepAnalysisResult result = badQuality.apply(sessions);
        assertEquals(1L, result.getValue());
    }

    // ===================== TEST SleeplessNights =====================

    // Пустой список - бессонных ночей нет
    @Test
    public void sleeplessNightsShouldReturnZeroForEmptyList() {
        SleepAnalysisResult result = sleeplessNightsFunction.apply(List.of());
        assertEquals(0L, result.getValue());
        assertEquals("Количество бессонных ночей: ", result.getDescription());
    }

    // Ночной сон (с 23:00 до 07:00) - бессонных ночей нет
    @Test
    public void sleeplessNightsShouldReturnZeroForNightSleep() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2026, 4, 10, 23, 0),
                        LocalDateTime.of(2026, 4, 11, 7, 0),
                        SleepQuality.NORMAL
                )
        );
        SleepAnalysisResult result = sleeplessNightsFunction.apply(sessions);
        assertEquals(0L, result.getValue());
    }

    // Только дневной сон (с 07:00 до 11:00) - ночь бессонная
    @Test
    public void sleeplessNightsShouldReturnOneForDaySleepOnly() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2026, 4, 10, 7, 0),
                        LocalDateTime.of(2026, 4, 10, 11, 0),
                        SleepQuality.NORMAL
                )
        );

        SleepAnalysisResult result = sleeplessNightsFunction.apply(sessions);
        assertEquals(1L, result.getValue());
    }

    // Сон с 02:00 до 07:00 (утренний сон) - бессонных ночей нет
    @Test
    public void sleeplessNightsShouldReturnZeroForMorningSleep() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2026, 4, 10, 2, 0),
                        LocalDateTime.of(2026, 4, 10, 7, 0),
                        SleepQuality.NORMAL
                )
        );

        SleepAnalysisResult result = sleeplessNightsFunction.apply(sessions);
        assertEquals(0L, result.getValue());
    }

    // ===================== TEST ChronotypeDetect =====================

    // Пустой список - Голубь
    @Test
    public void chronotypeShouldReturnDoveForEmptyList() {
        SleepAnalysisResult result = chronotypeDetect.apply(List.of());
        assertEquals("Голубь", result.getValue());
    }

    // Сова
    @Test
    public void chronotypeShouldReturnOwlForOwlNight() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2026, 4, 10, 23, 30),
                        LocalDateTime.of(2026, 4, 11, 9, 30),
                        SleepQuality.NORMAL
                )
        );
        SleepAnalysisResult result = chronotypeDetect.apply(sessions);
        assertEquals("Сова", result.getValue());
    }

    // Жаворонок
    @Test
    public void chronotypeShouldReturnLarkForLarkNight() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2026, 4, 10, 21, 30),
                        LocalDateTime.of(2026, 4, 11, 6, 30),
                        SleepQuality.NORMAL
                )
        );
        SleepAnalysisResult result = chronotypeDetect.apply(sessions);
        assertEquals("Жаворонок", result.getValue());
    }

    // Дневной сон - голубь
    @Test
    public void chronotypeShouldReturnDoveForDaySleep() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2026, 4, 10, 7, 0),
                        LocalDateTime.of(2026, 4, 10, 11, 0),
                        SleepQuality.NORMAL
                )
        );
        SleepAnalysisResult result = chronotypeDetect.apply(sessions);
        assertEquals("Голубь", result.getValue());
    }

    // Вспомогательный(-е) метод(-ы)

    private List<SleepingSession> createTestSessions() {
        SleepingSession session1 = new SleepingSession(
                LocalDateTime.of(2026, 4, 10, 22, 0),
                LocalDateTime.of(2026, 4, 11, 8, 30),
                SleepQuality.NORMAL
        );
        SleepingSession session2 = new SleepingSession(
                LocalDateTime.of(2026, 4, 15, 23, 0),
                LocalDateTime.of(2026, 4, 16, 5, 0),
                SleepQuality.BAD
        );
        SleepingSession session3 = new SleepingSession(
                LocalDateTime.of(2026, 4, 20, 1, 0),
                LocalDateTime.of(2026, 4, 20, 12, 0),
                SleepQuality.GOOD
        );
        return List.of(session1, session2, session3);
    }
}