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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class SpecialitySDJpaServiceTest {

    @Mock
    SpecialtyRepository specialtyRepository;
    @InjectMocks
    SpecialitySDJpaService service;

    /*
    BDD
    1- given
    2- when
    3- then - should
     */
    @Test
    void deleteByIdTest() {
        //given -- none --
        //when
        specialtyRepository.deleteById(1L);

        //then
        then(specialtyRepository).should(atMostOnce())
                .deleteById(anyLong());

    }

    @Test
    void TestDeleteByObject() {
        //given
        var s = new Speciality();
        //when
        service.delete(s);
        // then - should
        then(specialtyRepository).should(atMostOnce()).delete(any(Speciality.class));
        //verify(specialtyRepository, atMostOnce()).deleteById(1L);

    }

    @Test
    void testFindById() {
        //given
        var speciality = new Speciality();
        //when
        when(specialtyRepository.findById(anyLong())).thenReturn(Optional.of(speciality));
        Speciality foundSpecialty = service.findById(1L);
        //then
        assertThat(foundSpecialty).isNotNull();
        then(specialtyRepository).should().findById(anyLong());
        //verify(specialtyRepository, atMostOnce()).findById(anyLong());

    }

    @Test
    void testDelete() {
        //given
        Speciality speciality = new Speciality();
        //when
        service.delete(speciality);
        //then
        then(specialtyRepository).should(atMostOnce()).delete(any(Speciality.class));
        //    verify(specialtyRepository, atMostOnce()).delete(any(Speciality.class));
    }

    @Test
    void deleteByIfNever() {
        //when
        service.deleteById(4L);
        service.deleteById(4L);
        //then
        then(specialtyRepository).should(atMost(2)).deleteById(anyLong());
        then(specialtyRepository)
                .should(never())
                .deleteById(1L);
        verify(specialtyRepository, never())
                .deleteById(1L);
    }

    @Test
    void testDoThrow() {
        doThrow(new RuntimeException())
                .when(specialtyRepository)
                .delete(any());

        assertThrows(RuntimeException.class,
                () -> service.delete(any(Speciality.class)));
    }

    @Test
    void testFindByIdThrowsBDD() {

        given(specialtyRepository.findById(anyLong()))
                .willThrow(new RuntimeException());

        assertThrows(RuntimeException.class,
                () -> service.findById(anyLong()));
        then(specialtyRepository).should().findById(anyLong());
    }

    @Test
    void testDeleteBDD() {
        willThrow(new RuntimeException())
                .given(specialtyRepository)
                .delete(any(Speciality.class));

        assertThrows(RuntimeException.class,
                () -> service.delete(new Speciality()));
        then(specialtyRepository)
                .should()
                .delete(any(Speciality.class));
    }
}