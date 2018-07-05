package org.aplatanao.billing.persist;


import org.aplatanao.billing.persistence.InvoicesPerMonthTable;
import org.aplatanao.billing.persistence.InvoicesPerMonthTableId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface InvoicesPerMonthTableRepository extends Repository<InvoicesPerMonthTable, InvoicesPerMonthTableId> {

    @Query(value = "SELECT * FROM get_invoices_per_month(:year)", nativeQuery = true)
    List<InvoicesPerMonthTable> findByYear(Long year);
}
