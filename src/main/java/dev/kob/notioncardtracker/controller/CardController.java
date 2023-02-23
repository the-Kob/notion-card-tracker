package dev.kob.notioncardtracker.controller;

import dev.kob.notioncardtracker.model.Card;
import dev.kob.notioncardtracker.notion.NotionClient;
import dev.kob.notioncardtracker.notion.model.Page;
import dev.kob.notioncardtracker.service.CardsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/collections/{databaseId}/cards")
public class CardController {

    private final NotionClient client;

    public CardController(NotionClient client) {
        this.client = client;
    }

    @GetMapping
    public List<Card> findCardsFromDatabase(@PathVariable("databaseId") String databaseId) {
        List<Page> pages = client.databases.queryCards(databaseId);

        return pages.stream().map(CardsService::mapPageToCard).toList();
    }
}
