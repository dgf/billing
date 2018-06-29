package org.aplatanao.billing.api;

import org.aplatanao.billing.api.payloads.InvoiceList;
import org.aplatanao.billing.api.payloads.InvoiceRequest;
import org.aplatanao.billing.api.payloads.InvoiceResponse;
import org.aplatanao.billing.domain.Invoice;
import org.aplatanao.billing.repositories.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;

@Service
@Path("/invoice")
@Consumes("application/json")
@Produces("application/json")
public class InvoiceResource {

    private InvoiceRepository invoices;

    @Autowired
    public InvoiceResource(InvoiceRepository invoices) {
        this.invoices = invoices;
    }

    @GET
    public InvoiceList list() {
        InvoiceList l = new InvoiceList();
        for (Invoice i : invoices.findAll()) {
            InvoiceResponse ip = new InvoiceResponse();
            ip.setId(i.getId());
            ip.setName(i.getName());
            l.getInvoices().add(ip);
        }
        return l;
    }

    @POST
    public Response create(InvoiceRequest request) throws URISyntaxException {
        Invoice invoice = new Invoice();
        invoice.setName(request.getName());
        Invoice saved = invoices.save(invoice);
        String url = "http://localhost:8080/invoice/" + saved.getId();
        return Response.created(new URI(url)).build();
    }
}
