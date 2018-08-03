package org.aplatanao.billing.endpoint;

import org.aplatanao.billing.Converter;
import org.aplatanao.billing.persistence.PaymentRepository;
import org.aplatanao.billing.rest.api.PaymentsApi;
import org.aplatanao.billing.rest.model.Payments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentEndpoint implements PaymentsApi {

    private PaymentRepository payRepo;

    private Converter converter;

    @Autowired
    public PaymentEndpoint(PaymentRepository payRepo, Converter converter) {
        this.payRepo = payRepo;
        this.converter = converter;
    }

    @Override
    @Transactional
    public Payments getPayments(Integer page, Integer size) {
        return converter.toPayments(payRepo.findAllByOrderByDateAsc(PageRequest.of(page, size)));
    }
}
