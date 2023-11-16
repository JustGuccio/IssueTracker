package geiffel.da4.issuetracker.projet;

import com.fasterxml.jackson.databind.ObjectMapper;
import geiffel.da4.issuetracker.exceptions.ExceptionHandlingAdvice;
import geiffel.da4.issuetracker.exceptions.ResourceAlreadyExistsException;
import geiffel.da4.issuetracker.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;


import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest
@ContextConfiguration(classes = ProjetController.class)
@Import(ExceptionHandlingAdvice.class)
class ProjetControllerTest {


    @Autowired
    MockMvc mockMvc;

    private List<Projet>  projets;

    @MockBean
    private ProjetService projetService;


    @BeforeEach
    void setUp(){
        projets = new ArrayList<>(){{
            add( new Projet(1L, "Projet1"));
            add( new Projet(2L, "Projet2"));
            add( new Projet(3L, "Projet3"));
            add( new Projet(4L, "Projet4"));
            add( new Projet(5L, "Projet5"));
        }};
        Mockito.when(projetService.getAll()).thenReturn(projets);
        Mockito.when(projetService.getById(1L)).thenReturn(projets.get(0));
        Mockito.when(projetService.getById(5555L)).thenThrow(ResourceNotFoundException.class);
    }

    @Test
    void testGetAll() throws Exception{
        mockMvc.perform(get("/projets")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()
        ).andExpect(jsonPath("$", hasSize(5))
        ).andDo(print());
    }

    @Test
    void testGetByIdPassed() throws Exception{
        mockMvc.perform(get("/projets/1").contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk()
        ).andExpect(jsonPath("$.id", is(1))
        ).andExpect(jsonPath("$.nom", is("Projet1"))
        ).andReturn();
    }

    @Test
    void testGetByIdUnPassed() throws Exception{
        mockMvc.perform(get("/projet/5555")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound()
        ).andDo(print());
    }

    @Test
    void testCreationProjetPassed() throws Exception {
        Projet newProjet =new Projet(6L, "Projet6");
        /*ArgumentCaptor<Projet> projetRecevied = ArgumentCaptor.forClass(Projet.class);*/
        Mockito.when(projetService.create(any(Projet.class))).thenReturn(newProjet);

        mockMvc.perform(post("/projets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(newProjet))
        ).andExpect(status().isCreated()
        ).andExpect(header().string("Location", "/projets/"+newProjet.getId())
        ).andDo(print()).andReturn();
/*
        verify(projetService).create(projetRecevied.capture());
        assertEquals(newProjet, projetRecevied.getValue());
*/
    }

    @Test
    void testCreationProjetNoPass()throws Exception{
        Projet newProjet = projetService.getById(1L);
        when(projetService.create(any())).thenThrow(ResourceAlreadyExistsException.class);

        mockMvc.perform(post("/projets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(newProjet))
        ).andExpect(status().isConflict()
        ).andDo(print());
    }
/*
    @Test
    void testUpdateProjetPass() throws Exception {
        Projet updateProjet = projets.get(0);
        updateProjet.setId(50L);
        ArgumentCaptor<Projet> projet_received = ArgumentCaptor.forClass(Projet.class);

        mockMvc.perform(put("/issues/50")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updateProjet))
        ).andExpect(status().isNoContent()
        ).andDo(print()).andReturn();

        Mockito.verify(projetService).update(Mockito.anyLong(), projet_received.capture());
        assertEquals(updateProjet, projet_received.getValue());
    }

 */

    @Test
    void testDeleteProjet() throws Exception{

        Long projetId = projets.get(0).getId();

        mockMvc.perform(delete("/projets/"+projetId)
        ).andExpect(status().isNoContent()
        ).andDo(print()).andReturn();

        ArgumentCaptor<Long> id_received = ArgumentCaptor.forClass(Long.class);
        Mockito.verify(projetService).delete(id_received.capture());
        assertEquals(projetId, id_received.getValue());

    }

}
