package org.aplatanao.billing.repositories;


import org.aplatanao.billing.domain.Invoice;
import org.springframework.data.repository.CrudRepository;

public interface InvoiceRepository extends CrudRepository<Invoice, Long> {
}
