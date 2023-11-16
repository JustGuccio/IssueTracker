package geiffel.da4.issuetracker.projet;

import geiffel.da4.issuetracker.exceptions.ResourceAlreadyExistsException;
import geiffel.da4.issuetracker.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProjetServiceTest {


    private ProjetService projetService;

    private List<Projet>  projets;


    @BeforeEach
    void setUp() {
        projets = new ArrayList<>(){{
            add(new Projet(1L , "Projet 1"));
            add(new Projet(2L , "Projet 2"));
            add(new Projet(3L , "Projet 3"));
        }};
        projetService = new ProjetLocalService(projets);
    }


    @Test
    void testGetAll(){
        assertEquals(3, projetService.getAll().size());
    }

    @Test
    void testGetByID() {
        assertAll(
                () -> assertEquals(projets.get(0) , projetService.getById(1L)),
                () -> assertEquals(projets.get(2) , projetService.getById(3L)),
                () -> assertThrows(ResourceNotFoundException.class, () -> projetService.getById(200L))
        );
    }

    @Test
    void testCreation() {
        Projet create = new Projet(5L, "Projet 5");
        Projet existProjet = projets.get(2);
        assertAll(
                () -> assertEquals(create, projetService.create(create)),
                () -> assertTrue(projetService.getAll().contains(create)),
                () -> assertThrows(ResourceAlreadyExistsException.class, () -> projetService.create(existProjet))
        );

    }

    @Test
    void testUpdate() {
        Projet projetBase = projets.get(1);
        Projet newProjet = new Projet(projetBase.getId(), "Modifier");
        Projet existProjet = projets.get(2);

        projetService.update(newProjet.getId(), newProjet);
        Projet updateProjet = projetService.getById(projetBase.getId());
        assertAll(
                () -> assertEquals(newProjet , updateProjet),
                () -> assertTrue(projetService.getAll().contains(newProjet)),
                () -> assertThrows(ResourceNotFoundException.class, () ->projetService.update(200L, existProjet))
        );
    }

    @Test
    void testDelete(){
        Projet projet = projets.get(1);
        Long id = 2020L;

        projetService.delete(projet.getId());

        assertAll(
                () -> assertFalse(projetService.getAll().contains(projet)),
                () -> assertThrows(ResourceNotFoundException.class, () -> projetService.delete(id))
        );

    }
}
