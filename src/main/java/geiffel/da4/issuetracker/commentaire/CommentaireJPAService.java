package geiffel.da4.issuetracker.commentaire;

import geiffel.da4.issuetracker.exceptions.ResourceAlreadyExistsException;
import geiffel.da4.issuetracker.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
@Service
@Qualifier("jpa")
@Primary
public class CommentaireJPAService implements CommentaireService{

    private static final String  COMMENTAIRE = "Commentaire";

    private final CommentaireRepository commentaireRepository;
    @Autowired
    public CommentaireJPAService(CommentaireRepository commentaireRepository){
        this.commentaireRepository = commentaireRepository;
    }


    @Override
    public List<Commentaire> getAll() {
        return commentaireRepository.findAll();
    }

    @Override
    public Commentaire getById(Long id) {
        Optional<Commentaire> commentaire = commentaireRepository.findById(id);
        if (commentaire.isPresent()) {
            return commentaire.get();
        } else {
            throw new ResourceNotFoundException(COMMENTAIRE, id);
        }
    }

    @Override
    public List<Commentaire> getAllByAuthorId(Long id) {
        return getAll().stream()
                .filter(commentaire -> Objects.equals(commentaire.getAuthorId(), id))
                .toList();
    }

    @Override
    public List<Commentaire> getAllByIssueCode(Long code) {
        return getAll().stream()
                .filter(commentaire -> Objects.equals(commentaire.getIssueCode(), code))
                .toList();
    }

    @Override
    public Commentaire create(Commentaire commentaire6) {
        Long id = commentaire6.getId();
        if(commentaireRepository.existsById(id)){
            throw new ResourceAlreadyExistsException( COMMENTAIRE , id);
        }else {
            commentaireRepository.save(commentaire6);
        }
        return commentaire6;
    }

    @Override
    public void update(Long id, Commentaire toUpdate1) {
        if(commentaireRepository.existsById(id)){
            commentaireRepository.save(toUpdate1);
        }else {
            throw new ResourceNotFoundException(COMMENTAIRE , id);
        }
    }

    @Override
    public void delete(Long id) {
        if(commentaireRepository.existsById(id)){
            commentaireRepository.deleteById(id);
        }else {
            throw new ResourceNotFoundException(COMMENTAIRE, id);
        }
    }
}
