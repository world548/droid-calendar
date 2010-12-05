package com.kshun.droidcalendar.model;

import java.util.ArrayList;
import java.util.List;

import com.kshun.R;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class MarkImageProvider {
	public static final int BIT_MARK[] = {1,2,4,8,16,32,64,128};
	public static final int RES_ID[] = {
		R.drawable.mark1,
		R.drawable.mark2,
		R.drawable.mark3,
		R.drawable.mark4,
		R.drawable.mark5,
		R.drawable.mark6,
		R.drawable.mark7,
		R.drawable.mark8
		};
	private static Bitmap[] markImages = new Bitmap[8];
	private static Resources _r = null;

	public static void init(Resources r){
		_r = r;
		loadImagesView();
	}

	private static void loadImagesView(){
		for(int i=0 ; i<markImages.length ; i++){
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			markImages[i] = BitmapFactory.decodeResource(_r, RES_ID[i], options);
			int scaleW = options.outWidth / 10 + 1;
			int scaleH = options.outHeight / 10 + 1;
			int scale = Math.max(scaleW, scaleH);
			options.inJustDecodeBounds = false;
			options.inSampleSize = scale;
			markImages[i] = BitmapFactory.decodeResource(_r, RES_ID[i], options);
		}
	}

	public static List<Bitmap> getMarkBitmapList(int mark){
		List<Bitmap> resList = new ArrayList<Bitmap>();
		for(int i=0 ; i<markImages.length ; i++){
				if((mark & BIT_MARK[i]) == BIT_MARK[i]){
					resList.add(markImages[i]);
				}
		}
		return resList;
	}
}
