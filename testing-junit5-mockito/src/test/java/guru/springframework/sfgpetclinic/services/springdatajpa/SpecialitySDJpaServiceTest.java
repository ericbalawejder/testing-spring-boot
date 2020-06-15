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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.atMost;

@ExtendWith(MockitoExtension.class)
class SpecialitySDJpaServiceTest {

    @Mock(lenient = true)
    SpecialtyRepository specialtyRepository;

    @InjectMocks
    SpecialitySDJpaService specialitySDJpaService;

    @Test
    void testDeleteByObject() {
        //given
        Speciality speciality = new Speciality();

        //when
        specialitySDJpaService.delete(speciality);

        //then
        then(specialtyRepository).should().delete(any(Speciality.class));
    }

    @Test
    void findByIdTest() {
        //given
        Speciality speciality = new Speciality();
        given(specialtyRepository.findById(1L)).willReturn(Optional.of(speciality));

        //when
        Speciality foundSpecialty = specialitySDJpaService.findById(1L);

        //then
        assertThat(foundSpecialty).isNotNull();
        then(specialtyRepository).should(timeout(100)).findById(anyLong());
        then(specialtyRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void deleteById() {
        //given - none

        //when
        specialitySDJpaService.deleteById(1l);
        specialitySDJpaService.deleteById(1l);

        //then
        then(specialtyRepository).should(timeout(100).times(2)).deleteById(1L);
    }

    @Test
    void deleteByIdAtLeast() {
        //given

        //when
        specialitySDJpaService.deleteById(1l);
        specialitySDJpaService.deleteById(1l);

        //then
        then(specialtyRepository).should(timeout(100).atLeastOnce()).deleteById(1L);
    }

    @Test
    void deleteByIdAtMost() {
        //when
        specialitySDJpaService.deleteById(1l);
        specialitySDJpaService.deleteById(1l);

        //then
        // api does not allow timeout(100).atMost(int i)
        then(specialtyRepository).should(atMost(5)).deleteById(1L);
    }

    @Test
    void deleteByIdNever() {

        //when
        specialitySDJpaService.deleteById(1l);
        specialitySDJpaService.deleteById(1l);

        //then
        then(specialtyRepository).should(timeout(200).atLeastOnce()).deleteById(1L);
        then(specialtyRepository).should(never()).deleteById(5L);

    }

    @Test
    void testDelete() {
        //when
        specialitySDJpaService.delete(new Speciality());

        //then
        then(specialtyRepository).should().delete(any());
    }

    @Test
    void testDoThrow() {
        doThrow(new RuntimeException("boom")).when(specialtyRepository).delete(any());

        assertThrows(RuntimeException.class, () -> specialtyRepository.delete(new Speciality()));

        verify(specialtyRepository).delete(any());
    }

    @Test
    void testFindByIDThrows() {
        given(specialtyRepository.findById(1L)).willThrow(new RuntimeException("boom"));

        assertThrows(RuntimeException.class, () -> specialitySDJpaService.findById(1L));

        then(specialtyRepository).should().findById(1L);
    }

    @Test
    void testDeleteBDD() {
        // for void return type
        willThrow(new RuntimeException("boom")).given(specialtyRepository).delete(any());

        assertThrows(RuntimeException.class, () -> specialtyRepository.delete(new Speciality()));

        then(specialtyRepository).should().delete(any());
    }

    @Test
    void testSaveLambda() {
        //given
        final String MATCH_ME = "MATCH_ME";
        Speciality speciality = new Speciality();
        speciality.setDescription(MATCH_ME);

        Speciality savedSpecialty = new Speciality();
        savedSpecialty.setId(1L);

        //need mock to only return on match MATCH_ME string
        given(specialtyRepository.save(argThat(argument -> argument.getDescription().equals(MATCH_ME))))
                .willReturn(savedSpecialty);

        //when
        Speciality returnedSpecialty = specialitySDJpaService.save(speciality);

        //then
        assertThat(returnedSpecialty.getId()).isEqualTo(1L);
    }

    @Test
    void testSaveLambdaNoMatch() {
        //given
        final String MATCH_ME = "MATCH_ME";
        Speciality speciality = new Speciality();
        speciality.setDescription("Not a match");

        Speciality savedSpecialty = new Speciality();
        savedSpecialty.setId(1L);

        //need mock to only return on match MATCH_ME string
        given(specialtyRepository.save(argThat(argument -> argument.getDescription().equals(MATCH_ME))))
                .willReturn(savedSpecialty);

        //when
        Speciality returnedSpecialty = specialitySDJpaService.save(speciality);

        //then
        assertNull(returnedSpecialty);
    }
}