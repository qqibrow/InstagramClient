package exercise.lniu.instagramclient;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.RoundedImageView;
import com.makeramen.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by lniu on 1/24/15.
 */
public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {

    public InstagramPhotosAdapter(Context context, ArrayList<InstagramPhoto> objects) {
        super(context, R.layout.list_item, objects);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        InstagramPhoto photo = getItem(position);
        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);

        // Get all views.
        TextView tvCaption = (TextView)convertView.findViewById(R.id.tvCaption);
        ImageView imgPhoto = (ImageView)convertView.findViewById(R.id.imgPhoto);
        ImageView roundImgUser = (ImageView)convertView.findViewById(R.id.roundImgUser);
        TextView tvUser = (TextView)convertView.findViewById(R.id.tvUser);
        TextView tvLikes = (TextView)convertView.findViewById(R.id.tvLikes);
        TextView tvTime = (TextView)convertView.findViewById(R.id.tvTime);

        long currentTime = System.currentTimeMillis();
        tvTime.setText(DateUtils.getRelativeTimeSpanString(photo.timeStamp*1000,
                currentTime,
                DateUtils.SECOND_IN_MILLIS, DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR ));

        tvUser.setText(photo.username);

        DecimalFormat formatter = new DecimalFormat("#,###");
        tvLikes.setText(String.format("%s likes", formatter.format(photo.likesCount)));

        if(photo.caption != null)
            tvCaption.setText(photo.caption);

        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int screenWidth = size.x;

        final int showImgHeight = (int)(screenWidth * (photo.imageHeight / photo.imageWidth * 1.0f));
        final int showImgWidth = screenWidth;

        imgPhoto.getLayoutParams().height = showImgHeight;
        imgPhoto.getLayoutParams().width = showImgWidth;


        // reset image from the recycled view.
        // TODO(lniu) Bug: image not dynamically scale. Sometimes there are white blank around image.
        imgPhoto.setImageResource(0);
        final int maxDimention = Math.max(showImgHeight, showImgWidth);
        Picasso.with(getContext()).load(photo.imageUrl).into(imgPhoto);


        roundImgUser.setImageResource(0);
        // Background: Ask for the photo to be added in the imageView on the photo url.
        Transformation transformation = new RoundedTransformationBuilder()
                .borderColor(Color.BLACK)
                .borderWidthDp(2)
                .cornerRadiusDp(20)
                .oval(false)
                .build();

        Picasso.with(getContext())
                .load(photo.profileImgUrl)
                .transform(new CircleTransform())
                .into(roundImgUser);
       // Picasso.with(getContext()).load(photo.profileImgUrl).into(roundImgUser);

        return convertView;
    }
}
