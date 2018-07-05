package org.aplatanao.billing.persist;


import org.aplatanao.billing.persistence.InvoicesPerMonthResource;
import org.aplatanao.billing.persistence.InvoicesPerMonthResourceId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface InvoicesPerMonthTableRepository extends Repository<InvoicesPerMonthResource, InvoicesPerMonthResourceId> {

    @Query(value = "SELECT * FROM f_get_invoices_per_month(:year)", nativeQuery = true)
    List<InvoicesPerMonthResource> findByYear(Long year);
}
