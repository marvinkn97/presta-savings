package dev.marvin.savings.appuser.customer;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @Test
    void givenCustomerObject_whenSave_thenReturnSavedCustomer(){
        //given
        var customer = Customer.builder()
                .email("foo@example.com")
                .name("Foo Bar")
                .memberNumber("MEM234567")
                .build();

        //when
        var actual = customerRepository.save(customer);

        //then
        Assertions.assertThat(actual).isNotNull();
        Assertions.assertThat(actual.getId()).isGreaterThan(0);
    }

    @Test
    void findByMemberNumber() {
    }

    @Test
    void existsByEmail() {
    }
}