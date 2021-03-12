package edu.cnm.deepdive.deepdivegallery.service;

import android.content.Context;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import edu.cnm.deepdive.deepdivegallery.model.User;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class UserRepository {

  private final Context context;
  private final DeepDiveGalleryServiceProxy webService;
  private final GoogleSignInService signInService;

  public UserRepository(Context context) {
    this.context = context;
    signInService = GoogleSignInService.getInstance();
    webService = DeepDiveGalleryServiceProxy.getInstance();
  }

  public GoogleSignInAccount getAccount() {
    return signInService.getAccount();
  }

  public Single<User> getUserProfile() {
    return signInService.refresh()
        .observeOn(Schedulers.io())
        .flatMap((account) -> webService.getProfile(getBearerToken(account.getIdToken()))
            .subscribeOn(Schedulers.io()));
  }

  private String getBearerToken(String idToken) {
    return String.format("Bearer %s", idToken);
  }

}
