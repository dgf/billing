package org.aplatanao.billing.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface PaymentRepository extends PagingAndSortingRepository<PaymentTable, UUID> {
    Page<PaymentTable> findAllByOrderByDateAsc(Pageable pageable);
}
