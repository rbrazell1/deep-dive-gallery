package edu.cnm.deepdive.deepdivegallery.viewmodel;

import android.app.Application;
import android.net.Uri;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle.Event;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import edu.cnm.deepdive.deepdivegallery.model.Image;
import edu.cnm.deepdive.deepdivegallery.model.User;
import edu.cnm.deepdive.deepdivegallery.service.ImageRepository;
import edu.cnm.deepdive.deepdivegallery.service.UserRepository;
import io.reactivex.disposables.CompositeDisposable;
import java.util.List;
import java.util.UUID;

public class ImageViewModel extends AndroidViewModel implements LifecycleObserver {

  private final UserRepository userRepository;
  private final MutableLiveData<GoogleSignInAccount> account;
  private final MutableLiveData<User> user;
  private final MutableLiveData<Throwable> throwable;
  private final MutableLiveData<Image> image;
  private final MutableLiveData<List<Image>> imageList;
  private final CompositeDisposable pending;
  private final ImageRepository imageRepository;

  public ImageViewModel(
      @NonNull Application application) {
    super(application);
    userRepository = new UserRepository(application);
    account = new MutableLiveData<>(userRepository.getAccount());
    user = new MutableLiveData<>();
    throwable = new MutableLiveData<>();
    image = new MutableLiveData<>();
    imageList = new MutableLiveData<>();
    pending = new CompositeDisposable();
    imageRepository = new ImageRepository(application);
  }

  public LiveData<User> getUser() {
    return user;
  }

  public LiveData<Throwable> getThrowable() {
    return throwable;
  }

  public LiveData<Image> getImage() {
    return image;
  }

  public LiveData<List<Image>> getImageList() {
    return imageList;
  }

  public void store(UUID id, Uri uri, String title, String description) {
    throwable.postValue(null);
    pending.add(
        imageRepository
            .add(id,uri, title, description)
            .subscribe(
                (image) -> loadImageList(),
                (this::postThrowable)
            )
    );

  }

  public void loadImageList() {
    throwable.postValue(null);
    pending.add(
        imageRepository
            .getAll()
            .subscribe(
                imageList::postValue,
                throwable::postValue
            )
    );
  }

  @OnLifecycleEvent(Event.ON_STOP)
  private void clearPending() {
    pending.clear();
  }

  private void postThrowable(Throwable throwable) {
    Log.e(getClass().getName(), throwable.getMessage(), throwable);
    this.throwable.postValue(throwable);
  }
}
