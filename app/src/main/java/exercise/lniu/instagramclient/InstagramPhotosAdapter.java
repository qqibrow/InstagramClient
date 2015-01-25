package exercise.lniu.instagramclient;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.RoundedImageView;
import com.makeramen.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

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
        RoundedImageView roundImgUser = (RoundedImageView)convertView.findViewById(R.id.roundImgUser);
        if(photo.caption != null)
            tvCaption.setText(photo.caption);
        imgPhoto.getLayoutParams().height = photo.imageHeight;
        // reset image from the recycled view.
        imgPhoto.setImageResource(0);
        // Background: Ask for the photo to be added in the imageView on the photo url.
        Picasso.with(getContext()).load(photo.profileImgUrl).into(roundImgUser);
        Picasso.with(getContext()).load(photo.imageUrl).into(imgPhoto);
//        Transformation transformation = new RoundedTransformationBuilder()
//                .borderColor(Color.BLACK)
//                .borderWidthDp(3)
//                .cornerRadiusDp(30)
//                .oval(false)
//                .build();

//        Picasso.with(getContext())
//                .load("https://igcdn-photos-b-a.akamaihd.net/hphotos-ak-xaf1/t51.2885-19/10881954_647074052068465_483507547_a.jpg")
//                .fit()
//                .transform(transformation)
//                .into(roundImgUser);
        return convertView;
    }
}
