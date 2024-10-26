package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Speciality;
import guru.springframework.sfgpetclinic.repositories.SpecialtyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpecialitySDJpaServiceTest {

    @Mock
    SpecialtyRepository specialtyRepository;
    @InjectMocks
    SpecialitySDJpaService service;

    @Test
    void delete() {
        service.deleteById(1L);
        service.deleteById(1L);

        verify(specialtyRepository, atMost(2)).deleteById(1L);
        verify(specialtyRepository, atLeast(2)).deleteById(1L);
        verify(specialtyRepository, times(2)).deleteById(1L);
    }

    @Test
    void Testdelete() {
        service.delete(new Speciality());
        verify(specialtyRepository, atMostOnce()).deleteById(1L);

    }

    @Test
    void testFindById() {
        var speciality = new Speciality();
        when(specialtyRepository.findById(1L)).thenReturn(Optional.of(speciality));
        Speciality foundSpecialty = service.findById(1L);
        assertThat(foundSpecialty).isNotNull();
        verify(specialtyRepository, atMostOnce()).findById(anyLong());
    }

    @Test
    void testDelete() {
        Speciality speciality = new Speciality();
        service.delete(speciality);
        verify(specialtyRepository, atMostOnce()).delete(any(Speciality.class));
    }
}