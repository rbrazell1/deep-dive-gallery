package edu.cnm.deepdive.deepdivegallery.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import edu.cnm.deepdive.deepdivegallery.R;
import edu.cnm.deepdive.deepdivegallery.service.GoogleSignInService;
import edu.cnm.deepdive.deepdivegallery.viewmodel.ImageViewModel;

public class MainActivity extends AppCompatActivity {

  private ImageViewModel viewModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    setViewModel();
  }

  private void setViewModel() {
    viewModel = new ViewModelProvider(this).get(ImageViewModel.class);
    getLifecycle().addObserver(viewModel);
    viewModel.getThrowable().observe(this, (throwable) -> {
      if (throwable != null) {
        Toast.makeText(this, throwable.getLocalizedMessage(), Toast.LENGTH_LONG).show();
      }
    });
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    super.onCreateOptionsMenu(menu);
    getMenuInflater().inflate(R.menu.main_options, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    boolean handled = true;
    switch (item.getItemId()) {
      case R.id.sign_out:
        logout();
        break;
      default:
        handled = super.onOptionsItemSelected(item);
    }

    return super.onOptionsItemSelected(item);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
  }

  private void logout() {
    GoogleSignInService
        .getInstance()
        .signOut()
        .addOnCompleteListener((ignored) -> {
          Intent intent = new Intent(this, LoginActivity.class)
              .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
          startActivity(intent);
        });
  }
}