package dev.kob.notioncardtracker.notion;

import dev.kob.notioncardtracker.notion.service.DatabaseService;
import org.springframework.stereotype.Component;

@Component
public class NotionClient {

    public final DatabaseService databases;

    public NotionClient(DatabaseService databases) {
        this.databases = databases;
    }
}
