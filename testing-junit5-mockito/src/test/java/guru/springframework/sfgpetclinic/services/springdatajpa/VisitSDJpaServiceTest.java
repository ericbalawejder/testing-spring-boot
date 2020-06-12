package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.repositories.VisitRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class VisitSDJpaServiceTest {

    @Mock
    VisitRepository visitRepository;

    @InjectMocks
    VisitSDJpaService visitSDJpaService;

    @DisplayName("Test Find All")
    @Test
    void findAll() {
        // given
        Visit visit = new Visit();
        Set<Visit> visits = new HashSet<>();
        visits.add(visit);
        //when(visitRepository.findAll()).thenReturn(visits);
        given(visitRepository.findAll()).willReturn(visits);

        // when
        Set<Visit> foundVisits = visitSDJpaService.findAll();

        // then
        //verify(visitRepository).findAll();
        then(visitRepository).should().findAll();
        assertThat(foundVisits).hasSize(1);
    }

    @Test
    void findById() {
        //given
        Visit visit = new Visit();
        given(visitRepository.findById(anyLong())).willReturn(Optional.of(visit));
        //when(visitRepository.findById(anyLong())).thenReturn(Optional.of(visit));

        // when
        Visit foundVisit = visitSDJpaService.findById(1L);

        // then
        //verify(visitRepository).findById(anyLong());
        then(visitRepository).should().findById(anyLong());
        assertThat(foundVisit).isNotNull();
    }

    @Test
    void save() {
        // given
        Visit visit = new Visit();
        given(visitRepository.save(any(Visit.class))).willReturn(visit);
        //when(visitRepository.save(any(Visit.class))).thenReturn(visit);

        // when
        Visit savedVisit = visitSDJpaService.save(new Visit());

        // then
        //verify(visitRepository).save(any(Visit.class));
        then(visitRepository).should().save(any(Visit.class));
        assertThat(savedVisit).isNotNull();
    }

    @Test
    void delete() {
        //given
        Visit visit = new Visit();

        //when
        visitSDJpaService.delete(visit);

        //then
        //verify(visitRepository).delete(any(Visit.class));
        then(visitRepository).should().delete(any(Visit.class));
    }

    @Test
    void deleteById() {
        // when
        Visit visit = new Visit(1L);
        visitSDJpaService.deleteById(visit.getId());

        // Can replace visit.getId()
        //visitSDJpaService.deleteById(1L);

        // then
        //verify(visitRepository).deleteById(anyLong());
        then(visitRepository).should().deleteById(anyLong());
    }
}