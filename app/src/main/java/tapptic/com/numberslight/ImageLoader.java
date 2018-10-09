package tapptic.com.numberslight;

import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

public class ImageLoader
{
    public static void loadImage(ImageButton imageButton, String p_image, AssetManager assetManager)
    {
        try {
            InputStream ims = assetManager.open(p_image);

            Drawable d = Drawable.createFromStream(ims, null);
            imageButton.setImageDrawable(d);
            imageButton.setScaleType(ImageView.ScaleType.FIT_XY);

            ims.close();
        } catch (IOException ex) {
            Log.d("myTag", "erreur ouverture");
        }
    }

    public static void loadImage(ImageView imageView, String p_image, AssetManager assetManager)
    {
        try {
            InputStream ims = assetManager.open(p_image);

            Drawable d = Drawable.createFromStream(ims, null);
            imageView.setImageDrawable(d);
            ims.close();
        } catch (IOException ex) {
            Log.d("myTag", "erreur ouverture");
        }
    }
}
