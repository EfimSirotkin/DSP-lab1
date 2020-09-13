package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.*;
import java.lang.String;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private BarChart<String, Number> pixelDistributionHistogram;
    @FXML
    private BarChart<String, Number> pixelFirstFilterDistributionHistogram;
    @FXML
    private BarChart<String, Number> pixelSecondFilterDistributionHistogram;
    @FXML
    private BarChart<String, Number> pixelMinFilterDistributionHistogram;
    @FXML
    private BarChart<String, Number> pixelMaxDistributionHistogram;
    @FXML
    private BarChart<String, Number> pixelMinMaxFilterDistributionHistogram;


    @FXML
    private NumberAxis primaryImageYAxis;
    @FXML
    private CategoryAxis primaryImageXAxis;
    @FXML
    private NumberAxis firstFilterImageYAxis;
    @FXML
    private CategoryAxis firstFilterImageXAxis;
    @FXML
    private NumberAxis secondFilterImageYAxis;
    @FXML
    private CategoryAxis secondFilterImageXAxis;
    @FXML
    private NumberAxis minFilterImageYAxis;
    @FXML
    private CategoryAxis minFilterImageXAxis;
    @FXML
    private NumberAxis maxFilterImageYAxis;
    @FXML
    private CategoryAxis maxFilterImageXAxis;
    @FXML
    private NumberAxis minMaxFilterImageYAxis;
    @FXML
    private CategoryAxis minMaxFilterImageXAxis;

    @FXML
    private ImageView primaryImageView;
    @FXML
    private ImageView firstImageView;
    @FXML
    private ImageView secondImageView;
    @FXML
    private ImageView minImageView;
    @FXML
    private ImageView maxImageView;
    @FXML
    private ImageView minMaxImageView;

  /*  @FXML
    private TextField gminTextField;
    @FXML
    private TextField gmaxTextField;
    @FXML
    private TextField fminTextField;
    @FXML
    private TextField fmaxTextField;*/


    public String primaryImagePath ="F:\\Code\\labsIIPY\\Cosi_lab1\\res\\landscape.jpg";
    public String firstPreparationFilterImagePath = "F:\\Code\\labsIIPY\\Cosi_lab1\\res\\landscape2.jpg";
    public String secondPreparationFilterImagePath ="F:\\Code\\labsIIPY\\Cosi_lab1\\res\\landscape3.jpg";
    public String minFilteredPath = "F:\\Code\\labsIIPY\\Cosi_lab1\\res\\landscape_min.jpg";
    public String maxFilteredPath = "F:\\Code\\labsIIPY\\Cosi_lab1\\res\\landscape_max.jpg";
    public String min_maxFilteredPath = "F:\\Code\\labsIIPY\\Cosi_lab1\\res\\landscape_min_max.jpg";

    public int windowSize = 3;
    public int histogramWidth = 750;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {

            ImageProcessor.firstImageFilterPreparation(primaryImagePath, firstPreparationFilterImagePath);
            ImageProcessor.secondImageFilterPreparation(primaryImagePath, secondPreparationFilterImagePath);
            ImageProcessor.MinOrMaxFilterImage(primaryImagePath, minFilteredPath,windowSize, false);
            ImageProcessor.MinOrMaxFilterImage(primaryImagePath, maxFilteredPath,windowSize, true);
            ImageProcessor.minMaxImageFilter(primaryImagePath,min_maxFilteredPath, windowSize);


            pixelDistributionHistogram.getData().add(ImageProcessor.getDistributionHistogramSeries(primaryImagePath));
            pixelFirstFilterDistributionHistogram.getData().add(ImageProcessor.getDistributionHistogramSeries(firstPreparationFilterImagePath));
            pixelSecondFilterDistributionHistogram.getData().add(ImageProcessor.getDistributionHistogramSeries(secondPreparationFilterImagePath));
            pixelMinFilterDistributionHistogram.getData().add(ImageProcessor.getDistributionHistogramSeries(minFilteredPath));
            pixelMaxDistributionHistogram.getData().add(ImageProcessor.getDistributionHistogramSeries(maxFilteredPath));
            pixelMinMaxFilterDistributionHistogram.getData().add(ImageProcessor.getDistributionHistogramSeries(min_maxFilteredPath));

            pixelDistributionHistogram.setMinWidth(histogramWidth);
            pixelFirstFilterDistributionHistogram.setMinWidth(histogramWidth);
            pixelSecondFilterDistributionHistogram.setMinWidth(histogramWidth);
            pixelMinFilterDistributionHistogram.setMinWidth(histogramWidth);
            pixelMaxDistributionHistogram.setMinWidth(histogramWidth);
            pixelMinMaxFilterDistributionHistogram.setMinWidth(histogramWidth);


        } catch (IOException e) {
            e.printStackTrace();
        }

        File file = new File(primaryImagePath);
        Image image = new Image(file.toURI().toString());
        primaryImageView.setImage(image);

        file = new File(firstPreparationFilterImagePath);
        image = new Image(file.toURI().toString());
        firstImageView.setImage(image);

        file = new File(secondPreparationFilterImagePath);
        image = new Image(file.toURI().toString());
        secondImageView.setImage(image);

        file = new File(minFilteredPath);
        image = new Image(file.toURI().toString());
        minImageView.setImage(image);

        file = new File(maxFilteredPath);
        image = new Image(file.toURI().toString());
        maxImageView.setImage(image);

        file = new File(min_maxFilteredPath);
        image = new Image(file.toURI().toString());
        minMaxImageView.setImage(image);

    }
}
