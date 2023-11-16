package geiffel.da4.issuetracker;

import geiffel.da4.issuetracker.commentaire.Commentaire;
import geiffel.da4.issuetracker.commentaire.CommentaireRepository;
import geiffel.da4.issuetracker.issue.Issue;
import geiffel.da4.issuetracker.issue.IssueRepository;
import geiffel.da4.issuetracker.user.Fonction;
import geiffel.da4.issuetracker.user.User;
import geiffel.da4.issuetracker.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class IssueTrackerStudentsApplication {

    private final UserRepository userRepository;
    private final IssueRepository issueRepository;

    private final CommentaireRepository commentaireRepository;
    @Autowired
    public IssueTrackerStudentsApplication(UserRepository userRepository, IssueRepository issueRepository, CommentaireRepository commentaireRepository){
        this.userRepository = userRepository;
        this.issueRepository = issueRepository;
        this.commentaireRepository = commentaireRepository;
    }



    public static void main(String[] args) {
        SpringApplication.run(IssueTrackerStudentsApplication.class, args);
    }

    @Bean
    public CommandLineRunner setUpBDD() {
        return args -> {

            List<User> users = new ArrayList<>();
            users.add(new User(1L, "Machin", Fonction.USER));
            users.add(new User(2L, "Chose", Fonction.USER));
            users.add(new User(3L, "Truc", Fonction.DEVELOPPER));
            userRepository.saveAll(users);
            List<Issue> issues = new ArrayList<>();

            issues.add(new Issue(1L , "Title" , "Content" , userRepository.getReferenceById(1L), Timestamp.valueOf("2023-09-27 00:00:00"), Timestamp.valueOf("2023-09-28 00:00:00")));
            issueRepository.saveAll(issues);

            List<Commentaire> commentaires = new ArrayList<>();
            commentaires.add(new Commentaire(1L , userRepository.getReferenceById(1L), issueRepository.getReferenceById(1L) , "Contenu"));
            commentaireRepository.saveAll(commentaires);
        };
    }

}
