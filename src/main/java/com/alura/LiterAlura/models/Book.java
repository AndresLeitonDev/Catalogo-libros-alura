package com.alura.LiterAlura.models;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @Column(name = "book_id")
    private Long id;

    @Column(name = "title")
    private String title;

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name = "book_join_author", 
            joinColumns = @JoinColumn(name = "book_id"), 
            inverseJoinColumns = @JoinColumn(name = "author_id"), 
            uniqueConstraints = {@UniqueConstraint(columnNames = { "book_id", "author_id" }) })
    private List<Author> authors;

    @Column(name = "languages")
    private List<String> languages;

    @Column(name = "download_count")
    private int download_count;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public int getDownload_count() {
        return download_count;
    }

    public void setDownload_count(int download_count) {
        this.download_count = download_count;
    } 
}
