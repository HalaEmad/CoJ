package com.ibm.android.kit.utils;

/**
 *
 */

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Environment;

/**
 * @author Bassam
 * 
 */
public class ImageUtility {
	private final static String IMG_EXT = ".jpg";
	private final static String CASH_DIR = "/Android/data/%s/cache/";

	/**
	 * get private image directory
	 * 
	 * @param context
	 * @return
	 */
	public static String getPrivateImageDir(Context context) {

		return context.getFilesDir().getAbsolutePath();
	}

	/**
	 * get public image dir
	 * 
	 * @param context
	 * @return
	 */
	public static String getPublicImageDir(Context context) {

		String sdDir = Environment.getExternalStorageDirectory().getAbsolutePath();

		if (sdDir != null) {
			// format cash directory
			String cashDir = String.format(CASH_DIR, context.getPackageName());
			String path = sdDir + cashDir;

			// create directory if not created
			File file = new File(path);
			file.mkdirs();

			return path;
		}

		return null;
	}

	/**
	 * save image in application private folder
	 * 
	 * @param context
	 * @param image
	 * @param imageName
	 * @return
	 */
	public static void savePrivateImage(Context context, Bitmap bitmap, String imageName, int width, int height) {

		if (bitmap != null) {
			try {
				FileOutputStream out = context.openFileOutput(imageName + IMG_EXT, Context.MODE_PRIVATE);

				// resize
				if (width > 0 && height > 0)
					bitmap = resize(bitmap, width, height);

				bitmap.compress(CompressFormat.PNG, 100, out);

				out.flush();
				out.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * get saved private bitmap
	 * 
	 * @param context
	 * @param imageName
	 * @return
	 */
	public static Bitmap getPrivateImage(Context context, String imageName) {

		Bitmap bitmap = null;

		try {
			FileInputStream in = context.openFileInput(imageName + IMG_EXT);

			bitmap = BitmapFactory.decodeStream(in);

			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return bitmap;
	}

	/**
	 * delete saved private image
	 * 
	 * @param context
	 * @param imageName
	 * @return
	 */
	public static boolean deletePrivateImage(Context context, String imageName) {

		return context.deleteFile(imageName + IMG_EXT);
	}

	/**
	 * save image in public cache folder
	 * 
	 * @param context
	 * @param bitmap
	 * @param imageName
	 */
	public static void savePublicImage(Context context, Bitmap bitmap, String imageName, int width, int height) {

		if (bitmap != null) {
			try {
				String filePath = getPublicImagePath(context, imageName);

				if (filePath != null) {
					// open output stream
					FileOutputStream out = new FileOutputStream(filePath);

					// resize
					if (width > 0 && height > 0)
						bitmap = resize(bitmap, width, height);

					bitmap.compress(CompressFormat.PNG, 100, out);

					out.flush();
					out.close();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static Bitmap resize(Bitmap bitmap, int width, int height) {

		byte[] bytes = getByteArrayFromBitmap(bitmap);
		return decodeSampledBitmapFromBytes(bytes, width, height);
	}

	/**
	 * get saved public bitmap
	 * 
	 * @param context
	 * @param imageName
	 * @return
	 */
	public static Bitmap getPublicImage(Context context, String imageName) {

		Bitmap bitmap = null;

		try {
			String filePath = getPublicImagePath(context, imageName);

			if (filePath != null) {
				// open output stream
				FileInputStream in = new FileInputStream(filePath);

				bitmap = BitmapFactory.decodeStream(in);

				in.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return bitmap;
	}

	/**
	 * delete saved public image
	 * 
	 * @param context
	 * @param imageName
	 * @return
	 */
	public static boolean deletePublicImage(Context context, String imageName) {

		String filePath = getPublicImagePath(context, imageName);
		File file = new File(filePath);
		return file.delete();
	}

	/**
	 * get private image file path
	 * 
	 * @param context
	 * @param imageName
	 * @return
	 */
	public static String getPrivateImagePath(Context context, String imageName) {

		// File file = context.getFileStreamPath(imageName + PRIVATE_IMG_EXT);
		String dir = getPrivateImageDir(context);
		if (dir != null) {
			return dir + imageName + IMG_EXT;
		}

		return null;
	}

	/**
	 * get saved public bitmap
	 * 
	 * @param context
	 * @param imageName
	 * @return
	 */
	public static String getPublicImagePath(Context context, String imageName) {

		String dir = getPublicImageDir(context);

		if (dir != null) {
			return dir + imageName + IMG_EXT;
		}

		return null;
	}

	/**
	 * crop bitmap
	 * 
	 * @param bitmap
	 * @param rect
	 * @return
	 */
	public static Bitmap cropBitmap(Bitmap bitmap, Rect rect) {

		Bitmap cropedbitmap = Bitmap.createBitmap(bitmap, rect.left, rect.top, rect.width(), rect.height());
		bitmap.recycle();
		bitmap = null;

		return cropedbitmap;
	}

	/**
	 * get bitmap byte array
	 * 
	 * @param bitmap
	 * @return
	 */
	public static byte[] getByteArrayFromBitmap(Bitmap bitmap) {

		if (bitmap != null) {
			ByteArrayOutputStream compressedBytes = new ByteArrayOutputStream();
			bitmap.compress(CompressFormat.PNG, 100, compressedBytes);

			return compressedBytes.toByteArray();
		}

		return null;
	}

	/**
	 * get bitmap from byte array
	 * 
	 * @param data
	 */
	public static Bitmap getBitmapFromByteArray(byte[] data) {

		if (data != null) {
			return BitmapFactory.decodeByteArray(data, 0, data.length);
		}

		return null;
	}

	/**
	 * calculate in sample size to scale down image based on required width and
	 * height
	 * 
	 * @param width
	 * @param height
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static int calculateInSampleSize(int width, int height, int reqWidth, int reqHeight) {

		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {
			if (width > height) {
				inSampleSize = Math.round((float) height / (float) reqHeight);
			} else {
				inSampleSize = Math.round((float) width / (float) reqWidth);
			}
		}

		return inSampleSize;
	}

	/**
	 * draw water-mark on bitmap from drawable resource
	 * 
	 * @param context
	 * @param bitmap
	 * @param watermarkDrawbleId
	 * @return
	 */
	public static Bitmap addWatermark(Context context, Bitmap bitmap, int watermarkDrawbleId) {

		if (bitmap == null) {
			return null;
		}

		// create immutable bitmap from input bitmap
		Bitmap mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
		if (mutableBitmap == null) {
			return null;
		}

		Bitmap watermarkBitmap = BitmapFactory.decodeResource(context.getResources(), watermarkDrawbleId);
		if (watermarkBitmap == null) {
			return null;
		}

		Canvas canvas = new Canvas(mutableBitmap);
		canvas.drawBitmap(watermarkBitmap, 0, mutableBitmap.getHeight() - watermarkBitmap.getHeight(), null);

		return mutableBitmap;
	}

	public static Bitmap decodeSampledBitmapFromBytes(byte[] imageBytes, int reqWidth, int reqHeight) {
		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;

		BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length, options);
	}

	private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {
			// Calculate ratios of height and width to requested height and
			// width
			final int heightRatio = Math.round((float) height / (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);

			// Choose the smallest ratio as inSampleSize value, this will
			// guarantee
			// a final image with both dimensions larger than or equal to the
			// requested height and width.
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}

		return inSampleSize;
	}
}
