package xchg.online.shoppingcartframe;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by rsankarx on 07/12/16.
 */

public class AsychImageDownload extends AsyncTask<Void, Void, Void> {

    private String imageURL;
    private URLDownloadListener listener;
    private Bitmap image;
    private ImageView holder;

    public AsychImageDownload(String url, ImageView iv, URLDownloadListener l) {
        imageURL = url;
        listener = l;
        holder = iv;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listener.showProgress();
    }

    @Override
    protected Void doInBackground(Void... params) {
        downloadBitmap();
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        listener.dismissProgress();
        if(image != null)
        {
            holder.setImageBitmap(image);
        }
    }

    private Bitmap downloadBitmap() {
        try {
            URL url = new URL(imageURL);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            byte[] bitmapdata = new byte[1024];
            ByteArrayOutputStream ostr = new ByteArrayOutputStream();
            int read = in.read(bitmapdata);
            while (read > 0) {
                ostr.write(bitmapdata, 0, read);
                read = in.read(bitmapdata);
            }
            ByteArrayInputStream istr = new ByteArrayInputStream(ostr.toByteArray());
            ostr.close();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            image = BitmapFactory.decodeStream(istr);
        } catch (Exception e) {
            Log.e("ImageDownloader", "Something went wrong while" +
                    " retrieving bitmap from " + imageURL + e.toString());
        }

        return image;

    }

}
