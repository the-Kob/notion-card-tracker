package dev.kob.notioncardtracker.service;

import dev.kob.notioncardtracker.model.Collection;
import dev.kob.notioncardtracker.notion.model.Page;

public class CollectionsService {

    public static Collection mapPageToCollection(Page page) {
        return new Collection(
                page.getId(),
                page.getProperties().get("Name").get("title").get(0).get("text").get("content").asText(),
                page.getProperties().get("Date").get("date").get("start").asText(),
                page.getProperties().get("Series").get("select").get("name").asText(),
                page.getProperties().get("Complete").get("checkbox").asBoolean(),
                page.getUrl(),
                page.getCover().get("file").get("url").asText()
        );
    }
}
