package dev.kob.notioncardtracker.notion.model;

public class Block {
    private String object;
    private String id;

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

    @Override
    public String toString() {
        return "Page{" +
                "object='" + object + '\'' +
                '}';
    }
}
