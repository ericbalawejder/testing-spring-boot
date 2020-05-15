package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.fauxspring.Model;
import guru.springframework.sfgpetclinic.fauxspring.ModelMapImpl;
import guru.springframework.sfgpetclinic.model.Speciality;
import guru.springframework.sfgpetclinic.model.Vet;
import guru.springframework.sfgpetclinic.services.SpecialtyService;
import guru.springframework.sfgpetclinic.services.VetService;
import guru.springframework.sfgpetclinic.services.map.SpecialityMapService;
import guru.springframework.sfgpetclinic.services.map.VetMapService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class VetControllerTest {

    private VetService vetService;
    private SpecialtyService specialtyService;
    private VetController vetController;

    @BeforeEach
    void setUp() {
        specialtyService = new SpecialityMapService();
        vetService = new VetMapService(specialtyService);
        vetController = new VetController(vetService);


        Vet sarah = new Vet(1L, "Sarah", "Smith",
                Set.of(new Speciality(202L, "orthopedic")));

        Vet joe = new Vet(2L, "Joe", "Jackson",
                Set.of(new Speciality(202L, "orthopedic"),
                        new Speciality(210L, "oncology"),
                        new Speciality(311L, "dermatology")));

        vetService.save(sarah);
        vetService.save(joe);
    }

    @Test
    void listVets() {
        Model model = new ModelMapImpl();
        String view = vetController.listVets(model);
        assertThat("vets/index").isEqualTo(view);
        Set modelAttribute = (Set) ((ModelMapImpl) model).getMap().get("vets");
        assertThat(modelAttribute.size()).isEqualTo(2);
    }
}