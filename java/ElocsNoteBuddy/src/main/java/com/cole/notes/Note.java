package com.cole.notes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Note {
    String title;
    String content;
    private LocalDateTime created;
    private LocalDateTime modified;
    private List<String> tags;

    public Note(String title, String content) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }

    this.title = title;
    this.content = content;
    this.created = LocalDateTime.now();
    this.modified = LocalDateTime.now();
    this.tags = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public LocalDateTime getModified() {
        return modified;
    }

    public List<String> getTags() {
        return tags;
    }

    public void addTag(String tag) {
        if (!tags.contains(tag)) {
            tags.add(tag);
        }
    }
}
