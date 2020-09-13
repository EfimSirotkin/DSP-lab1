package sample;


import javafx.scene.chart.XYChart;
import javafx.scene.image.Image;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageProcessor {

    public static final int MAXIMUM_PIXEL_VALUE = 255;

    public static XYChart.Series getDistributionHistogramSeries(String imageFilePath) throws IOException {

        File sourceFile = new File(imageFilePath);
        BufferedImage sourceImage = ImageIO.read(sourceFile);

        int imageWidth = sourceImage.getWidth();
        int imageHeight = sourceImage.getHeight();

        int numberOfElements = MAXIMUM_PIXEL_VALUE + 1;
        int[] generalPixelDistributionArray = new int[numberOfElements];

        Color pixelColor;

        for (int i = 0; i < imageHeight; ++i)
            for (int j = 0; j < imageWidth; ++j) {
                pixelColor = new Color(sourceImage.getRGB(j, i));
                int red = pixelColor.getRed();
                int green = pixelColor.getGreen();
                int blue = pixelColor.getBlue();
                int generalPixel = (int) (0.3 * red + 0.59 * green + 0.11 * blue);

                generalPixelDistributionArray[generalPixel]++;
            }
        return HistogramInitializer.initializeHistogram(generalPixelDistributionArray);
    }

    public static Image getImage(String imageFilePath) throws IOException {

        InputStream stream = new FileInputStream(imageFilePath);
        Image image = new Image(stream);
        return image;
    }

    public static void firstImageFilterPreparation(String inputFilePath, String destinationFilePath) throws IOException
    {
        File sourceFile = new File(inputFilePath);
        BufferedImage sourceImage = ImageIO.read(sourceFile);
        BufferedImage destinationImage = ImageIO.read(sourceFile);


        int imageWidth = sourceImage.getWidth();
        int imageHeight = sourceImage.getHeight();

        int gMin = 25;
        int gMax = 200 ;

        Color pixelColor;

        for(int y = 0; y < imageHeight; ++y)
            for(int x = 0; x < imageWidth; ++x)
            {
                pixelColor = new Color(sourceImage.getRGB(x, y));
                int red = pixelColor.getRed();
                int green = pixelColor.getGreen();
                int blue = pixelColor.getBlue();

                int newR = 0;
                int newG = 0;
                int newB = 0;

                int inputPixel = (int) (0.3 * red + 0.59 * green + 0.11 * blue);
                if(inputPixel == 0) {
                    newR = gMin / 3;
                    newG = gMin / 3;
                    newB = gMin / 3;
                }

                else {
                    double k = (double) (gMax - gMin) / 255;
                    newR = (int) (k * red + (double) gMin / 3);
                    newG = (int) (k * green + (double) gMin / 3);
                    newB = (int) (k * blue + (double) gMin / 3);

                    if(newR > gMax)
                        newR = 255;
                    if(newG > gMax)
                        newG = 255;
                    if(newB > gMax)
                        newB = 255;
                }

                Color newPixelColor = new Color(newR, newG, newB);

                destinationImage.setRGB(x,y,newPixelColor.getRGB());
            }
        File outputFile = new File(destinationFilePath);
        ImageIO.write(destinationImage, "jpg", outputFile);
    }

    public static void secondImageFilterPreparation(String inputFilePath, String destinationFilePath) throws IOException {
        File sourceFile = new File(inputFilePath);
        BufferedImage sourceImage = ImageIO.read(sourceFile);
        BufferedImage destinationImage = ImageIO.read(sourceFile);

        int imageWidth = sourceImage.getWidth();
        int imageHeight = sourceImage.getHeight();

        int fMin = 20;
        int fMax = 240;

        Color pixelColor;

        for(int y = 0; y < imageHeight; ++y)
            for(int x = 0; x < imageWidth; ++x)
            {
                pixelColor = new Color(sourceImage.getRGB(x, y));
                int red = pixelColor.getRed();
                int green = pixelColor.getGreen();
                int blue = pixelColor.getBlue();

                int newR = 0;
                int newG = 0;
                int newB = 0;;

                int inputPixel = (int) (0.3 * red + 0.59 * green + 0.11 * blue);

                if(inputPixel <= fMin)
                {
                    newR = 0;
                    newG = 0;
                    newB = 0;
                }
                else if(inputPixel >= fMax) {
                    newR = 255;
                    newG = 255;
                    newB = 255;
                }

                else {
                    double k = (double) 255 / (fMax - fMin);
                    newR = (int) (k * (red - fMin / 3));
                    newG = (int) (k * (green - fMin / 3));
                    newB = (int) (k * (blue - fMin / 3));

                    newR = ImageProcessor.checkPixelValue(newR, fMax);
                    newG = ImageProcessor.checkPixelValue(newG, fMax);
                    newB = ImageProcessor.checkPixelValue(newB, fMax);

                }

                int newPixel = (int) (0.3 * newR + 0.59 * newG + 0.11 * newB);

                Color newPixelColor = new Color(newR, newG, newB);

                destinationImage.setRGB(x,y,newPixelColor.getRGB());

            }
        File outputFile = new File(destinationFilePath);
        ImageIO.write(destinationImage, "jpg", outputFile);
    }

    public static int checkPixelValue(int sourceValue, int fMax)
    {
        if(sourceValue > fMax)
            sourceValue = 255;
        if(sourceValue < 0)
            sourceValue = 0;
        return sourceValue;
    }

    public static void MinOrMaxFilterImage(String sourceImagePath, String destinationImagePath, int windowSize, boolean isMax) throws IOException {

        File sourceFile = new File(sourceImagePath);
        BufferedImage sourceImage = ImageIO.read(sourceFile);
        BufferedImage destinationImage = ImageIO.read(sourceFile);

        int imageWidth = sourceImage.getWidth();
        int imageHeight = sourceImage.getHeight();

        int heightDelta = windowSize / 2;
        int widthDelta = windowSize / 2;


        int[] redPixels = new int[windowSize * windowSize];
        int[] greenPixels = new int[windowSize * windowSize];
        int [] bluePixels = new int[windowSize * windowSize];

        int[] generalPixels = new int[windowSize * windowSize];


        for(int y = heightDelta; y < imageHeight - heightDelta; y++)
            for(int x = widthDelta; x < imageWidth - widthDelta; x++)
            {
                int t = 0;
                for(int i = y - heightDelta; i < y + heightDelta + 1; i++)
                    for(int j = x - widthDelta; j < x + widthDelta + 1; j++)
                    {

                            Color currentColor = new Color(sourceImage.getRGB(j,i));
                            redPixels[t] = currentColor.getRed();
                            greenPixels[t] = currentColor.getGreen();
                            bluePixels[t] = currentColor.getBlue();

                            generalPixels[t] = (int) (0.3 * redPixels[t] + 0.59 * greenPixels[t] + 0.11 * bluePixels[t]);
                            t++;

                    }

                Color newPixelColor = null;
                if(!isMax) {
                    int minimumPixelIndex = getMinPixelIndex(generalPixels);
                    newPixelColor = new Color(redPixels[minimumPixelIndex], greenPixels[minimumPixelIndex], bluePixels[minimumPixelIndex]);
                }
                if(isMax) {
                    int maximumPixelIndex = getMaxPixelIndex(generalPixels);
                    newPixelColor = new Color(redPixels[maximumPixelIndex], greenPixels[maximumPixelIndex], bluePixels[maximumPixelIndex]);
                }

                destinationImage.setRGB(x,y, newPixelColor.getRGB());

            }
        File outputFile = new File(destinationImagePath);
        ImageIO.write(destinationImage, "jpg", outputFile);

    }

    public static void minMaxImageFilter(String sourceImagePath, String destinationImagePath, int windowSize) throws IOException {

        String minDestinationFilePath = "F:\\Code\\labsIIPY\\Cosi_lab1\\res\\CAR_min_for_max.png";
        MinOrMaxFilterImage(sourceImagePath, minDestinationFilePath, windowSize, false);
        MinOrMaxFilterImage(minDestinationFilePath, destinationImagePath,windowSize,true);
    }

    public static int getMinPixelIndex(int[] sourcePixels)
    {
        int minimumValue = 255;
        int minimumPixelIndex = 0;
        for(int i = 0; i < sourcePixels.length; i++)
            if(sourcePixels[i] < minimumValue)
            {
                minimumValue = sourcePixels[i];
                minimumPixelIndex = i;
            }
        return minimumPixelIndex;
    }

    public static int getMaxPixelIndex(int[] sourcePixels)
    {
        int maximumValue = 0;
        int maximumPixelIndex = 0;
        for(int i = 0; i < sourcePixels.length; i++)
            if(sourcePixels[i] > maximumValue)
            {
                maximumValue = sourcePixels[i];
                maximumPixelIndex = i;
            }
        return maximumPixelIndex;
    }

    public void minFilter(String imagePath) throws IOException {
        File sourceFile = new File(imagePath);
        BufferedImage sourceImage = ImageIO.read(sourceFile);

        int windowSize = 3;


        int imageWidth = sourceImage.getWidth();
        int imageHeight = sourceImage.getHeight();
    }

}
