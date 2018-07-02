package org.aplatanao.billing.api;

import org.aplatanao.billing.repositories.InvoiceRepository;
import org.aplatanao.billing.rest.definitions.Invoice;
import org.aplatanao.billing.rest.definitions.Invoices;
import org.aplatanao.billing.rest.paths.InvoiceApi;
import org.aplatanao.billing.rest.paths.InvoicesApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

@Service
public class InvoiceResource implements InvoicesApi, InvoiceApi {

    private InvoiceRepository invoices;

    @Autowired
    public InvoiceResource(InvoiceRepository invoices) {
        this.invoices = invoices;
    }

    private static org.aplatanao.billing.entities.Invoice POST(Invoice i) {
        org.aplatanao.billing.entities.Invoice invoice = new org.aplatanao.billing.entities.Invoice();
        invoice.setCode(i.getCode());
        invoice.setComment(i.getComment());
        invoice.setDate(i.getDate());
        return invoice;
    }

    private static Invoice GET(org.aplatanao.billing.entities.Invoice i) {
        return new Invoice()
                .id(i.getId())
                .date(i.getDate())
                .code(i.getCode())
                .comment(i.getComment());
    }

    private static Invoices LIST(Page<org.aplatanao.billing.entities.Invoice> p) {
        Invoices i = new Invoices();
        i.addAll(p.map(InvoiceResource::GET).getContent());
        return i;
    }

    @Override
    public Invoice getInvoiceById(Long id) throws Exception {
        org.aplatanao.billing.entities.Invoice invoice = invoices.getById(id);
        if (invoice == null) {
            throw new NotFoundException();
        }
        return InvoiceResource.GET(invoice);
    }

    @Override
    public Invoice addInvoice(@Valid Invoice body) throws Exception {
        return InvoiceResource.GET(invoices.save(InvoiceResource.POST(body)));
    }

    @Override
    public Invoices getInvoices(@NotNull @Min(0) Integer page, @NotNull Integer size) throws Exception {
        return InvoiceResource.LIST(invoices.findAll(PageRequest.of(page, size)));
    }

}
