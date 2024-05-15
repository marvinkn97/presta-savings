package dev.marvin.savings.appuser.customer;

import dev.marvin.savings.AbstractTestContainersTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomerRepositoryTest extends AbstractTestContainersTest {
    @Autowired
    CustomerRepository underTest;

    @BeforeEach
    void setUp() {
        underTest.deleteAll();

        var c1 = Customer.builder()
                .email("customer1@presta.com")
                .name("Customer One")
                .memberNumber("MEM123")
                .build();

        var c2 = Customer.builder()
                .email("customer2@presta.com")
                .name("Customer Two")
                .memberNumber("MEM456")
                .build();

        var c3 = Customer.builder()
                .email("customer3@presta.com")
                .name("Customer Three")
                .memberNumber("MEM789")
                .build();

        var customers = List.of(c1, c2, c3);

        underTest.saveAll(customers);
    }

    @Test
    void givenCustomerObject_whenSave_thenReturnSavedCustomer() {

        //given
        var customer = Customer.builder()
                .memberNumber("MEM345678")
                .name("Marvin Nyingi")
                .email("marvin@example.com")
                .build();

        //when
        var actual = underTest.save(customer);

        //then
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isGreaterThan(0);
        assertThat(underTest.findAll().size()).isEqualTo(4);
        assertThat(actual).satisfies(c -> {
            assertThat(c.getEmail()).isEqualTo(customer.getEmail());
            assertThat(c.getMemberNumber()).isEqualTo(customer.getMemberNumber());
            assertThat(c.getName()).isEqualTo(customer.getName());
        });
    }

    @Test
    void givenCustomerList_whenFindAll_thenReturnCustomerList() {

        //when
        var actual = underTest.findAll();

        //then
        assertThat(actual).isNotEmpty();
        assertThat(actual.size()).isEqualTo(3);
    }

    @Test
    void givenValidMemberNumber_whenFindByMemberNumber_thenReturnCustomer() {

        //given
        String memberNumber = "MEM123";

        //when
        var actual = underTest.findByMemberNumber(memberNumber);

        //then
        assertThat(actual).isPresent();
        actual.ifPresent(customer -> assertThat(customer.getMemberNumber()).isEqualTo(memberNumber));
    }

    @Test
    void givenInvalidMemberNumber_whenFindByMemberNumber_thenThrowException() {
        //given
        String memberNumber = "000";

        //when
        var actual = underTest.findByMemberNumber(memberNumber);

        //then
        assertThat(actual).isNotPresent();
        assertThatThrownBy(actual::get)
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("No value present");
    }

    @Test
    void givenValidEmail_whenExistsByEmail_thenReturnTrue() {
        //given
        String email = "customer2@presta.com";

        //when
        var actual = underTest.existsByEmail(email);

        //then
        assertThat(actual).isTrue();
    }

    @Test
    void givenInvalidValidEmail_whenExistsByEmail_thenReturnFalse() {
        //given
        String email = "000@gmail.com";

        //when
        var actual = underTest.existsByEmail(email);

        //then
        assertThat(actual).isFalse();
    }

}