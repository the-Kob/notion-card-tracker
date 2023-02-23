package dev.kob.notioncardtracker.notion.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class DatabaseWithBlocks {
    private String object;
    @JsonProperty("results")
    private List<Block> blocks = new ArrayList<>();
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

    public List<Block> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<Block> blocks) {
        this.blocks = blocks;
    }

    public String getNextCursor() {
        return nextCursor;
    }

    public void setNextCursor(String nextCursor) {
        this.nextCursor = nextCursor;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    @Override
    public String toString() {
        return "DatabaseWithBlocks{" +
                "object='" + object + '\'' +
                ", blocks=" + blocks +
                ", nextCursor='" + nextCursor + '\'' +
                ", hasMore=" + hasMore +
                '}';
    }
}
