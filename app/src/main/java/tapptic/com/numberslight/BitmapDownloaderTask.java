package tapptic.com.numberslight;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.widget.Button;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class BitmapDownloaderTask extends AsyncTask<String, Void, Bitmap> {
    private final WeakReference<ImageView> imageViewReference;
    public Context myContext;

    public BitmapDownloaderTask(ImageView imageView, Context p_context) {
        imageViewReference = new WeakReference<ImageView>(imageView);
        myContext = p_context;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        try {
            URL pictureURL = new URL(params[0]);

            HttpURLConnection connection = (HttpURLConnection) pictureURL.openConnection();

            InputStream is = new BufferedInputStream(connection.getInputStream());
            is.mark(is.available());

            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;

            Bitmap img = BitmapFactory.decodeStream(is, null, options);

            return img;

        } catch (MalformedURLException e) {
            Logger.debug(e.getMessage());
            e.printStackTrace();

        } catch (IOException e) {
            Logger.debug(e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    @Override
    // Once the image is downloaded, associates it to the imageView
    protected void onPostExecute(Bitmap bitmap) {
        if (isCancelled()) {
            bitmap = null;
        }

        if (imageViewReference != null) {
            ImageView myimage = imageViewReference.get();

            if (myimage != null) {
                BitmapDrawable bdrawable = new BitmapDrawable(myContext.getResources(),bitmap);
                myimage.setImageDrawable(bdrawable);
//                myimage.setCompoundDrawables(null,bdrawable,null,null);
            }
        }

        if (bitmap != null) {
            bitmap = null;
        }
    }
}