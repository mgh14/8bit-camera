package com.mgh14.bitcam;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static com.mgh14.bitcam.NesColorHelper.COLORS;
import static com.mgh14.bitcam.NesColorHelper.findClosestColor;
import static com.mgh14.bitcam.NesColorHelper.rgbToInt;

/**
 *
 */
public class BitReader
{
    private static final int ORIGINAL_WIDTH = 256;
    private static final int ORIGINAL_HEIGHT = 240;
    private static final double ASPECT_RATIO = (double) ORIGINAL_WIDTH / (double) ORIGINAL_HEIGHT;

    public static void main(String[] args)
    {
        BufferedImage img = null;
        try {
//            img = ImageIO.read(new File("/Users/mgh14/Desktop/me.jpg"));
            img = ImageIO.read(new File("/Users/mgh14/Desktop/test.jpg"));
        } catch (IOException e) {
        }

        final int[][] imgPixels = getPixelsSlow(img);
        System.out.println("Image Size: " + imgPixels.length + " " + imgPixels[0].length);

        final int[][] newImgPixels = colorifyImage(imgPixels);
        final int[][] newAgain = bittizeImage(newImgPixels);
        writePixelsToImage(newAgain);

        System.out.println("Done.");
    }

    private static int[][] bittizeImage(final int[][] imgPixels) {
        int width = imgPixels.length;
        int height = imgPixels[0].length;
        int me = (int) (height / 256d) * 1;

        for (int i = 0; i < width; i++)
        {
            int currentPixel = imgPixels[i][height - 1];
            for (int j = height - 1; j >= 0; j--)
            {
                if (j % me == 0) {
                    currentPixel = imgPixels[i][j];
                } else
                {
                    imgPixels[i][j] = currentPixel;
                }
            }
        }

        return imgPixels;
    }

    // original NES: 256 horiz. x 240 vert.  (ratio of
    private static int[][] colorifyImage(final int[][] imgPixels) {
        int width = imgPixels.length;
        int height = imgPixels[0].length;

        int[][] newImgPixels = new int[width][height];
        for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < height; j++)
            {
                int currentPixel = imgPixels[i][j];
                int closestColorIndex = findClosestColor(currentPixel);
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
