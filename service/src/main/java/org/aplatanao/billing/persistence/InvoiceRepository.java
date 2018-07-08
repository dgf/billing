package org.aplatanao.billing.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface InvoiceRepository extends PagingAndSortingRepository<InvoiceTable, String> {
    Page<InvoiceTable> findAllByOrderByDateAsc(Pageable pageable);
}
