package edu.cnm.deepdive.deepdivegallery.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle.Event;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;
import edu.cnm.deepdive.deepdivegallery.model.Gallery;
import edu.cnm.deepdive.deepdivegallery.service.GalleryRepository;
import io.reactivex.disposables.CompositeDisposable;
import java.util.List;
import java.util.UUID;

public class GalleryViewModel extends AndroidViewModel implements LifecycleObserver {

  private final MutableLiveData<Gallery> gallery;
  private final MutableLiveData<List<Gallery>> galleryList;
  private final MutableLiveData<Throwable> throwable;
  private final CompositeDisposable pending;
  private final GalleryRepository galleryRepository;

  public GalleryViewModel(
      @NonNull Application application) {
    super(application);
    galleryRepository = new GalleryRepository(application);
    galleryList = new MutableLiveData<>();
    gallery = new MutableLiveData<>();
    throwable = new MutableLiveData<>();
    pending = new CompositeDisposable();
    loadGalleries();
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

  public void loadGalleries() {
    throwable.postValue(null);
    pending.add(
        galleryRepository
            .getGalleryList()
            .subscribe(
                galleryList::postValue,
                throwable::postValue
            )
    );
  }

  public void getGallery(UUID id) {
    throwable.postValue(null);
    pending.add(
        galleryRepository
        .getGallery(id)
        .subscribe(
            gallery::postValue,
            throwable::postValue
        )
    );
  }

  @OnLifecycleEvent(Event.ON_STOP)
  private void clearPending() {
    pending.clear();
  }
}
