package org.aplatanao.billing.service;

import org.aplatanao.billing.persistence.InvoicesPerMonthRepository;
import org.aplatanao.billing.rest.api.ReportsApi;
import org.aplatanao.billing.rest.model.InvoicesPerMonth;
import org.aplatanao.billing.rest.model.InvoicesPerMonthInner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReportsResource implements ReportsApi {

    private InvoicesPerMonthRepository perMonth;

    @Autowired
    public ReportsResource(InvoicesPerMonthRepository perMonth) {
        this.perMonth = perMonth;
    }

    @Override
    public InvoicesPerMonth getInvoicesPerMonth(Integer year) {
        InvoicesPerMonth invoices = new InvoicesPerMonth();
        invoices.addAll(perMonth.findByYear(year.shortValue())
                .stream()
                .map(m -> new InvoicesPerMonthInner()
                        .year(Math.toIntExact(m.getId().getYear()))
                        .month(Math.toIntExact(m.getId().getMonth()))
                        .count(Math.toIntExact(m.getCount()))
                        .amount(BigDecimal.valueOf(m.getCents())
                                .divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP))
                )
                .collect(Collectors.toList()));
        return invoices;
    }
}
