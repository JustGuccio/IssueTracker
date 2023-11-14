package geiffel.da4.issuetracker.commentaire;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import geiffel.da4.issuetracker.issue.Issue;
import geiffel.da4.issuetracker.issue.IssueJSONSerializer;
import geiffel.da4.issuetracker.user.User;
import geiffel.da4.issuetracker.user.UserJSONSerializer;
import jakarta.persistence.*;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Commentaire {
    @Id
    private Long id;
    @ManyToOne
    @JsonSerialize(using = UserJSONSerializer.class)
    private User author;
    @ManyToOne
    @JsonSerialize(using = IssueJSONSerializer.class)
    private Issue issue;
    private String contenu;

    public Commentaire(Long id, User author, Issue issue, String contenu) {
        this.id = id;
        this.author = author;
        this.issue = issue;
        this.contenu = contenu;
        this.author.addCommentaire(this);
        this.issue.addCommentaire(this);

    }

    public Commentaire() {
    }

    public Long getAuthorId(){
        return this.author.getId();
    }
    public Long getIssueCode() {
        return this.issue.getCode();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }


}
