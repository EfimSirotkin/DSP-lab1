package sample;

import javafx.scene.chart.*;
import java.util.ArrayList;


public class HistogramInitializer {
    public static XYChart.Series initializeHistogram(int[] sourceArray) {
        ArrayList<String> valuesList = new ArrayList<>(256);
        for(int i = 0; i < 256; i ++)
            valuesList.add(String.valueOf(i));

        XYChart.Series series = new XYChart.Series();
        for(int i = 0; i < sourceArray.length; ++i) {
            series.getData().add(new XYChart.Data<>(valuesList.get(i), sourceArray[i]));
        }
        return series;
    }
}
