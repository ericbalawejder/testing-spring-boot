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
        Visit visit = new Visit();

        Set<Visit> visits = new HashSet<>();
        visits.add(visit);

        when(visitRepository.findAll()).thenReturn(visits);

        Set<Visit> foundVisits = visitSDJpaService.findAll();

        verify(visitRepository).findAll();

        assertThat(foundVisits).hasSize(1);
    }

    @Test
    void findById() {
        Visit visit = new Visit();

        when(visitRepository.findById(anyLong())).thenReturn(Optional.of(visit));

        Visit foundVisit = visitSDJpaService.findById(1L);

        verify(visitRepository).findById(anyLong());

        assertThat(foundVisit).isNotNull();
    }

    @Test
    void save() {
        Visit visit = new Visit();

        when(visitRepository.save(any(Visit.class))).thenReturn(visit);

        Visit savedVisit = visitSDJpaService.save(new Visit());

        verify(visitRepository).save(any(Visit.class));

        assertThat(savedVisit).isNotNull();
    }

    @Test
    void delete() {
        Visit visit = new Visit();

        visitSDJpaService.delete(visit);

        verify(visitRepository).delete(any(Visit.class));
    }

    @Test
    void deleteById() {
        Visit visit = new Visit(1L);

        visitSDJpaService.deleteById(visit.getId());

        //visitSDJpaService.deleteById(1L);

        verify(visitRepository).deleteById(anyLong());
    }
}