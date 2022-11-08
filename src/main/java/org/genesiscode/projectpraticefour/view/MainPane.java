package org.genesiscode.projectpraticefour.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.genesiscode.projectpraticefour.service.Information;
import org.genesiscode.projectpraticefour.service.Service;

import java.util.List;

public class MainPane {

    private static MainPane mainPane;
    private Service service;
    private VBox pane;
    private TableView<RowTable> table;

    private MainPane() {
        service = new Service();
        loadControls();
        buildPane();
    }

    public synchronized static MainPane getInstance() {
        return mainPane != null ? mainPane : new MainPane();
    }

    public VBox getPane() {
        return pane;
    }

    private void loadControls() {
        buildTable();
    }

    private void buildPane() {
        Label title = new Label("DULCE  ADA");
        title.setFont(new Font("Arial", 25));
        pane = new VBox(10, title, table);
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(10, 30, 30, 30));
    }

    private void buildTable() {
        table = new TableView<>();
        table.getColumns().addAll(List.of(
                column("", "value", 120),
                column("Reader's Digest", "readerMagazine", 100),
                column("Time", "timeMagazine", 100),
                column("People", "peopleMagazine", 100),
                column("National geographic", "nationalMagazine", 150)
        ));
        table.setItems(service.getObservableList());
    }

    private <U, T> TableColumn<U, T> column(String titleColumn, String property, double prefSize) {
        TableColumn<U, T> column = new TableColumn<>(titleColumn);
        column.setCellValueFactory(new PropertyValueFactory<>(property));
        column.setPrefWidth(prefSize);
        return column;
    }
}
