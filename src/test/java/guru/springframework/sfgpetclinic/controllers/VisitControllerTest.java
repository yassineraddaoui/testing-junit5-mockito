package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.services.VisitService;
import guru.springframework.sfgpetclinic.services.map.PetMapService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class VisitControllerTest {
    @Mock
    VisitService visitService;
    //@Mock
    @Spy
    PetMapService petService;
    @InjectMocks
    VisitController visitController;

    @Test
    void setAllowedFields() {
    }

    @Test
    void loadPetWithVisit() {
        //given
        Map<String, Object> model = new HashMap<>();
        Pet pet = new Pet();
        petService.save(pet);
        given(petService.findById(anyLong()))
                .willCallRealMethod();
        //.willReturn(pet);
        //when

        Visit visit = visitController.loadPetWithVisit(1L, model);

        //then
        assertThat(visit.getPet().getId()).isEqualTo(1L);
        assertThat(visit).isNotNull();
        assertThat(visit.getPet()).isNotNull();
        verify(petService,times(1)).findById(anyLong());
    }

    @Test
    void loadPetWithVisitStubbing() {
        //given
        Map<String, Object> model = new HashMap<>();
        Pet pet = new Pet(1L);
        Pet pet2 = new Pet(2L);
        petService.save(pet);
        given(petService.findById(anyLong()))
                .willReturn(pet2);
        //.willReturn(pet);
        //when

        Visit visit = visitController.loadPetWithVisit(1L, model);

        //then
        assertThat(visit.getPet().getId()).isEqualTo(1L);
        assertThat(visit).isNotNull();
        assertThat(visit.getPet()).isNotNull();
        verify(petService,times(1)).findById(anyLong());
    }
    @Test
    void initNewVisitForm() {
    }

    @Test
    void processNewVisitForm() {
    }
}