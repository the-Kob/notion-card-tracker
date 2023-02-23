package dev.kob.notioncardtracker.service;

import dev.kob.notioncardtracker.model.Card;
import dev.kob.notioncardtracker.notion.model.Page;

public class CardsService {

    public static Card mapPageToCard(Page page) {
        return new Card(
                page.getId(),
                page.getProperties().get("Name").get("title").get(0).get("text").get("content").asText(),
                page.getProperties().get("Number").get("number").asInt(),
                page.getProperties().get("Card").get("files").get(0).get("file").get("url").asText(),
                page.getProperties().get("Do I have it?").get("checkbox").asBoolean(),
                page.getProperties().get("No. of additional copies").get("number").asInt()
        );
    }
}
