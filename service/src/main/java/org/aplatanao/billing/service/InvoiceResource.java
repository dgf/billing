package org.aplatanao.billing.service;

import org.aplatanao.billing.persistence.InvoiceRepository;
import org.aplatanao.billing.persistence.InvoiceTable;
import org.aplatanao.billing.persistence.InvoicesPerMonthRepository;
import org.aplatanao.billing.persistence.InvoicesPerMonthResource;
import org.aplatanao.billing.rest.api.InvoicesApi;
import org.aplatanao.billing.rest.model.Invoice;
import org.aplatanao.billing.rest.model.Invoices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

@Service
public class InvoiceResource implements InvoicesApi {

    private InvoiceRepository invoices;

    private InvoicesPerMonthRepository perMonth;

    @Autowired
    public InvoiceResource(InvoiceRepository invoices, InvoicesPerMonthRepository perMonth) {
        this.invoices = invoices;
        this.perMonth = perMonth;
    }

    private static InvoiceTable POST(Invoice i) {
        InvoiceTable invoice = new InvoiceTable();
        invoice.setCode(i.getCode());
        invoice.setComment(i.getComment());
        invoice.setDate(i.getDate());
        return invoice;
    }

    private static Invoice GET(InvoiceTable i) {
        return new Invoice()
                .id(i.getId())
                .date(i.getDate())
                .code(i.getCode())
                .comment(i.getComment());
    }

    private static Invoices LIST(Page<InvoiceTable> p) {
        Invoices i = new Invoices();
        i.addAll(p.map(InvoiceResource::GET).getContent());
        return i;
    }

    @Override
    public Invoice getInvoiceById(Long id) {
        InvoiceTable invoice = invoices.getById(id);
        if (invoice == null) {
            throw new NotFoundException("Invoice " + id + " not found.");
        }
        return GET(invoice);
    }

    @Override
    public Invoice addInvoice(@Valid Invoice body) {
        return GET(invoices.save(POST(body)));
    }

    @Override
    public Invoices getInvoices(@NotNull @Min(0) Integer page, @NotNull Integer size) {
        return LIST(invoices.findAll(PageRequest.of(page, size)));
    }

    @GET
    @Path("/by-month")
    @Produces({"application/json"})
    public List<InvoicesPerMonthResource> getByMonth() {
        return perMonth.findByYear(2018L);
    }
}
