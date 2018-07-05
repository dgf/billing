package org.aplatanao.billing.persistence;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface InvoicesPerMonthRepository extends Repository<InvoicesPerMonthResource, InvoicesPerMonthResourceId> {

    @Query(value = "SELECT * FROM f_get_invoices_per_month(:year)", nativeQuery = true)
    List<InvoicesPerMonthResource> findByYear(Long year);
}
