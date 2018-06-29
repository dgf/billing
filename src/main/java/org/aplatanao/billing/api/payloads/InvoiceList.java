package org.aplatanao.billing.api.payloads;

import java.util.ArrayList;
import java.util.Collection;

public class InvoiceList {

    private Collection<InvoiceResponse> invoices = new ArrayList<>();

    public Collection<InvoiceResponse> getInvoices() {
        return invoices;
    }

    public void setInvoices(Collection<InvoiceResponse> invoices) {
        this.invoices = invoices;
    }
}
