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
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by rsankarx on 07/12/16.
 */

public class AsychImageDownload extends AsyncTask<Void, Void, Void> {

    private static Queue<AsychImageDownload> DOWNLOADQUEUE = new ConcurrentLinkedQueue<>();
    private static AtomicInteger running = new AtomicInteger(0);

    private static final int MAX_RUNNING = 1;

    private String imageURL;
    private URLDownloadListener listener;
    private Bitmap image;
    private ImageView holder;
    private int scale;

    public AsychImageDownload(String url, ImageView iv, URLDownloadListener l, int s) {
        imageURL = url;
        listener = l;
        holder = iv;
        scale = s;
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

    public void startProcessing() {
        while (!DOWNLOADQUEUE.isEmpty() && (running.get() <= MAX_RUNNING)) {
            running.incrementAndGet();
            AsychImageDownload download = DOWNLOADQUEUE.poll();
            download.execute();
        }
    }

    public void queue() {
        DOWNLOADQUEUE.add(this);
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
            options.inJustDecodeBounds = false;
            options.inSampleSize = scale;
            image = BitmapFactory.decodeStream(istr, null, options);
            istr.close();
        } catch (Exception e) {
            Log.e("ImageDownloader", "Something went wrong while" +
                    " retrieving bitmap from " + imageURL + e.toString());
        }

        running.decrementAndGet();
        startProcessing();
        return image;

    }

}
