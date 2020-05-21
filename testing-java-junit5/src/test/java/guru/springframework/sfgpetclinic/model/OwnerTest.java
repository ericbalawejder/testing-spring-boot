package guru.springframework.sfgpetclinic.model;

import guru.springframework.sfgpetclinic.CustomArgsProvider;
import guru.springframework.sfgpetclinic.ModelTests;
import org.assertj.core.internal.bytebuddy.asm.Advice;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class OwnerTest implements ModelTests {

    @Test
    void dependentAssertions() {

        Owner owner = new Owner(1l, "Joe", "Buck");
        owner.setCity("Key West");
        owner.setTelephone("1231231234");

        assertAll("Properties Test",
                () -> assertAll("Person Properties",
                        () -> assertEquals("Joe", owner.getFirstName(), "First Name Did not Match"),
                        () -> assertEquals("Buck", owner.getLastName())),
                () -> assertAll("Owner Properties",
                        () -> assertEquals("Key West", owner.getCity(), "City Did Not Match"),
                        () -> assertEquals("1231231234", owner.getTelephone())
                ));
    }

    @DisplayName("Value Source Test - ")
    @ParameterizedTest(name = "{displayName} [{index}] {arguments}")
    @ValueSource(strings = {"Spring", "Framework", "Guru"})
    void testValueSource(String value) {
        System.out.println(value);
    }

    @DisplayName("Enum Source Test - ")
    @ParameterizedTest(name = "{displayName} [{index}] {arguments}")
    @EnumSource(OwnerType.class)
    void enumTest(OwnerType ownerType) {
        System.out.println(ownerType);
    }

    @DisplayName("CSV Input Test - ")
    @ParameterizedTest(name = "{displayName} [{index}] {arguments}")
    @CsvSource({
            "FL, 1, 1",
            "PA, 4, 2",
            "NH, 22, 44"
    })
    void csvInputTest(String stateName, int value1, int value2) {
        System.out.println(stateName + " = " + value1 + ":" + value2);
    }

    @DisplayName("CSV Input Test - ")
    @ParameterizedTest(name = "{displayName} [{index}] {arguments}")
    @CsvFileSource(resources = "/input.csv", numLinesToSkip = 1)
    void csvFromFileTest(String stateName, int value1, int value2) {
        System.out.println(stateName + " = " + value1 + ":" + value2);
    }

    @DisplayName("Method Provider Test - ")
    @ParameterizedTest(name = "{displayName} [{index}] {arguments}")
    @MethodSource("getargs")
    void fromMethodTest(String stateName, int value1, int value2) {
        System.out.println(stateName + " = " + value1 + ":" + value2);
    }

    static Stream<Arguments> getargs() {
        return Stream.of(
                Arguments.of("FL", 1, 1),
                Arguments.of("OH", 3, 4),
                Arguments.of("CA", 44, 87));
    }

    @DisplayName("Method Provider Test - ")
    @ParameterizedTest(name = "{displayName} [{index}] {arguments}")
    @ArgumentsSource(CustomArgsProvider.class)
    void fromCustomProviderTest(String stateName, int value1, int value2) {
        System.out.println(stateName + " = " + value1 + ":" + value2);
    }
}