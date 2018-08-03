package org.aplatanao.billing.desktop;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import org.aplatanao.billing.rest.client.api.DefaultApi;
import org.aplatanao.billing.rest.client.model.Invoice;
import org.aplatanao.billing.rest.client.model.Invoices;

public class Controller {

    @FXML
    private TextArea taskResponse;

    @FXML
    private TableView<Invoice> invoicesTable;

    @FXML
    private TableColumn<Invoice, String> codeCol;

    @FXML
    private TableColumn<Invoice, String> dateCol;

    @FXML
    private TableColumn<Invoice, String> commentCol;


    private ObservableList<Invoice> invoices = FXCollections.observableArrayList();

    @FXML
    private void initialize() {

        codeCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getCode()));
        dateCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getDate().toString()));
        commentCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getComment()));

        invoicesTable.setItems(invoices);
    }

    @FXML
    private void connect(ActionEvent event) {
        DefaultApi api = new DefaultApi();
        taskResponse.setText(api.getTasks().toString());

        invoices.clear();
        int page = 0, size = 20, year = 2018;
        Invoices data = api.getInvoices(page, size, year);

        this.invoices.addAll(data);
    }
}
