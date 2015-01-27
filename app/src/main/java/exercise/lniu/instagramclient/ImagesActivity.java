package exercise.lniu.instagramclient;

import android.graphics.drawable.ColorDrawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ImagesActivity extends ActionBarActivity {

    public static final String CLIENT_ID = "00848b8aa18140cb9f67a432b34e5839";
    private ArrayList<InstagramPhoto> photos;
    private ListView lvPhotos;
    private InstagramPhotosAdapter photosAdapter;
    private SwipeRefreshLayout swipeContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF3F9FE0));

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchPopularPhotos();
            }
        });

        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        photos = new ArrayList<InstagramPhoto>();

        lvPhotos = (ListView)findViewById(R.id.lvPhotos);
        photosAdapter = new InstagramPhotosAdapter(this, photos);
        lvPhotos.setAdapter(photosAdapter);

        lvPhotos.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int topRowVerticalPosition =
                        (lvPhotos == null || lvPhotos.getChildCount() == 0) ?
                                0 : lvPhotos.getChildAt(0).getTop();
                swipeContainer.setEnabled(firstVisibleItem == 0 && topRowVerticalPosition >= 0);
            }
        });
        fetchPopularPhotos();
    }

    private void fetchPopularPhotos() {
        // https://api.instagram.com/v1/media/popular?client_id=00848b8aa18140cb9f67a432b34e5839
        AsyncHttpClient client = new AsyncHttpClient();
        // Trigger the network request.
        String popularUrl = "https://api.instagram.com/v1/media/popular?client_id=" + CLIENT_ID;
        client.get(popularUrl, new JsonHttpResponseHandler() {
            // Define success and failure callbacks.
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                   JSONArray photosJson = null;
                try {
                    photos.clear();
                    photosJson = response.getJSONArray("data");
                    for( int i = 0; i < photosJson.length(); ++i) {
                        JSONObject photoJson = photosJson.getJSONObject(i);
                        InstagramPhoto photo = InstagramPhoto.InstogramPhotoBuilder(photoJson);
                        //Log.i("DEBUG", photo.toString());
                        photos.add(photo);
                    }
                    photosAdapter.notifyDataSetChanged();
                    swipeContainer.setRefreshing(false);
                }catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.menu_images, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }
}
