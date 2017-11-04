import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.widget.ImageView;
import android.app.Activity;

public class VideoProcessing
{
	void convertBitmapToJpeg(Bitmap bmFrame, String name)
	{
		Files filesDir = getAppContext().getFilesDir();
		File image = new File(filesDir, name+".jpg");

		OutputStream os;
		try{
			os = new FileOutputStream(image);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
			os.flush();
			os.close();
		}
		catch(Exception e) {
			Log.e(getClass().getSimpleName(),"Error writing bitmap", e);		
		}
	}
	public static void main(String args[])
	{
		String uri = "/home/mmukundram/Videos/ShoppingCam.mp4";
		MediaMetaDataRetriever myMedia = new MediaMetadataRetriever();
		myMedia.setDataSource(url);
		Bitmap bmFrame = mediaMetadataRetriever.getFrameAtTime(5000000);
		convertBitmapToJpeg(bmFrame,"ShoppingCam");
	}
}

/*

public class MainActivity extends Activity {
 
 

 @Override
 protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_main);
  ImageView capturedImageView = (ImageView)findViewById(R.id.capturedimage);
  
  MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
  
  mediaMetadataRetriever.setDataSource(uri);
  Bitmap bmFrame = mediaMetadataRetriever.getFrameAtTime(5000000); //unit in microsecond
  capturedImageView.setImageBitmap(bmFrame);
 }

}


*/

