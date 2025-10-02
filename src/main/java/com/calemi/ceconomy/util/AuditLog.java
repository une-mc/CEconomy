// com/calemi/ceconomy/util/AuditLog.java
package com.calemi.ceconomy.util;

import java.io.IOException;
import java.nio.file.*;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public final class AuditLog {
    private static final Path ROOT = Paths.get("ceconomy-audit");
    private static final DateTimeFormatter TS = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    public static void write(UUID uuid, String playerName, String line) {
        try {
            Files.createDirectories(ROOT);
            Path file = ROOT.resolve(uuid.toString() + ".log");
            String ts = TS.format(Instant.now().atOffset(ZoneOffset.UTC));
            String full = String.format("%s | %s (%s) | %s%n", ts, playerName, uuid, line);
            Files.writeString(file, full, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            // last resort: surface to console
            System.out.println("[CEconomy-Audit] Failed to write audit line: " + e.getMessage());
        }
    }

    private AuditLog() {}
}
