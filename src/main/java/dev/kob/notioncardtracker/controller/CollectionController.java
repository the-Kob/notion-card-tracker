package dev.kob.notioncardtracker.controller;

import dev.kob.notioncardtracker.model.Card;
import dev.kob.notioncardtracker.model.Collection;
import dev.kob.notioncardtracker.notion.NotionClient;
import dev.kob.notioncardtracker.notion.config.NotionConfigProperties;
import dev.kob.notioncardtracker.notion.model.Page;
import dev.kob.notioncardtracker.service.CardsService;
import dev.kob.notioncardtracker.service.CollectionsService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

@RestController
@RequestMapping("/api/collections")
public class CollectionController {
    private final NotionClient client;
    private final NotionConfigProperties notionConfigProperties;

    public CollectionController(NotionClient client, NotionConfigProperties notionConfigProperties) {
        this.client = client;
        this.notionConfigProperties = notionConfigProperties;
    }

    @GetMapping("/all")
    public List<Collection> findCollections() {
        List<Page> pages = client.databases.query(notionConfigProperties.databaseId());

        return pages.stream().map(CollectionsService::mapPageToCollection).toList();
    }

    @GetMapping("/{id}")
    public Collection findCollection(@PathVariable String id) {
        Page page = client.databases.queryPage(id);

        return CollectionsService.mapPageToCollection(page);
    }

    @PostMapping
    public ResponseEntity createCollection(@RequestBody Map<String, String> params) throws URISyntaxException {
        String collectionId = client.databases.createPage(toJsonCollection(params));

        return ResponseEntity.created(new URI("/api/collections/" + collectionId)).body(collectionId);
    }

    @PatchMapping("/{id}")
    public ResponseEntity updateOrArchiveCollection(@PathVariable String id, @RequestBody Map<String, String> params) {
        Page collection = new Page();

        // If we archive the collection,
        // then there's no need to update the other params
        if(Boolean.parseBoolean(params.get("archived"))) {
            //TODO
        } else {
            //TODO
        }

        return ResponseEntity.ok(collection);
    }

    @GetMapping("/{id}/cards/all")
    public List<Card> findCardsFromDatabase(@PathVariable String id) {
        List<Page> pages = client.databases.queryCards(id);

        return pages.stream().map(CardsService::mapPageToCard).toList();
    }

    @GetMapping("/{id}/cards/{c_id}")
    public Card findCardFromDatabase(@PathVariable String id, @PathVariable String c_id) {
        //TODO

        return null;
    }

    private Map<String, Object> toJsonCollection(Map<String, String> params) {
        Map<String, Object> jsonCollection = new LinkedHashMap<>();

        Map<String, Object> parent = new HashMap<>();
        parent.put("database_id", notionConfigProperties.databaseId());

        jsonCollection.put("parent", parent);

        Map<String, Object> properties = new HashMap<>();

        Map<String, Object> name = new HashMap<>();
        Map<String, Object> text = new HashMap<>();
        Map<String, Object> content = new HashMap<>();
        content.put("content", params.get("name"));
        text.put("text", content);
        List<Object> title = Arrays.asList(text);

        name.put("title", title);

        Map<String, Object> series = new HashMap<>();
        Map<String, Object> select = new HashMap<>();
        select.put("name", params.get("series"));
        series.put("select", select);

        Map<String, Object> complete = new HashMap<>();
        complete.put("checkbox", Boolean.parseBoolean(params.get("complete")));

        Map<String, Object> date = new HashMap<>();
        Map<String, Object> dateObj = new HashMap<>();
        dateObj.put("start", params.get("date"));
        dateObj.put("end", null);
        date.put("date", dateObj);

        properties.put("Name", name);
        properties.put("Date", date);
        properties.put("Series", series);
        properties.put("Complete", complete);

        jsonCollection.put("properties", properties);

        Map<String, Object> cover = new HashMap<>();
        Map<String, Object> external = new HashMap<>();
        external.put("url", params.get("cover"));

        cover.put("type", "external");
        cover.put("external", external);

        jsonCollection.put("cover", cover);

        return jsonCollection;
    }
}
