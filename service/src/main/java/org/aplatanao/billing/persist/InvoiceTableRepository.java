package org.aplatanao.billing.persist;

import org.aplatanao.billing.persistence.InvoiceTable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface InvoiceTableRepository extends PagingAndSortingRepository<InvoiceTable, Long> {
    InvoiceTable getById(Long id);
}
