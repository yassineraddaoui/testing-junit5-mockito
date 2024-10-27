package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.repositories.VisitRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VisitSDJpaServiceTest {
    @Mock
    VisitRepository visitRepository;
    @InjectMocks
    VisitSDJpaService service;

    @Test
    void findAll() {
        //given
        Set<Visit> visits = Set.of(new Visit(), new Visit());
        given(visitRepository.findAll()).willReturn(visits);
//        when(visitRepository.findAll()).thenReturn(visits);

        //when
        var visitsResult = service.findAll();

        //then
        then(visitRepository)
                .should(atMostOnce())
                .findAll();
        assertThat(visitsResult).isEqualTo(visits);
        //verify(visitRepository, atMostOnce()).findAll();
    }

    @Test
    void findById() {

        //given
        var visit = new Visit();
//        when(visitRepository.findById(anyLong()))
//                .thenReturn(Optional.of(visit));

        given(visitRepository.findById(anyLong()))
                .willReturn(Optional.of(visit));
        //when
        var visitResult = service.findById(1L);

        //then
        assertThat(visitResult).isEqualTo(visit);
        then(visitRepository).should().findById(anyLong());

        //verify(visitRepository, atMostOnce()).findById(anyLong());
    }

    @Test
    void findByIdBDD() {
        //given
        Visit vs = new Visit();
        given(visitRepository.findById(1L))
                .willReturn(Optional.of(vs));
        //when
        var visitResult = service.findById(1L);

        //then
        assertThat(visitResult).isEqualTo(vs);
        //verify(visitRepository, atMostOnce()).findById(anyLong());
        then(visitRepository).should(atMostOnce())
                .findById(anyLong());
        then(visitRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void save() {
        //given
        Visit visit = new Visit();
        given(visitRepository.save(any(Visit.class)))
                .willReturn(visit);
        //when(visitRepository.save(any(Visit.class))).thenReturn(visit);

        //when
        var result = service.save(visit);

        //then
        assertThat(result).isEqualTo(visit);
        then(visitRepository).should(atMostOnce())
                .save(any(Visit.class));
        //verify(visitRepository, atMostOnce()).save(any(Visit.class));
    }

    @Test
    void delete() {
        //given
        var visit = new Visit();
        //when
        service.delete(visit);
        //then - should
        then(visitRepository).should(atMostOnce())
                .delete(any(Visit.class));

        //verify(visitRepository, atMostOnce()).delete(any(Visit.class));
    }

    @Test
    void deleteById() {
        //when
        service.deleteById(1L);
        //then
        then(visitRepository).should(atMostOnce())
                .deleteById(anyLong());
        //verify(visitRepository, atMostOnce()).deleteById(anyLong());

    }

}