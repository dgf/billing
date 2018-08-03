package org.aplatanao.billing.process.delegate;

import org.aplatanao.billing.Converter;
import org.aplatanao.billing.persistence.InvoiceRepository;
import org.aplatanao.billing.persistence.InvoiceTable;
import org.aplatanao.billing.rest.model.Invoice;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("saveInvoice")
public class SaveInvoice implements JavaDelegate {

    private InvoiceRepository invoices;
    private Converter converter;

    public SaveInvoice(InvoiceRepository invoices, Converter converter) {
        this.invoices = invoices;
        this.converter = converter;
    }

    @Override
    @Transactional
    public void execute(DelegateExecution delegateExecution) throws Exception {
        // get invoice payload from the process execution
        Invoice invoice = (Invoice) delegateExecution.getVariable("invoice");

        // map the invoice and save in the repository
        InvoiceTable saved = invoices.save(converter.toInvoiceTable(invoice));

        // add positions and save it again
        saved.setInvoicePositionTables(converter.toInvoicePositionTableSet(saved, invoice.getPositions()));
        InvoiceTable updated = invoices.save(saved);

        // update process variable
        delegateExecution.setVariable("invoice",
                Variables.objectValue(converter.toInvoice(updated)).create());
    }
}
