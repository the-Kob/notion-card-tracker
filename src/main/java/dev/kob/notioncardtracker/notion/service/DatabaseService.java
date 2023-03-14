package dev.kob.notioncardtracker.notion.service;

import dev.kob.notioncardtracker.notion.config.NotionConfigProperties;
import dev.kob.notioncardtracker.notion.model.DatabaseWithBlocks;
import dev.kob.notioncardtracker.notion.model.DatabaseWithPages;
import dev.kob.notioncardtracker.notion.model.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.simple.JSONValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DatabaseService {

    private final Logger log = LoggerFactory.getLogger(DatabaseService.class);
    private final NotionConfigProperties notionConfigProps;
    private final RestTemplate restTemplate;

    public DatabaseService(NotionConfigProperties notionConfigProps, RestTemplate restTemplate) {
        this.notionConfigProps = notionConfigProps;
        this.restTemplate = restTemplate;
    }

    public List<Page> query(String databaseId) {
        String url = notionConfigProps.apiUrl() + "/v1/databases/" + databaseId + "/query";
        log.info("Querying database: {}", url);
        ResponseEntity<DatabaseWithPages> db = restTemplate.exchange(
                url,
                HttpMethod.POST,
                new HttpEntity<>(getDefaultHeaders()),
                DatabaseWithPages.class
        );

        List<Page> pages = db.getBody().getPages();

        /*
         If the database resorts to pagination i.e. has too many entries,
         it does not retrieve all of its pages in one go.
         We need to check if it has more entries and then retrieve the
         rest of the pages, so we can add them to the previous retrieved ones.
         */
        while(db.getBody().hasMore()) {
            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("start_cursor", db.getBody().nextCursor());

            url = notionConfigProps.apiUrl() + "/v1/databases/" + databaseId + "/query";
            log.info("Querying the rest of the database: {}", requestBody.get("start_cursor"));
            db = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    new HttpEntity<>(requestBody, getDefaultHeaders()),
                    DatabaseWithPages.class
            );

            pages.addAll(db.getBody().getPages());
        }

        // Remove archived page son
        for (Page page: pages) {
            if(page.getArchived()) {
                pages.remove(page);
            }
        }

        return pages;
    }

    public Page queryPage(String pageId) {
        String url = notionConfigProps.apiUrl() + "/v1/pages/" + pageId;
        log.info("Querying page: {}", url);

        ResponseEntity<Page> pg = restTemplate.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(getDefaultHeaders()),
                Page.class
        );

        Page page = pg.getBody();

        return page;
    }

    public List<Page> queryCards(String databaseId) {
        String url = notionConfigProps.apiUrl() + "/v1/blocks/" + databaseId + "/children";
        log.info("Querying database for cards: {}", url);

        // First we need to get the inline database's id
        ResponseEntity<DatabaseWithBlocks> db = restTemplate.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(getDefaultHeaders()),
                DatabaseWithBlocks.class
        );

        String dbId = db.getBody().getBlocks().get(0).getId();

        // Now we can query all the cards from that database
        List<Page> cards = query(dbId);

        return cards;
    }

    public String createPage(Map<String, Object> collection) {
        String url = notionConfigProps.apiUrl() + "/v1/pages";
        log.info("Creating entry in database: {}", url);

        ResponseEntity<Page> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                new HttpEntity<>(collection, getDefaultHeaders()),
                Page.class
        );

        return response.getBody().getId();
    }



    private HttpHeaders getDefaultHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Notion-Version", notionConfigProps.apiVersion());
        headers.set("Authorization", notionConfigProps.authToken());

        return headers;
    }
}
