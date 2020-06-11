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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SpecialitySDJpaServiceTest {

    @Mock
    SpecialtyRepository specialtyRepository;

    @InjectMocks
    SpecialitySDJpaService specialitySDJpaService;

    @Test
    void deleteById() {
        specialitySDJpaService.deleteById(1L);
        specialitySDJpaService.deleteById(1L);

        // specify the number of times that mock gets invoked.
        //verify(specialtyRepository, times(2)).deleteById(1L);

        //verify(specialtyRepository, atLeastOnce()).deleteById(1L);
        verify(specialtyRepository, atMost(5)).deleteById(1L);
    }

    @Test
    void deleteByIdNever() {
        verify(specialtyRepository, never()).deleteById(1L);
    }

    @Test
    void testDelete() {
        specialitySDJpaService.delete(new Speciality());
    }

    @Test
    void findById() {
        Speciality speciality = new Speciality();

        when(specialtyRepository.findById(1L)).thenReturn(Optional.of(speciality));

        Speciality foundSpecialty = specialitySDJpaService.findById(1L);

        assertThat(foundSpecialty).isNotNull();

        //verify(specialtyRepository).findById(1L);
        // argument matcher
        verify(specialtyRepository).findById(anyLong());
    }

    @Test
    void testDeleteByObject() {
        Speciality speciality = new Speciality();

        specialitySDJpaService.delete(speciality);

        verify(specialtyRepository).delete(any(Speciality.class));
    }
}