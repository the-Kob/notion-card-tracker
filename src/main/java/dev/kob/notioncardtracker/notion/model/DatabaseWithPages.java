package dev.kob.notioncardtracker.notion.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class DatabaseWithPages {
    private String object;
    @JsonProperty("results")
    private List<Page> pages = new ArrayList<>();
    @JsonProperty("next_cursor")
    private String nextCursor;
    @JsonProperty("has_more")
    private boolean hasMore;

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public List<Page> getPages() {
        return pages;
    }

    public void setPages(List<Page> pages) {
        this.pages = pages;
    }

    public String nextCursor() {
        return nextCursor;
    }

    public void setNextCursor(String nextCursor) {
        this.nextCursor = nextCursor;
    }

    public boolean hasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    @Override
    public String toString() {
        return "Database{" +
                "object='" + object + '\'' +
                ", pages=" + pages +
                ", nextCursor=" + nextCursor +
                ", hasMore=" + hasMore +
                '}';
    }
}
