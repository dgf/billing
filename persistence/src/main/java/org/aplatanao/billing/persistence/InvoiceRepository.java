package org.aplatanao.billing.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface InvoiceRepository extends PagingAndSortingRepository<InvoiceTable, UUID> {

    Page<InvoiceTable> findAllByOrderByDateAsc(Pageable pageable);

    @Query(value = "SELECT * FROM t_invoice WHERE EXTRACT(YEAR FROM date) = :year", nativeQuery = true)
    Page<InvoiceTable> findByYear(@Param("year") short year, Pageable pageable);
}
