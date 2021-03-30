package edu.cnm.deepdive.deepdivegallery.controller;

import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AlertDialog.Builder;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import com.squareup.picasso.Picasso;
import edu.cnm.deepdive.deepdivegallery.R;
import edu.cnm.deepdive.deepdivegallery.databinding.FragmentUploadPropertiesBinding;
import edu.cnm.deepdive.deepdivegallery.model.Gallery;
import edu.cnm.deepdive.deepdivegallery.viewmodel.GalleryViewModel;
import edu.cnm.deepdive.deepdivegallery.viewmodel.ImageViewModel;
import java.util.List;
import java.util.UUID;

public class UploadPropertiesFragment extends DialogFragment implements TextWatcher {

  private FragmentUploadPropertiesBinding binding;
  private Uri uri;
  private AlertDialog dialog;
  private ImageViewModel imageViewModel;
  private GalleryViewModel galleryViewModel;
  private List<Gallery> galleryList;


  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    uri = UploadPropertiesFragmentArgs.fromBundle(getArguments()).getContentUri();
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(
      @Nullable Bundle savedInstanceState) {
    binding = FragmentUploadPropertiesBinding.inflate(
        LayoutInflater.from(getContext()), null, false);
    dialog = new Builder(getContext())
        .setIcon(R.drawable.ic_upload_24)
        .setTitle(R.string.create_dialog_title)
        .setView(binding.getRoot())
        .setNeutralButton(android.R.string.cancel, (dlg, which) -> {/* Does Nothing, no need */})
        .setPositiveButton(android.R.string.ok, (dlg, which) -> upload())
        .create();
    dialog.setOnShowListener((dlg) -> checkSubmitConditions());
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
    Picasso
        .get()
        .load(uri)
        .into(binding.image);
    binding.imageTitle.addTextChangedListener(this);
    binding.galleryTitleDescription.addTextChangedListener(this);
    imageViewModel = new ViewModelProvider(getActivity()).get(ImageViewModel.class);
    galleryViewModel
        .getGalleryList()
        .observe(getViewLifecycleOwner(),
            (galleries) -> this.galleryList = galleries);
  }

  @Override
  public void beforeTextChanged(CharSequence s, int start, int count, int after) {

  }

  @Override
  public void onTextChanged(CharSequence s, int start, int before, int count) {

  }

  @Override
  public void afterTextChanged(Editable s) {
    checkSubmitConditions();

  }

  private void checkSubmitConditions() {
    Button posititve = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
    posititve.setEnabled(!binding.imageTitle.getText().toString().trim().isEmpty());
  }

  private void upload() {
    String title = binding.imageTitle.getText().toString().trim();
    String description = binding.galleryDescription.getText().toString().trim();
    String galleryTitle = binding.galleryTitleDescription.getText().toString().trim();
    String titleId = "";
    for (Gallery g : galleryList) {
      if (g != null && galleryTitle.equals(g.getTitle())) {
        titleId = g.getId().toString();
      }
    }
    UUID id = UUID.fromString(titleId);
    imageViewModel.store(id,uri,title, (description.isEmpty() ? null : description));
  }
}