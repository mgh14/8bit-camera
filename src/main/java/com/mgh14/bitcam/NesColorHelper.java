package com.mgh14.bitcam;

/**
 *
 */
public class NesColorHelper
{
    private NesColorHelper() {}

    public static final int[][] COLORS = new int[][] {
            { 0,86,95 },
            { 1,0,0 },
            { 18,124,34 },
            { 189,189,189 },
            { 204,75,9 },
            { 204,90,29 },
            { 215,39,0 },
            { 219,252,255 },
            { 235,134,52 },
            { 247,72,186 },
            { 249,237,241 },
            { 255,186,170 },
            { 31,56,236 },
            { 33,153,43 },
            { 34,122,2 },
            { 64,153,149 },
            { 72,176,0 },
            { 93,148,251 },
            { 95,138,240 }
    };

//    public static final int[][] COLORS = new int[][] {
//            { 124,124,124 },
//            { 0,0,252 },
//            { 0,0,188 },
//            { 68,40,188 },
//            { 148,0,132 },
//            { 168,0,32 },
//            { 168,16,0 },
//            { 136,20,0 },
//            { 80,48,0 },
//            { 0,120,0 },
//            { 0,104,0 },
//            { 0,88,0 },
//            { 0,64,88 },
//            { 0,0,0 },
//            { 188,188,188 },
//            { 0,120,248 },
//            { 0,88,248 },
//            { 104,68,252 },
//            { 216,0,204 },
//            { 228,0,88 },
//            { 248,56,0 },
//            { 228,92,16 },
//            { 172,124,0 },
//            { 0,184,0 },
//            { 0,168,0 },
//            { 0,168,68 },
//            { 0,136,136 },
//            { 248,248,248 },
//            { 60,188,252 },
//            { 104,136,252 },
//            { 152,120,248 },
//            { 248,120,248 },
//            { 248,88,152 },
//            { 248,120,88 },
//            { 252,160,68 },
//            { 248,184,0 },
//            { 184,248,24 },
//            { 88,216,84 },
//            { 88,248,152 },
//            { 0,232,216 },
//            { 120,120,120 },
//            { 252,252,252 },
//            { 164,228,252 },
//            { 184,184,248 },
//            { 216,184,248 },
//            { 248,184,248 },
//            { 248,164,192 },
//            { 240,208,176 },
//            { 252,224,168 },
//            { 248,216,120 },
//            { 216,248,120 },
//            { 184,248,184 },
//            { 184,248,216 },
//            { 0,252,252 },
//            { 248,216,248 }
//    };

    public static int findClosestColor(final int pixel) {
        int red = (pixel & (0xff << 16)) >> 16;
        int green = (pixel & (0xff << 8)) >> 8;
        int blue = (pixel & (0xff << 0)) >> 0;

        int distance = 1000;
        int closestIndex = -1;
        for (int i = 0; i < COLORS.length; i++)
        {
            int colorRed = COLORS[i][0];
            int colorGreen = COLORS[i][1];
            int colorBlue = COLORS[i][2];

            int totalDistance = Math.abs(red - colorRed) + Math.abs(green - colorGreen) + Math.abs(blue - colorBlue);
            if (totalDistance < distance) {
                distance = totalDistance;
                closestIndex = i;
                if (distance == 0) {
                    break;
                }
            }
        }

//        System.out.println("Closest color for pixel " + pixel + " (" + red + ", " + green + ", " +
//                blue + ") is " + COLORS[closestIndex]);
        return closestIndex;
    }

    public static int rgbToInt(final int red, final int green, final int blue)
    {
        int pixel = 0;
        pixel += (red << 16);
        pixel += (green << 8);
        pixel += (blue << 0);
        return pixel;
    }
}
