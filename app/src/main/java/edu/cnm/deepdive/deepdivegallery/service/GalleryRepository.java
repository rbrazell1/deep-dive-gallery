package edu.cnm.deepdive.deepdivegallery.service;

import android.content.Context;
import edu.cnm.deepdive.deepdivegallery.model.Gallery;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import java.util.UUID;

public class GalleryRepository {

  private final Context context;
  private final DeepDiveGalleryServiceProxy deepDiveGalleryServiceProxy;
  private final GoogleSignInService googleSignInService;


  public GalleryRepository(Context context) {
    this.context = context;
    deepDiveGalleryServiceProxy = DeepDiveGalleryServiceProxy.getInstance();
    googleSignInService = GoogleSignInService.getInstance();
  }

  public Single<Gallery> getGallery(UUID id) {
    return googleSignInService
        .refreshBearerToken()
        .observeOn(Schedulers.io())
        .flatMap((account) ->
            deepDiveGalleryServiceProxy.getGallery(id, account));
  }

  public Single<List<Gallery>> getGalleryList() {
    return googleSignInService
        .refreshBearerToken()
        .observeOn(Schedulers.io())
        .flatMap(deepDiveGalleryServiceProxy::getGalleryList);
  }
}
