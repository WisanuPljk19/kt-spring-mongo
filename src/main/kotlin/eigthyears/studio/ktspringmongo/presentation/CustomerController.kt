package eigthyears.studio.ktspringmongo.presentation

import eigthyears.studio.ktspringmongo.model.Customer
import eigthyears.studio.ktspringmongo.service.CustomerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


@RestController
@RequestMapping("/customers")
class CustomerController {

    @Autowired lateinit var service: CustomerService


    @GetMapping
    fun getAllCustomers(): Flux<Customer>? {
        return service.getCustomers()
    }

    @GetMapping("/{id}")
    fun getCustomer(@PathVariable id: String): Mono<ResponseEntity<Customer>>? {
        return service.getCustomerById(id)!!
            .map { body: Customer? ->
                ResponseEntity.ok(
                    body
                )
            }
            .defaultIfEmpty(ResponseEntity.notFound().build())
    }

    @GetMapping(params = ["name"])
    fun getCustomerByName(@RequestParam("name") name: String): Flux<Customer>? {
        return service.getCustomerByFirstName(name)
    }

    @PostMapping
    fun postCustomer(@RequestBody customer: Mono<Customer>): Mono<Customer>? {
        return service.createCustomer(customer)
    }

    @PutMapping("/{id}")
    fun updateCustomer(
        @PathVariable id: String,
        @RequestBody customer: Mono<Customer>
    ): Mono<ResponseEntity<Customer>>? {
        return service.updateCustomerById(id, customer)!!
            .map { body: Customer? ->
                ResponseEntity.ok(
                    body
                )
            }
            .defaultIfEmpty(ResponseEntity.notFound().build())
    }

    @DeleteMapping("/{id}")
    fun deleteCustomer(@PathVariable id: String): Mono<Void>? {
        return service.deleteCustomer(id)
    }
}