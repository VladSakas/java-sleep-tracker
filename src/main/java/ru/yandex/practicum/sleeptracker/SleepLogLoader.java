package ru.yandex.practicum.sleeptracker;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class SleepLogLoader {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

    public List<SleepingSession> load(String fileName) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);

        return lines.stream()
                .filter(line -> !line.isBlank())
                .map(line -> line.split(";"))
                .map(this::parseLine)
                .collect(Collectors.toList());
    }

    private SleepingSession parseLine(String[] parts) {
        LocalDateTime startSleep = LocalDateTime.parse(parts[0], FORMATTER);
        LocalDateTime endSleep = LocalDateTime.parse(parts[1], FORMATTER);
        SleepQuality quality = SleepQuality.valueOf(parts[2]);

        return new SleepingSession(startSleep, endSleep, quality);
    }

}
