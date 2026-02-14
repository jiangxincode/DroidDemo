package edu.jiangxin.droiddemo.mediastore;

import java.util.List;


import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import edu.jiangxin.droiddemo.R;

public class PhotoGridViewAdapter extends BaseAdapter{
	
	private List<PhotoInfo> photolist = null;
	private LayoutInflater inflater = null;
	private Context ctx = null;
	
	public PhotoGridViewAdapter (List<PhotoInfo> infolist,Context ctx)
	{
		this.photolist = infolist;
		this.ctx = ctx;
		this.inflater = LayoutInflater.from(ctx);
	}

	@Override
    public int getCount() {
		// TODO Auto-generated method stub
		return photolist.size();
	}

	@Override
    public Object getItem(int position) {
		// TODO Auto-generated method stub
		return photolist.get(position);
	}

	@Override
    public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		convertView = inflater.inflate(R.layout.photogridviewitem, parent, false);
		ImageView iv = convertView.findViewById(R.id.gv_photo);
		String uri = photolist.get(position).getPhotosrc();
		iv.setImageURI(Uri.parse(uri));
		return convertView;
//		ImageView image = new ImageView(ctx);
//		image.setAdjustViewBounds(true);
//		image.setScaleType(ImageView.ScaleType.FIT_XY);
//		image.setLayoutParams(new Gallery.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
//		String uri = photolist.get(position).getPhotosrc();
//		image.setImageURI(Uri.parse(uri));
//		return image;
	}

}


