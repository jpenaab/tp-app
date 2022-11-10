package org.hopto.tiempoplaya.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Size;

import java.util.List;

public class ImageUtils {

    public static Bitmap getReducedBitmapImages(final String imagePath, final int requiredWidth, final int requiredHeight)
    {
        System.out.println(" --- image_path in getBitmapForCameraImages --- "+imagePath+" - reqWidth & reqHeight "+requiredWidth+" "+requiredHeight);
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = true;
        options.inJustDecodeBounds = true;

        // First decode with inJustDecodeBounds=true to check dimensions
        bitmap = BitmapFactory.decodeFile(imagePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, requiredWidth, requiredHeight);

        options.inJustDecodeBounds = false;

        // Decode bitmap with inSampleSize set
        bitmap = BitmapFactory.decodeFile(imagePath, options);

        return bitmap;
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;

        int stretch_width = Math.round((float)width / (float)reqWidth);
        int stretch_height = Math.round((float)height / (float)reqHeight);

        if (stretch_width <= stretch_height)
            return stretch_height;
        else
            return stretch_width;
    }
}
