package org.aplatanao.billing;

import org.aplatanao.billing.persistence.InvoicePositionTable;
import org.aplatanao.billing.persistence.InvoicePositionTableId;
import org.aplatanao.billing.persistence.InvoiceTable;
import org.aplatanao.billing.persistence.PaymentTable;
import org.aplatanao.billing.rest.model.*;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component("convert")
public class Converter {

    public LocalDate toLocalDate(Date date) {
        if (date == null) {
            return null;
        }
        Instant milli = Instant.ofEpochMilli(date.getTime());
        return LocalDateTime.ofInstant(milli, ZoneId.systemDefault()).toLocalDate();
    }

    public Date toDate(LocalDate date) {
        if (date == null) {
            return null;
        }
        return Date.from(date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public Set<InvoicePositionTable> toInvoicePositionTableSet(InvoiceTable invoice, List<InvoicePosition> positions) {
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

    public InvoiceTable toInvoiceTable(Invoice i) {
        InvoiceTable invoice = new InvoiceTable();
        invoice.setCode(i.getCode());
        invoice.setComment(i.getComment());
        invoice.setDate(i.getDate());
        return invoice;
    }

    public Invoice toInvoice(InvoiceTable i) {
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

    public Invoices toInvoices(Page<InvoiceTable> p) {
        Invoices i = new Invoices();
        i.addAll(p.map(this::toInvoice).getContent());
        return i;
    }

    public Payment toPayment(PaymentTable table) {
        return new Payment()
                .uuid(table.getUuid().toString())
                .code(table.getCode())
                .date(table.getDate())
                .comment(table.getComment())
                .cents(table.getCents());
    }

    public Payments toPayments(Page<PaymentTable> p) {
        Payments i = new Payments();
        i.addAll(p.map(this::toPayment).getContent());
        return i;
    }
}
