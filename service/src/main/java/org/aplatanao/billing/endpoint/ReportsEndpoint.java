package org.aplatanao.billing.endpoint;

import org.aplatanao.billing.persistence.InvoicesPerMonthRepository;
import org.aplatanao.billing.rest.api.ReportsApi;
import org.aplatanao.billing.rest.model.InvoicesPerMonth;
import org.aplatanao.billing.rest.model.InvoicesPerMonthInner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.stream.Collectors;

@Service
public class ReportsEndpoint implements ReportsApi {

    private InvoicesPerMonthRepository perMonth;

    @Autowired
    public ReportsEndpoint(InvoicesPerMonthRepository perMonth) {
        this.perMonth = perMonth;
    }

    @Override
    @Transactional(readOnly = true)
    public InvoicesPerMonth getInvoicesPerMonth(Integer year) {
        InvoicesPerMonth invoices = new InvoicesPerMonth();
        invoices.addAll(perMonth.findByYear(year.shortValue())
                .stream()
                .map(m -> new InvoicesPerMonthInner()
                        .year((int) m.getId().getYear())
                        .month((int) m.getId().getMonth())
                        .count(m.getCount())
                        .cents(m.getCents())
                )
                .sorted(Comparator.comparingInt(InvoicesPerMonthInner::getMonth))
                .collect(Collectors.toList()));
        return invoices;
    }
}
