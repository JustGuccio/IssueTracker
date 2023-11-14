package geiffel.da4.issuetracker.projet;

import geiffel.da4.issuetracker.exceptions.ResourceAlreadyExistsException;
import geiffel.da4.issuetracker.exceptions.ResourceNotFoundException;
import geiffel.da4.issuetracker.user.User;

import java.util.List;

public interface ProjetService {

    List<Projet> getAll();

    Projet getById(Long id);

    Projet create(Projet newProjet) throws ResourceAlreadyExistsException;

    void update(Long id, Projet updatedProjet) throws ResourceNotFoundException;

    void delete(Long id) throws ResourceNotFoundException;
}
