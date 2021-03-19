package edu.cnm.deepdive.deepdivegallery.controller;

import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import edu.cnm.deepdive.deepdivegallery.R;
import edu.cnm.deepdive.deepdivegallery.databinding.FragmentUploadPropertiesBinding;
import edu.cnm.deepdive.deepdivegallery.viewmodel.MainViewModel;

public class UploadPropertiesFragment extends DialogFragment {

  private FragmentUploadPropertiesBinding binding;
  private Uri uri;
  private AlertDialog dialog;
  private MainViewModel mainViewModel;


  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // TODO get uri from parameters
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(
      @Nullable Bundle savedInstanceState) {
    binding = FragmentUploadPropertiesBinding.inflate(
        LayoutInflater.from(getContext()), null, false);
    dialog = new androidx.appcompat.app.AlertDialog.Builder(getContext())
        .setIcon(R.drawable.ic_upload_24)
        .setTitle("Upload Properties")
        .setView(binding.getRoot())
        .setNeutralButton(android.R.string.cancel, (dlg, which) -> {/* Does Nothing, no need */})
        .setPositiveButton(android.R.string.ok, (dlg, which) -> {/* TODO Start upload process */})
        .create();
    // TODO Attach text listener to validate fields
    return dialog;
  }

  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(
      @NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    // TODO setup viewmodel and observe as necessary
  }
}