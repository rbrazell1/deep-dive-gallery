package edu.cnm.deepdive.deepdivegallery.controller;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import edu.cnm.deepdive.deepdivegallery.R;

public class LoginActivity extends AppCompatActivity {

  private TextView mTextView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    mTextView = (TextView) findViewById(R.id.text);
  }
}