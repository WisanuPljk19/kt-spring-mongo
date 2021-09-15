package eigthyears.studio.ktspringmongo.service

import eigthyears.studio.ktspringmongo.model.Customer
import eigthyears.studio.ktspringmongo.repository.CustomerRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


@Service
class CustomerService {

    @Autowired lateinit var repository: CustomerRepository

    fun getCustomers(): Flux<Customer>? {
        return repository.findAll()
    }

    fun getCustomerById(id: String): Mono<Customer>? {
        return repository.findById(id)
    }

    fun getCustomerByFirstName(firstName: String): Flux<Customer>? {
        return repository.findByFirstName(firstName)
    }

    fun createCustomer(customer: Mono<Customer>): Mono<Customer>? {
        return customer.flatMap { c: Customer ->
            repository.save(
                c
            )
        }
    }

    fun updateCustomerById(id: String, customer: Mono<Customer>): Mono<Customer>? {
        return customer.flatMap { c: Customer ->
            repository.findById(
                id
            ).flatMap { (id1): Customer ->
                c.id = id1
                repository.save(c)
            }
        }
    }

    fun deleteCustomer(id: String): Mono<Void>? {
        return repository.deleteById(id)
    }

}