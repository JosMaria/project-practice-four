package org.genesiscode.projectpraticefour.view;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.genesiscode.projectpraticefour.service.OptionComboBox;
import org.genesiscode.projectpraticefour.service.Service;

import java.util.List;

import static org.genesiscode.projectpraticefour.service.OptionComboBox.*;

public class MainPane {

    private static MainPane mainPane;
    private final Service service;
    private VBox pane;
    private TableView<RowTable> table;
    private Label lblRetailPrice, lblCostOfGoods, lblReader, lblTime, lblPeople, lblNational;
    private Label lblTotalGrossProfit, lblValueTotalGrossProfit;
    private TextField fieldRetailPriceReader, fieldRetailPriceTime, fieldRetailPricePeople, fieldRetailPriceNational,
            fieldCostOfGoodsReader, fieldCostOfGoodsTime, fieldCostOfGoodsPeople, fieldCostOfGoodsNational;
    private Button btnLoad, btnStart;
    private ComboBox<OptionComboBox> comboBox;
    private Group group;
    private BarChart<String, Number> barChart;

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

    private void buildGraphicBar() {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setCategories(FXCollections.observableArrayList(List.of("Magazine")));
        xAxis.setLabel("Revistas que se venden");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Cantidad");

        barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Volumen de ventas");
        barChart.setBarGap(30);
        barChart.setAnimated(true);
        barChart.getData().addAll(loadDataGraphic());
        group = new Group(barChart);
    }

    private void loadControls() {
        buildPaneInput();
        buildTable();
        btnLoad = new Button("Cargar");
        btnLoad.setOnAction(actionEvent -> btn_click_load());
        btnStart = new Button("Comenzar");
        btnStart.setOnAction(actionEvent -> btn_click_start());

        comboBox = new ComboBox<>(FXCollections.observableArrayList(List.of(FIRST, ANY, AVERAGE)));
        comboBox.setValue(FIRST);
        comboBox.setOnAction(this::combo_box_click);

        buildGraphicBar();
    }

    public void btn_click_start() {
        service.start();
        lblValueTotalGrossProfit.setText(String.valueOf(service.getTotalGrossProfit()));
        barChart.setData(FXCollections.observableList(loadDataGraphic()));
        table.refresh();
    }

    private List<XYChart.Series<String, Number>> loadDataGraphic() {
        //Prepare XYChart.Series objects by setting data
        XYChart.Series<String, Number> seriesReader = new XYChart.Series<>();
        seriesReader.setName("READER");
        seriesReader.getData().add(new XYChart.Data<>("Magazine", service.getSalesVolumeReader()));
        // 562 362 646 508
        XYChart.Series<String, Number> seriesTime = new XYChart.Series<>();
        seriesTime.setName("TIME");
        seriesTime.getData().add(new XYChart.Data<>("Magazine", service.getSalesVolumeTime()));

        XYChart.Series<String, Number> seriesPeople = new XYChart.Series<>();
        seriesPeople.setName("PEOPLE");
        seriesPeople.getData().add(new XYChart.Data<>("Magazine", service.getSalesVolumePeople()));

        XYChart.Series<String, Number> seriesNational = new XYChart.Series<>();
        seriesNational.setName("NATIONAL");
        seriesNational.getData().add(new XYChart.Data<>("Magazine", service.getSalesVolumeNational()));

        return List.of(seriesReader, seriesTime, seriesPeople, seriesNational);
    }

    private void combo_box_click(ActionEvent actionEvent) {
        @SuppressWarnings("unchecked")
        ComboBox<OptionComboBox> source = (ComboBox<OptionComboBox>) actionEvent.getSource();
        service.changeOption(source.getValue());
        table.refresh();
        lblValueTotalGrossProfit.setText(String.valueOf(service.getTotalGrossProfit()));
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
            table.refresh();
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }
    }

    private void buildPane() {
        Label title = new Label("PROYECTO - PRACTICA 4");
        title.setFont(new Font("Arial", 25));

        // build input pane
        HBox titlesPane = new HBox(30, lblReader, lblTime, lblPeople, lblNational);
        titlesPane.setAlignment(Pos.CENTER_RIGHT);
        HBox retailPricePane = new HBox(10, lblRetailPrice, fieldRetailPriceReader, fieldRetailPriceTime, fieldRetailPricePeople, fieldRetailPriceNational);
        retailPricePane.setAlignment(Pos.CENTER_RIGHT);
        HBox costOfGoodsPane = new HBox(10, lblCostOfGoods, fieldCostOfGoodsReader, fieldCostOfGoodsTime, fieldCostOfGoodsPeople, fieldCostOfGoodsNational);
        costOfGoodsPane.setAlignment(Pos.CENTER_RIGHT);
        HBox buttonsPane = new HBox(20, btnLoad, btnStart);
        buttonsPane.setAlignment(Pos.CENTER_RIGHT);
        buttonsPane.setPadding(new Insets(10));
        VBox inputPane = new VBox(10, titlesPane, retailPricePane, costOfGoodsPane, buttonsPane);
        inputPane.setPadding(new Insets(20));
        inputPane.setAlignment(Pos.CENTER_RIGHT);

        HBox resultsPane = new HBox(10, lblTotalGrossProfit, lblValueTotalGrossProfit);
        VBox tablePane = new VBox(10, table, resultsPane);

        HBox topPane = new HBox(20, inputPane, comboBox, tablePane);
        topPane.setAlignment(Pos.CENTER_RIGHT);
        topPane.setPadding(new Insets(20));

        pane = new VBox(10, title, topPane, group);
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(10, 30, 30, 30));
    }

    private void buildTable() {
        table = new TableView<>();
        table.getColumns().addAll(List.of(
                column("", "value", 110),
                column("Reader's\nDigest", "readerMagazine", 90),
                column("Time", "timeMagazine", 85),
                column("People", "peopleMagazine", 85),
                column("National\nGeographic", "nationalMagazine", 90)
        ));
        table.setItems(service.getObservableList());
        table.setEditable(true);
        table.setMaxHeight(140);
        table.setMinWidth(470);
    }

    public void buildPaneInput() {
        lblReader = new Label("Reader's\nDigest");
        lblTime = new Label("Time");
        lblPeople =  new Label("People");
        lblNational = new Label("National\nGeographic");

        lblRetailPrice = new Label("Retail Price");
        lblCostOfGoods = new Label("Cost of Goods");
        lblTotalGrossProfit = new Label("Beneficio Total bruto");
        lblValueTotalGrossProfit = new Label("XXX");

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
