package edu.cnm.deepdive.deepdivegallery.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import edu.cnm.deepdive.deepdivegallery.model.Gallery;
import java.util.List;

public class GalleryViewModel extends AndroidViewModel {

  private final MutableLiveData<Gallery> gallery;
  private final MutableLiveData<List<Gallery>> galleryList;
  private final MutableLiveData<Throwable> throwable;

  public GalleryViewModel(
      @NonNull Application application) {
    super(application);
    galleryList = new MutableLiveData<>();
    gallery = new MutableLiveData<>();
    throwable = new MutableLiveData<>();
  }

  public LiveData<Gallery> getGallery() {
    return gallery;
  }

  public LiveData<List<Gallery>> getGalleryList() {
    return galleryList;
  }

  public LiveData<Throwable> getThrowable() {
    return throwable;
  }
}
