package geiffel.da4.issuetracker.commentaire;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@Qualifier("jpa")
public interface CommentaireRepository extends JpaRepository<Commentaire, Long> {
}
