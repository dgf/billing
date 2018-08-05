package org.aplatanao.billing.graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.aplatanao.billing.Converter;
import org.aplatanao.billing.persistence.InvoiceRepository;
import org.aplatanao.billing.rest.model.Invoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.UUID;

@Component
public class Query implements GraphQLQueryResolver {

    private final InvoiceRepository invoices;

    private final Converter converter;

    @Autowired
    public Query(InvoiceRepository invoices, Converter converter) {
        this.invoices = invoices;
        this.converter = converter;
    }

    @Transactional(readOnly = true)
    public Collection<Invoice> invoices(Integer year, Integer page, Integer size) {
        PageRequest pr = PageRequest.of(page, size);
        if (year == null) {
            return converter.toInvoices(invoices.findAllByOrderByDateAsc(pr));
        }
        return converter.toInvoices(invoices.findAllByYear(year.shortValue(), pr));
    }

    @Transactional(readOnly = true)
    public Invoice invoice(String uuid) {
        return invoices.findById(UUID.fromString(uuid))
                .map(converter::toInvoice).orElse(null);
    }
}
