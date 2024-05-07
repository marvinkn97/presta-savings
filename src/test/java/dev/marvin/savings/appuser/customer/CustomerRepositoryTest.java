package dev.marvin.savings.appuser.customer;

import dev.marvin.savings.AbstractTestContainersTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomerRepositoryTest extends AbstractTestContainersTest {
    @Autowired
    CustomerRepository customerRepository;


    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = Customer.builder()
                .email("foo@example.com")
                .name("Foo Bar")
                .isEmailConfirmed(false)
                .memberNumber("MEM234567")
                .build();
    }

    @Test
    @DisplayName(value = "Test Case for Saving Customer")
    void givenAppUserObject_whenSave_thenReturnSavedAppUser(){

        //when
        var actual = customerRepository.save(customer);

        //then
        Assertions.assertThat(actual).isNotNull();
        Assertions.assertThat(actual.getId()).isGreaterThan(0);
    }

    @Test
    @DisplayName(value = "Test Case for find all Customers")
    void givenCustomerList_whenFindAll_thenReturnCustomerList(){

        customerRepository.deleteAll();

        var newCustomer = Customer.builder()
                .email("foo2@example.com")
                .name("Foo Bar")
                .isEmailConfirmed(false)
                .memberNumber("MEM234569")
                .build();

        customerRepository.save(customer);
        customerRepository.save(newCustomer);

        //when
        var actual = customerRepository.findAll();

        //then
        Assertions.assertThat(actual.size()).isGreaterThan(0);
        Assertions.assertThat(actual.size()).isEqualTo(2);
    }

    @Test
    void findByMemberNumber() {
    }

    @Test
    void existsByEmail() {
    }


    @Test
    void testFindByMemberNumber() {
    }

    @Test
    void testExistsByEmail() {
    }
}