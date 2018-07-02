package org.aplatanao.billing.repositories;

import org.aplatanao.billing.entities.Invoice;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface InvoiceRepository extends PagingAndSortingRepository<Invoice, Long> {
    Invoice getById(Long id);
}
