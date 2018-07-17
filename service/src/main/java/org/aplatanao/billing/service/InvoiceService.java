package org.aplatanao.billing.service;

import org.aplatanao.billing.persistence.InvoicePositionTable;
import org.aplatanao.billing.persistence.InvoicePositionTableId;
import org.aplatanao.billing.persistence.InvoiceRepository;
import org.aplatanao.billing.persistence.InvoiceTable;
import org.aplatanao.billing.rest.api.InvoicesApi;
import org.aplatanao.billing.rest.model.Invoice;
import org.aplatanao.billing.rest.model.InvoicePosition;
import org.aplatanao.billing.rest.model.Invoices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.NotFoundException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class InvoiceService implements InvoicesApi {

    private InvoiceRepository invoices;

    @Autowired
    public InvoiceService(InvoiceRepository invoices) {
        this.invoices = invoices;
    }

    private static Set<InvoicePositionTable> POST(InvoiceTable invoice, List<InvoicePosition> positions) {
        return IntStream.range(0, positions.size())
                .mapToObj(i -> {
                    InvoicePosition p = positions.get(i);
                    InvoicePositionTableId id = new InvoicePositionTableId();
                    id.setInvoice(invoice.getUuid());
                    id.setNumber((short) i);

                    InvoicePositionTable table = new InvoicePositionTable();
                    table.setId(id);
                    table.setInvoiceTable(invoice);
                    table.setCents(p.getCents());
                    table.setDescription(p.getDescription());
                    return table;
                })
                .collect(Collectors.toSet());
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
                .uuid(i.getUuid().toString())
                .createdAt(i.getCreatedAt())
                .code(i.getCode())
                .date(i.getDate())
                .comment(i.getComment())
                .positions(i.getInvoicePositionTables()
                        .stream()
                        .sorted(Comparator.comparingInt(p -> p.getId().getNumber()))
                        .map(p -> new InvoicePosition()
                                .createdAt(p.getCreatedAt())
                                .description(p.getDescription())
                                .cents(p.getCents())
                        )
                        .collect(Collectors.toList()));
    }

    private static Invoices LIST(Page<InvoiceTable> p) {
        Invoices i = new Invoices();
        i.addAll(p.map(InvoiceService::GET).getContent());
        return i;
    }

    @Override
    @Transactional
    public Invoice addInvoice(Invoice body) {
        InvoiceTable i = invoices.save(POST(body));
        i.setInvoicePositionTables(POST(i, body.getPositions()));
        return GET(invoices.save(i));
    }

    @Override
    @Transactional(readOnly = true)
    public Invoice getInvoiceByUUID(String uuid) {
        Optional<InvoiceTable> optional = invoices.findById(UUID.fromString(uuid));
        if (optional.isPresent()) {
            return GET(optional.get());
        }
        throw new NotFoundException("Invoice " + uuid + " not found.");
    }

    @Override
    @Transactional(readOnly = true)
    public Invoices getInvoices(Integer page, Integer size) {
        return LIST(invoices.findAllByOrderByDateAsc(PageRequest.of(page, size)));
    }
}
