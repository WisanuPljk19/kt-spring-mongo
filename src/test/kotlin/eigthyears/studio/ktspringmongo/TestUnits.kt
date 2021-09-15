package eigthyears.studio.ktspringmongo

import eigthyears.studio.ktspringmongo.model.Customer
import reactor.core.publisher.Mono


class TestUnits {

    companion object {
        fun mockCustomers(): List<Customer> {
            val customers: MutableList<Customer> = ArrayList()
            for (i in 0..3) {
                customers.add(mockCustomer())
            }
            return customers
        }

        fun mockCustomerMono(): Mono<Customer> {
            return Mono.just(mockCustomer())
        }

        fun mockCustomerForUpdate(): Mono<Customer> {
            val customer = mockCustomer()
            customer.age = 21
            return Mono.just(customer)
        }

        fun mockCustomer(): Customer {
            return Customer(firstName = "John",
                lastName = "Doe",
                age = 20,
                email = "john.doe@mail.com")
        }
    }

}