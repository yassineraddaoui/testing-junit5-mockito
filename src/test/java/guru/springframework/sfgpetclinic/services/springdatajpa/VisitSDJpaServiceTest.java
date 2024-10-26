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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VisitSDJpaServiceTest {
    @Mock
    VisitRepository visitRepository;
    @InjectMocks
    VisitSDJpaService service;

    @Test
    void findAll() {
        Set<Visit> visits = Set.of(new Visit(), new Visit());
        when(visitRepository.findAll())
                .thenReturn(visits);
        var visitsResult = service.findAll();

        assertThat(visitsResult).isEqualTo(visits);
        verify(visitRepository, atMostOnce()).findAll();
    }

    @Test
    void findById() {
        var visit = new Visit();
        when(visitRepository.findById(1L))
                .thenReturn(Optional.of(visit));
        var visitResult = service.findById(1L);
        assertThat(visitResult).isEqualTo(visit);
        verify(visitRepository, atMostOnce()).findById(anyLong());
    }

    @Test
    void save() {
        Visit visit = new Visit();
        when(visitRepository.save(any(Visit.class))).thenReturn(visit);
        var result = service.save(visit);
        assertThat(result).isEqualTo(visit);
        verify(visitRepository, atMostOnce()).save(any(Visit.class));
    }

    @Test
    void delete() {
        var visit = new Visit();
        service.delete(visit);
        verify(visitRepository, atMostOnce()).delete(any(Visit.class));
    }

    @Test
    void deleteById() {
        var visit = new Visit();

        service.deleteById(1L);

        verify(visitRepository, atMostOnce()).deleteById(anyLong());
    }
}