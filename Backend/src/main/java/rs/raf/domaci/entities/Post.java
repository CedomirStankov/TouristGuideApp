package rs.raf.domaci.entities;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class Post {

    private Integer id;
    @NotNull(message = "Author field is required")
    @NotEmpty(message = "Author field is required")
    private String author;

    @NotNull(message = "Title field is required")
    @NotEmpty(message = "Title field is required")
    private String title;

    @NotNull(message = "Content field is required")
    @NotEmpty(message = "Content field is required")
    private String content;

    private String date=LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

    private List<Komentar> comments = new ArrayList<>();

    public Post() {
    }

    public Post(String author, String title, String content, String date, List<Komentar> comments) {
        this();
        this.author = author;
        this.title = title;
        this.content = content;
        this.date = date;
        this.comments = comments;
    }

    public Post(Integer id, String author, String title, String content, String date, List<Komentar> comments) {
        this(author, title, content,date,comments);
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Komentar> getComments() {
        return comments;
    }

    public void setComments(List<Komentar> comments) {
        this.comments = comments;
    }

    public void addComment(Komentar comment) {
        this.comments.add(comment);
    }
}
