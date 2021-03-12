package edu.cnm.deepdive.deepdivegallery.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import edu.cnm.deepdive.deepdivegallery.model.User;
import edu.cnm.deepdive.deepdivegallery.service.UserRepository;
import org.jetbrains.annotations.NotNull;

public class MainViewModel extends AndroidViewModel {

  private final UserRepository userRepository;
  private final MutableLiveData<GoogleSignInAccount> account;
  private final MutableLiveData<User> user;
  private final MutableLiveData<Throwable> throwable;


  public MainViewModel(
      @NonNull @NotNull Application application) {
    super(application);
    userRepository = new UserRepository(application);
    account = new MutableLiveData<>(userRepository.getAccount());
    user = new MutableLiveData<>();
    throwable = new MutableLiveData<>();
    testRoundTrip();
  }

  public LiveData<User> getUser() {
    return user;
  }

  public LiveData<Throwable> getThrowable() {
    return throwable;
  }

  private void testRoundTrip() {
    userRepository.getUserProfile()
        .subscribe(
            user::postValue,
            throwable::postValue
        );
  }

}
