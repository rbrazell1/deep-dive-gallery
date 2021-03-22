package edu.cnm.deepdive.deepdivegallery;

import android.app.Application;
import com.squareup.picasso.Picasso;
import edu.cnm.deepdive.deepdivegallery.service.GoogleSignInService;


public class DeepDiveGalleryApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    GoogleSignInService.setContext(this);
    Picasso.setSingletonInstance(
        new Picasso.Builder(this)
        .loggingEnabled(BuildConfig.DEBUG)
        .build()
    );

  }
}
