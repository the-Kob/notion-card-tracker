package dev.kob.notioncardtracker.notion.model;

import com.fasterxml.jackson.databind.JsonNode;

public class Page {

    private String object;
    private String id;
    private String url;
    private boolean archived;
    private JsonNode properties;
    private JsonNode cover;

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean getArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public JsonNode getProperties() {
        return properties;
    }

    public void setProperties(JsonNode properties) {
        this.properties = properties;
    }

    public JsonNode getCover() {
        return cover;
    }
    public void setCover(JsonNode cover) { this.cover = cover; }

    @Override
    public String toString() {
        return "Page{" +
                "object='" + object + '\'' +
                ", id='" + id + '\'' +
                ", url='" + url + '\'' +
                ", cover='" + cover + '\'' +
                ", archived='" + archived + '\'' +
                ", properties=" + properties +
                '}';
    }
}
