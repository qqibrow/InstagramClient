package exercise.lniu.instagramclient;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lniu on 1/24/15.
 */
public class InstagramPhoto {
    public String username;
    public String caption;
    public String imageUrl;
    public int imageHeight;
    public int likesCount;

    static InstagramPhoto InstogramPhotoBuilder(JSONObject jsonObject) throws JSONException {
        InstagramPhoto photo = new InstagramPhoto();
        photo.username = jsonObject.getJSONObject("user").getString("username");
        if(jsonObject.getJSONObject("caption") != null) {
            photo.caption = jsonObject.getJSONObject("caption").getString("text");
        }
        photo.imageUrl = jsonObject.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
        photo.imageHeight = jsonObject.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");
        photo.likesCount = jsonObject.getJSONObject("likes").getInt("count");
        return photo;
    }
}
