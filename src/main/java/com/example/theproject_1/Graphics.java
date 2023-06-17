package com.example.theproject_1;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;


public class Graphics extends Application {

    private static int WINDOW_SIZE = 30;
    private ScheduledExecutorService scheduledExecutorService;

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("The Battle Of Sexes");

        //defining the axis
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();

        xAxis.setLabel("Year");
        xAxis.setAnimated(true);
        yAxis.setLabel("Population Members");
        yAxis.setAnimated(true);

        final CategoryAxis xAxis2 = new CategoryAxis();
        final NumberAxis yAxis2 = new NumberAxis();

        xAxis2.setLabel("Year");
        xAxis2.setAnimated(true);
        yAxis2.setLabel("Population");
        yAxis2.setAnimated(true);

        //defining the Chart Lines
        final LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        final LineChart<String, Number> lineChart2 = new LineChart<>(xAxis2, yAxis2);
        lineChart.setTitle("             Population Members Growing");
        lineChart2.setTitle("Population Growing (last 30 years)");

        lineChart.setAnimated(true);
        lineChart2.setAnimated(true);

        //defining the series to display
        XYChart.Series<String, Number> series_P = new XYChart.Series<>();
        XYChart.Series<String, Number> series_C = new XYChart.Series<>();
        XYChart.Series<String, Number> series_S = new XYChart.Series<>();
        XYChart.Series<String, Number> series_F = new XYChart.Series<>();

        XYChart.Series<String, Number> population = new XYChart.Series<>();
        XYChart.Series<String, Number> deaths = new XYChart.Series<>();


        //assigning names to the series
        series_P.setName("Philanderer                             ");
        series_C.setName("Coy");
        series_S.setName("Fast");
        series_F.setName("Faithful");

        population.setName("Population");
        deaths.setName("Deaths");


        // add series to the charts
        lineChart.getData().addAll(series_P,series_C,series_S,series_F);
        lineChart2.getData().addAll(population,deaths);


        // set up a scheduled executor to periodically put data into the chart
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(() -> {


            // Update the chart
            Platform.runLater(() -> {

                if (population.getData().size() > WINDOW_SIZE)
                    population.getData().remove(0);
                if (deaths.getData().size() > WINDOW_SIZE)
                    deaths.getData().remove(0);

            });
        }, 0, 1, TimeUnit.SECONDS);


        //creating the pie chart
        ObservableList<PieChart.Data> pieChartData
                = FXCollections.observableArrayList(
                new PieChart.Data("Philanderer   ", 0),
                new PieChart.Data("Coy", 0),
                new PieChart.Data("Fast", 0),
                new PieChart.Data("Faithful", 0)
        );
        final PieChart pieChart2 = new PieChart(pieChartData);
        pieChart2.setAnimated(false);

        //creating the displayed page
        VBox vBox = new VBox();
        HBox hbox = new HBox();
        Label counters = new Label();
        counters.setFont(Font.font("Copperplate",30));
        hbox.getChildren().addAll(lineChart2,pieChart2,counters);
        vBox.getChildren().addAll(hbox,lineChart);

        //attaching the vbox on the root
        StackPane root = new StackPane();
        root.getChildren().add(vBox);

        Scene scene = new Scene(root, 1500, 800);
        primaryStage.setTitle("The Battle of Sexes");
        primaryStage.setScene(scene);
        primaryStage.show();

        // updating data
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.millis(1000), (ActionEvent actionEvent) -> {
                    pieChartData.set(0, new PieChart.Data("Philanderer                 ", Population.counter_P));
                    pieChartData.set(1, new PieChart.Data("Coy", Population.counter_C));
                    pieChartData.set(2, new PieChart.Data("Fast", Population.counter_S));
                    pieChartData.set(3, new PieChart.Data("Faithful", Population.counter_F));

                    series_P.getData().add(new XYChart.Data<>(""+Population.Current_year,Population.counter_P));
                    series_C.getData().add(new XYChart.Data<>(""+Population.Current_year,Population.counter_C));
                    series_S.getData().add(new XYChart.Data<>(""+Population.Current_year,Population.counter_S));
                    series_F.getData().add(new XYChart.Data<>(""+Population.Current_year,Population.counter_F));

                    population.getData().add(new XYChart.Data<>(""+Population.Current_year,Population.counter_tot));
                    deaths.getData().add(new XYChart.Data<>(""+Population.Current_year,Population.counter_deaths));
                    String s = "     ";
                    counters.setText("\n\n"+s+"Year: "+Population.Current_year+"\n\n"+s+"Population: " + Population.counter_tot +"\n"+s+"Deaths: "+ Population.counter_deaths+"\n"+s+"Men: "+Population.counter_M+"\n"+s+"Women: "+Population.counter_W+"\n"+s+"Children: "+Population.children_list.size() );
                }));

        timeline.setCycleCount(1000);
        timeline.play();
    }

    public static void main(String[] args) {
        Pop_Controller pop1 = new Pop_Controller();
        pop1.start();
        launch(args);

    }
}