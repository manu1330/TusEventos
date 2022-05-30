package com.example.tuseventos.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Articulos implements Serializable {
    String id;
    String title;
    String subtitle;
    String text;
    String image;
    Date date;
    Float lat;
    Float lng;
    Boolean isFavorite;
    Boolean isRemindme;

    public Articulos(JSONObject json) {
        try {
            id = json.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            title = json.getString("title");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            subtitle = json.getString("subtitle");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            text = json.getString("text");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            image = json.getString("image");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = sdf.parse(json.getString("date"));
        } catch (JSONException | ParseException e) {
            e.printStackTrace();
        }
        try {
            lat = (float) json.getDouble("lat");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            lng = (float) json.getDouble("lng");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            isFavorite = json.getBoolean("is_favorite");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            isRemindme = json.getBoolean("is_remindme");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        try {
            json.put("id", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            json.put("title", title);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            json.put("subtitle", subtitle);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            json.put("text", text);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            json.put("image", image);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            json.put("date", sdf.format(date));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            json.put("lat", lat);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            json.put("lng", lng);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    public String getId() {return id;}

    public void setId(String id) {this.id = id;}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Float getLat() {
        return lat;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public Float getLng() {
        return lng;
    }

    public void setLng(Float lng) {
        this.lng = lng;
    }

    public Boolean getFavorite() {return isFavorite;}

    public void setFavorite(Boolean favorite) {isFavorite = favorite;}

    public Boolean getRemindme() {return isRemindme;}

    public void setRemindme(Boolean remindme) {isRemindme = remindme;}
}

