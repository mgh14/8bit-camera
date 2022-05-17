package com.mgh14.bitcam;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static com.mgh14.bitcam.NesColorHelper.*;

/**
 *
 */
public class BitReader
{
    private static final int ORIGINAL_WIDTH = 256;
    private static final int ORIGINAL_HEIGHT = 240;
    // original NES: 256 horiz. x 240 vert.  (ratio of
    private static final double ASPECT_RATIO = (double) ORIGINAL_WIDTH / (double) ORIGINAL_HEIGHT;

    public static void main(String[] args)
    {
        System.out.println("Transforming...");
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("/Users/mgh14/Desktop/meae.jpg"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        final int[][] transformed = transformImage(img);

        writePixelsToImage(transformed);
        System.out.println("Done.");
    }

    public static int[][] transformImage(final BufferedImage image) {
        final int[][] imgPixels = getPixelsSlow(image);
        System.out.println("Image Size: " + imgPixels.length + " " + imgPixels[0].length);

        final int[][] coloredPixels = colorifyImage(imgPixels);
        return bittizeImage(coloredPixels);
    }

    private static int[][] bittizeImage(final int[][] imgPixels) {
        int width = imgPixels.length;
        int height = imgPixels[0].length;
        int pixelChangeLimit = (int) (height / 256d);

        for (int i = 0; i < width; i++)
        {
            int currentPixel = imgPixels[i][height - 1];
            for (int j = height - 1; j >= 0; j--)
            {
                if (j % pixelChangeLimit == 0) {
                    currentPixel = imgPixels[i][j];
                } else
                {
                    imgPixels[i][j] = currentPixel;
                }
            }
        }

        return imgPixels;
    }

    private static int[][] colorifyImage(final int[][] imgPixels) {
        int width = imgPixels.length;
        int height = imgPixels[0].length;

        int[][] newImgPixels = new int[width][height];
        for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < height; j++)
            {
                int currentPixel = imgPixels[i][j];
                int closestColorIndex = findClosestColor(currentPixel, NesColorHelper.COLORS);
                final int closestColorInt = rgbToInt(COLORS[closestColorIndex][0],
                        COLORS[closestColorIndex][1], COLORS[closestColorIndex][2]);
                newImgPixels[i][j] = closestColorInt;
            }
        }

        return newImgPixels;
    }

    private static void writePixelsToImage(final int[][] pixels) {
        final int width = pixels.length;
        final int height = pixels[0].length;

        BufferedImage theImage = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                theImage.setRGB(i, j, pixels[i][j]);
            }
        }

        File outputfile = new File("/Users/mgh14/Desktop/out.jpg");
        try {
            outputfile.createNewFile();
            ImageIO.write(theImage, "jpg", outputfile);
        } catch (IOException e1) {

        }
    }

    static int[][] getPixelsSlow(final BufferedImage img) {
        int[][] imgPixels = new int[img.getWidth()][img.getHeight()];
        for (int i = 0; i < img.getWidth(); i++)
        {
            for (int j = 0; j < img.getHeight(); j++)
            {
                imgPixels[i][j] = img.getRGB(i, j);
            }
        }
        return imgPixels;
    }
}
