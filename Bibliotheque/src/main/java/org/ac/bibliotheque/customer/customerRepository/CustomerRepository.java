package org.ac.bibliotheque.customer.customerRepository;

import org.ac.bibliotheque.customer.entity.Customer;

public interface CustomerRepository {
    Customer findByEmail(String email);


}
