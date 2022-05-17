package com.mgh14.bitcam;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

import static com.mgh14.bitcam.BitReader.getPixelsSlow;

/**
 *
 */
public class CompareReaders
{
    public static void main(String[] args)
    {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("/Users/mgh14/Desktop/me.jpg"));
        } catch (IOException e) {
        }

        final int[][] imgPixels = getPixels(img);
        final int[][] imgP2 = getPixelsSlow(img);

        System.out.println("Size x size: " + imgPixels.length + " " + imgPixels[0].length + ", " + imgP2.length + " " + imgP2[0].length);
        long counter = 0;
        for (int i = 0; i < imgPixels.length; i++)
        {
            for (int j = 0; j < imgPixels[0].length; j++)
            {
                final int diff = imgPixels[i][j] - imgP2[i][j];
                if (diff == 0)
                {
                    counter++;
                }
                else
                {
                    //todo
                    //System.out.println("Not zero! " + i + "," + j + " diff: " + diff);
                }
            }
        }

        int x = 4;
    }

    // note: this one is currently not working! (i.e. incorrect!)
    public static int[][] getPixels(final BufferedImage img) {
        byte[] m = ((DataBufferByte) img.getData().getDataBuffer()).getData();

        int mPointer = 0;
        int[][] imgPixels = new int[img.getWidth()][img.getHeight()];
        int bytesPerPixel = hasAlphaChannel(img) ? 4 : 3;
        for (int i = 0; i < img.getHeight(); i++)
        {

            for (int j = 0; j < img.getWidth(); j++)
            {
                int pixel = 0;

                //1
                byte currentByte = m[mPointer];
                pixel = pixel | (currentByte);

                //2
                currentByte = m[mPointer + 1];
                pixel = pixel | (currentByte) << (8);

                //3
                currentByte = m[mPointer + 2];
                pixel = pixel | (currentByte) << (8 * 2);

                if (bytesPerPixel == 4) {
                    //4
                    currentByte = m[mPointer + 3];
                    pixel = pixel | (currentByte) << (8 * 3);
                }

                mPointer = mPointer + bytesPerPixel;
                int mine = pixel - img.getRGB(j, i);
                imgPixels[j][i] = pixel;
            }
        }

        return imgPixels;
    }

    private static boolean hasAlphaChannel(final BufferedImage img) {
        return img != null && img.getAlphaRaster() != null;
    }
}
