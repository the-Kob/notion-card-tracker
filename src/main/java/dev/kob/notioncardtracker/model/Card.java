package dev.kob.notioncardtracker.model;

import java.awt.*;
import java.time.LocalDateTime;

public record Card(String id, String name, int number, String cardImageUrl, boolean haveIt, int numberOfAdditionalCopies) {
}
