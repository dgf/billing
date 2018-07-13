package org.aplatanao.billing.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface InvoiceRepository extends PagingAndSortingRepository<InvoiceTable, UUID> {
    Page<InvoiceTable> findAllByOrderByDateAsc(Pageable pageable);
}
