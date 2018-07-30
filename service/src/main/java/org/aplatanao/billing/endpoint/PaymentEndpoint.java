package org.aplatanao.billing.endpoint;

import org.aplatanao.billing.persistence.PaymentRepository;
import org.aplatanao.billing.persistence.PaymentTable;
import org.aplatanao.billing.rest.api.PaymentsApi;
import org.aplatanao.billing.rest.model.Payment;
import org.aplatanao.billing.rest.model.Payments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class PaymentEndpoint implements PaymentsApi {

    private PaymentRepository payRepo;

    @Autowired
    public PaymentEndpoint(PaymentRepository payRepo) {
        this.payRepo = payRepo;
    }

    @Override
    @Transactional
    public Payments getPayments(Integer page, Integer size) {

        PaymentTable value = new PaymentTable();
        value.setCode("code 123");
        value.setCents(5252L);
        value.setComment("comment");
        value.setDate(LocalDate.now());
        payRepo.save(value);

        Page<PaymentTable> payments = payRepo.findAllByOrderByDateAsc(PageRequest.of(page, size));
        return LIST(payments);
    }

    private Payments LIST(Page<PaymentTable> p) {
        Payments i = new Payments();
        i.addAll(p.map(PaymentEndpoint::GET).getContent());
        return i;
    }

    private static Payment GET(PaymentTable table) {
        return new Payment()
                .uuid(table.getUuid().toString())
                .code(table.getCode())
                .date(table.getDate())
                .comment(table.getComment())
                .cents(table.getCents());
    }
}
