package org.aplatanao.billing.service;

import org.aplatanao.billing.persistence.*;
import org.aplatanao.billing.rest.api.InvoicesApi;
import org.aplatanao.billing.rest.model.Invoice;
import org.aplatanao.billing.rest.model.InvoicePosition;
import org.aplatanao.billing.rest.model.Invoices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.ws.rs.NotFoundException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class InvoiceResource implements InvoicesApi {

    private InvoiceRepository invoices;

    @Autowired
    public InvoiceResource(InvoiceRepository invoices) {
        this.invoices = invoices;
    }

    private static Set<InvoicePositionTable> POST(InvoiceTable invoice, List<InvoicePosition> positions) {
        return positions.stream()
                .map(p -> {
                    InvoicePositionTableId id = new InvoicePositionTableId();
                    id.setInvoiceCode(invoice.getCode());
                    id.setNumber(p.getNumber().shortValue());

                    InvoicePositionTable table = new InvoicePositionTable();
                    table.setId(id);
                    table.setInvoiceTable(invoice);
                    table.setCents(p.getAmount().multiply(BigDecimal.valueOf(100L)).longValueExact());
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
        invoice.setInvoicePositionTables(POST(invoice, i.getPositions()));
        return invoice;
    }

    private static Invoice GET(InvoiceTable i) {
        //throw new ArithmeticException("integer overflow");
        return new Invoice()
                .code(i.getCode())
                .date(i.getDate())
                .comment(i.getComment())
                .positions(i.getInvoicePositionTables()
                        .stream()
                        .map(p -> new InvoicePosition()
                                .number(Math.toIntExact(p.getId().getNumber()))
                                .amount(BigDecimal.valueOf(p.getCents())
                                        .divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP))
                                .description(p.getDescription()))
                        .collect(Collectors.toList()));
    }

    private static Invoices LIST(Page<InvoiceTable> p) {
        Invoices i = new Invoices();
        i.addAll(p.map(InvoiceResource::GET).getContent());
        return i;
    }

    @Override
    public Invoice getInvoiceByCode(String code) {
        Optional<InvoiceTable> optional = invoices.findById(code);
        if (optional.isPresent()) {
            return GET(optional.get());
        }
        throw new NotFoundException("Invoice " + code + " not found.");
    }

    @Override
    public Invoice addInvoice(@Valid Invoice body) {
        return GET(invoices.save(POST(body)));
    }

    @Override
    public Invoices getInvoices(@NotNull @Min(0) Integer page, @NotNull Integer size) {
        return LIST(invoices.findAll(PageRequest.of(page, size)));
    }
}
