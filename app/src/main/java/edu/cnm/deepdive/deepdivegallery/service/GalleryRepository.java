package edu.cnm.deepdive.deepdivegallery.service;

import android.content.Context;

public class GalleryRepository {

  private final Context context;
  private final DeepDiveGalleryServiceProxy deepDiveGalleryServiceProxy;
  private final GoogleSignInService googleSignInService;


  public GalleryRepository(Context context) {
    this.context = context;
    deepDiveGalleryServiceProxy = DeepDiveGalleryServiceProxy.getInstance();
    googleSignInService = GoogleSignInService.getInstance();
  }
}
