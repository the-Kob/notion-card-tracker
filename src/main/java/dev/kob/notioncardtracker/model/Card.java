package dev.kob.notioncardtracker.model;

public record Card(String id, String name, int number, String cardImageUrl, boolean haveIt, int numberOfAdditionalCopies) {
}
