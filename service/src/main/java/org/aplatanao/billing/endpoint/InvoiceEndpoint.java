package org.aplatanao.billing.endpoint;

import org.aplatanao.billing.Converter;
import org.aplatanao.billing.persistence.InvoiceRepository;
import org.aplatanao.billing.persistence.InvoiceTable;
import org.aplatanao.billing.process.Engine;
import org.aplatanao.billing.rest.api.InvoicesApi;
import org.aplatanao.billing.rest.model.*;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import javax.ws.rs.NotFoundException;
import java.util.Optional;
import java.util.UUID;

@Service
public class InvoiceEndpoint implements InvoicesApi {

    private InvoiceRepository invoices;

    private Engine engine;

    private Converter converter;

    @Autowired
    public InvoiceEndpoint(InvoiceRepository invoices, Engine engine, Converter converter) {
        this.invoices = invoices;
        this.engine = engine;
        this.converter = converter;
    }

    @Override
    public TaskReference addInvoice(Invoice body) {
        Task task = engine.getTask(engine.start("newInvoiceMessage", "invoice", body));
        return new TaskReference()
                .uuid(task.getId())
                .dueDate(converter.toLocalDate(task.getDueDate()))
                .description(task.getDescription());
    }

    @Override
    public InvoiceReference approveInvoice(@Valid InvoiceApproval body) {
        String uuid = body.getUuid(); // TODO validate UUID

        // query and complete the approval task
        Task task = engine.getTask(uuid);
        if (task == null) {
            throw new NotFoundException("Invoice approval " + uuid + " not found.");
        }
        engine.complete(task, "invoiceApproved", body.isApproved());

        // fetch invoice and return reference
        Invoice invoice = engine.getHistory(Invoice.class, task.getProcessInstanceId(), "invoice");
        if (invoice == null) {
            return null; // 204
        }
        return new InvoiceReference()
                .uuid(invoice.getUuid())
                .code(invoice.getCode())
                .date(invoice.getDate());
    }

    @Override
    @Transactional(readOnly = true)
    public Invoice getInvoiceByUUID(String uuid) {
        Optional<InvoiceTable> optional = invoices.findById(UUID.fromString(uuid));
        if (optional.isPresent()) {
            return converter.toInvoice(optional.get());
        }
        throw new NotFoundException("Invoice " + uuid + " not found.");
    }

    @Override
    @Transactional(readOnly = true)
    public Invoices getInvoices(Integer page, Integer size, Integer year) {
        PageRequest pr = PageRequest.of(page, size);
        if (year == null) {
            return converter.toInvoices(invoices.findAllByOrderByDateAsc(pr));
        }
        return converter.toInvoices(invoices.findAllByYear(year.shortValue(), pr));
    }
}
