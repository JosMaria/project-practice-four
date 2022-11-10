package org.genesiscode.projectpraticefour.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.genesiscode.projectpraticefour.service.Service;

import java.util.List;

public class MainPane {

    private static MainPane mainPane;
    private final Service service;
    private VBox pane;
    private TableView<RowTable> table;
    private Label lblRetailPrice, lblCostOfGoods, lblReader, lblTime, lblPeople, lblNational;
    private TextField fieldRetailPriceReader, fieldRetailPriceTime, fieldRetailPricePeople, fieldRetailPriceNational,
            fieldCostOfGoodsReader, fieldCostOfGoodsTime, fieldCostOfGoodsPeople, fieldCostOfGoodsNational;
    private Button btnLoad;

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
        buildPaneInput();
        buildTable();
        btnLoad = new Button("Cargar");
        btnLoad.setOnAction(actionEvent -> btn_click_load());
    }

    private void btn_click_load() {
        try {
            double retailPriceReaderMagazine = Double.parseDouble(fieldRetailPriceReader.getText());
            double retailPriceTimeMagazine = Double.parseDouble(fieldRetailPriceTime.getText());
            double retailPricePeopleMagazine = Double.parseDouble(fieldRetailPricePeople.getText());
            double retailPriceNationalMagazine = Double.parseDouble(fieldRetailPriceNational.getText());

            double costOfGoodsReaderMagazine = Double.parseDouble(fieldCostOfGoodsReader.getText());
            double costOfGoodsTimeMagazine = Double.parseDouble(fieldCostOfGoodsTime.getText());
            double costOfGoodsPeopleMagazine = Double.parseDouble(fieldCostOfGoodsPeople.getText());
            double costOfGoodsNationalMagazine = Double.parseDouble(fieldCostOfGoodsNational.getText());

            service.loadValues(retailPriceReaderMagazine, retailPriceTimeMagazine, retailPricePeopleMagazine, retailPriceNationalMagazine,
                    costOfGoodsReaderMagazine, costOfGoodsTimeMagazine, costOfGoodsPeopleMagazine, costOfGoodsNationalMagazine);

        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }
    }

    private void buildPane() {
        Label title = new Label("DULCE  ADA");
        title.setFont(new Font("Arial", 25));

        // build input pane
        HBox titlesPane = new HBox(30, lblReader, lblTime, lblPeople, lblNational);
        titlesPane.setAlignment(Pos.CENTER_RIGHT);
        HBox retailPricePane = new HBox(10, lblRetailPrice, fieldRetailPriceReader, fieldRetailPriceTime, fieldRetailPricePeople, fieldRetailPriceNational);
        retailPricePane.setAlignment(Pos.CENTER_RIGHT);
        HBox costOfGoodsPane = new HBox(10, lblCostOfGoods, fieldCostOfGoodsReader, fieldCostOfGoodsTime, fieldCostOfGoodsPeople, fieldCostOfGoodsNational);
        costOfGoodsPane.setAlignment(Pos.CENTER_RIGHT);
        VBox inputPane = new VBox(10, titlesPane, retailPricePane, costOfGoodsPane, btnLoad);
        inputPane.setPadding(new Insets(20));
        inputPane.setAlignment(Pos.CENTER_RIGHT);

        pane = new VBox(10, title, inputPane, table);
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

    public void buildPaneInput() {
        lblReader = new Label("Reader's\nDigest");
        lblTime = new Label("Time");
        lblPeople =  new Label("People");
        lblNational = new Label("National\nGeographic");

        lblRetailPrice = new Label("Retail Price");
        lblCostOfGoods = new Label("Cost of Goods");

        fieldRetailPriceReader = new TextField();
        fieldCostOfGoodsReader = new TextField();
        fieldRetailPriceTime = new TextField();
        fieldCostOfGoodsTime = new TextField();
        fieldRetailPricePeople = new TextField();
        fieldCostOfGoodsPeople = new TextField();
        fieldRetailPriceNational = new TextField();
        fieldCostOfGoodsNational = new TextField();

        List<TextField> fields = List.of(
                fieldRetailPriceReader, fieldCostOfGoodsReader,
                fieldRetailPriceTime, fieldCostOfGoodsTime,
                fieldRetailPricePeople, fieldCostOfGoodsPeople,
                fieldRetailPriceNational, fieldCostOfGoodsNational);

        for (TextField field: fields) {
            field.setPrefColumnCount(4);
        }
    }

    private <U, T> TableColumn<U, T> column(String titleColumn, String property, double prefSize) {
        TableColumn<U, T> column = new TableColumn<>(titleColumn);
        column.setCellValueFactory(new PropertyValueFactory<>(property));
        column.setPrefWidth(prefSize);
        return column;
    }
}
