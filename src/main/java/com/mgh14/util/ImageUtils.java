package com.mgh14.util;

/**
 *
 */
public class ImageUtils
{
    private ImageUtils() {}

    public static boolean areSame(int[][] image1, int[][] image2) {
        if (image1.length != image2.length) {
            return false;
        }

        if (image1.length == 0) {
            return true;
        }

        if (image1[0].length != image2[0].length) {
            return false;
        }

        // ok, same dimensions. Now we start comparing pixel by pixel...
        int dim1 = image1.length;
        int dim2 = image1[0].length;
        for (int i = 0; i < dim1; i++)
        {
            for (int j = 0; j < dim2; j++)
            {
                int diff = image1[i][j] - image2[i][j];
                if (diff != 0) {
                    return false;
                }
            }
        }

        return true;
    }
}
