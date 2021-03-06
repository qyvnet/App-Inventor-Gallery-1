package com.android.aigproject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MainListAdapter extends ArrayAdapter<ListItem>{
    Context context; 
    int layoutResourceId;  
    ArrayList<ListItem> data;
    //ListItem data[] = null;
    
    // Initializer
    public MainListAdapter(Context context, int layoutResourceId, ArrayList<ListItem> data) {
    	super(context, layoutResourceId, data);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.data = data;
    }

    /* This will be called for every item in the ListView,
     * to create views with their properties set as we want.
     * */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	View row = convertView;
    	
    	/* This will be used to cache the ImageView and TextView
    	 *  so they can be reused for every row in the ListView
    	 */
    	ListItemHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            
            holder = new ListItemHolder();
            
            ImageView image = (ImageView)row.findViewById(R.id.thumbnail);
            holder.thumbnail = image;
            holder.txtTitle = (TextView)row.findViewById(R.id.txtTitle);
            holder.txtAuthor = (TextView)row.findViewById(R.id.txtAuthor);
            holder.txtDesc = (TextView)row.findViewById(R.id.txtDesc);
            
            row.setTag(holder);
        }
        else
        {
            holder = (ListItemHolder)row.getTag();
        }
    	
    	
        ListItem source_item = data.get(position);
        holder.txtTitle.setText(source_item.title);
        holder.txtAuthor.setText(source_item.author);
        holder.txtDesc.setText(source_item.desc);
//        holder.imgIcon.setImageResource(source_item.icon);
        holder.thumbnail.setImageBitmap(loadImageByURL(source_item.imageFileURL));
    	return row;
    }
    
    /* Created temporarily for holding data into ListItem.
     * */
    static class ListItemHolder {
    	ImageView thumbnail;
        TextView txtTitle;
        String imageFileURL;
        TextView txtAuthor;
        TextView txtDesc;
        TextView txtLikes;
    }
    
    private Bitmap loadImageByURL(String imageFileURL) {
		try {
			URL url = new URL(imageFileURL);
			
			if(!imageFileURL.endsWith("00")){
				imageFileURL = imageFileURL + "100";
			}
			
			
			URLConnection conn = url.openConnection();
			HttpURLConnection httpConn = (HttpURLConnection) conn;
			httpConn.setRequestMethod("GET");
			httpConn.connect();
			if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				InputStream inputStream = httpConn.getInputStream();
				Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
				inputStream.close();
				return bitmap;
			} else {
				// return null;
			}

		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
    
    
}
