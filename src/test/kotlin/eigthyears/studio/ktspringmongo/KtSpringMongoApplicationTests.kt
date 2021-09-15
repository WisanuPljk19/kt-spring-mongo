package eigthyears.studio.ktspringmongo

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import eigthyears.studio.ktspringmongo.model.Customer
import org.springframework.test.web.reactive.server.WebTestClient
import eigthyears.studio.ktspringmongo.repository.CustomerRepository
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class KtSpringMongoApplicationTests {
    @Autowired
    private lateinit var context: ApplicationContext

    @Autowired
    private lateinit var repository: CustomerRepository

    private lateinit var client: WebTestClient

    @BeforeAll
    fun setUp() {
        client = WebTestClient.bindToApplicationContext(context).build()
    }

    @Test
    @Order(0)
    fun givenCustomers_whenGetMethod_thenReturnOkCustomersBody() {
        repository.saveAll(TestUnits.mockCustomers()).collectList().block()
        client.get().uri("/customers")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(Customer::class.java)
            .hasSize(6)
    }

    @Test
    @Order(1)
    fun givenID_whenGetMethodByID_thenReturnOkCustomerBody() {
        val mockedId: String? = repository.save(TestUnits.mockCustomer()).map(Customer::id).block()
        client.get().uri(String.format("/customers/%s", mockedId))
            .exchange()
            .expectStatus().isOk
            .expectBody(Customer::class.java)
    }

    @Test
    @Order(2)
    fun givenFistName_whenGetMethodByFirstName_thenReturnOkCustomerBody() {
        val mockedId: String? = repository.save(TestUnits.mockCustomer()).map(Customer::firstName).block()
        client.get().uri(String.format("/customers?name=%s", mockedId))
            .exchange()
            .expectStatus().isOk
            .expectBodyList(Customer::class.java)
    }

    @Test
    @Order(3)
    fun givenCustomer_whenPostMethod_thenReturnCreatedCustomerBody() {
        client.post().uri("/customers")
            .body(TestUnits.mockCustomerMono(), Customer::class.java)
            .exchange()
            .expectStatus().isOk
            .expectBody(Customer::class.java)
    }

    @Test
    @Order(4)
    fun givenID_whenPutMethodByID_thenReturnOkCustomerBody() {
        val mockedId: String? = repository.save(TestUnits.mockCustomer()).map(Customer::id).block()
        client.put().uri(String.format("/customers/%s", mockedId))
            .body(TestUnits.mockCustomerForUpdate(), Customer::class.java)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.age").isEqualTo(21)
    }

    @Test
    @Order(5)
    fun givenID_whenDeleteMethodByID_thenReturnOkNoBody() {
        val mockedId: String? = repository.save(TestUnits.mockCustomer()).map(Customer::id).block()
        client.delete().uri(String.format("/customers/%s", mockedId))
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .isEmpty
    }
}
