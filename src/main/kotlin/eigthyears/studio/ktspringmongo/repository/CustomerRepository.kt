package eigthyears.studio.ktspringmongo.repository

import eigthyears.studio.ktspringmongo.model.Customer
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux

interface CustomerRepository: ReactiveCrudRepository<Customer, String> {
    fun findByFirstName(fistName: String?): Flux<Customer>?
}