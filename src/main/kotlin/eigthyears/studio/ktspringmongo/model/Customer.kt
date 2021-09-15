package eigthyears.studio.ktspringmongo.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Customer(
    @Id var id: String? = null,
    var firstName: String,
    var lastName: String,
    var age: Int? = null,
    var email: String? = null
)