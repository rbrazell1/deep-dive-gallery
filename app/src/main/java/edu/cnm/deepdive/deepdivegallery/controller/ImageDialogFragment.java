package edu.cnm.deepdive.deepdivegallery.controller;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import com.squareup.picasso.Picasso;
import edu.cnm.deepdive.deepdivegallery.BuildConfig;
import edu.cnm.deepdive.deepdivegallery.databinding.FragmentImageDialogBinding;
import edu.cnm.deepdive.deepdivegallery.model.Image;

public class ImageDialogFragment extends DialogFragment {

  private Image image;

  public static ImageDialogFragment newInstance(Image image) {
    ImageDialogFragment fragment = new ImageDialogFragment();
    Bundle args = new Bundle();
    args.putSerializable("image", image);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      image = (Image) getArguments().getSerializable("image");
    }
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

    FragmentImageDialogBinding binding = FragmentImageDialogBinding.inflate(
        LayoutInflater.from(getContext()));

    if (image.getHref() != null) {
      Picasso
          .get()
          .load(String
              .format(BuildConfig.CONTENT_FORMAT, image.getHref()))
          .into(binding.imageDetail);
    }

    binding.imageDescription.setText(
        (image.getDescription()) != null ? image.getDescription() : "N?A");
    binding.imageId.setText(
        (image.getUuid() != null) ? "Id: " + image.getUuid() : "N/A");
    binding.imageType.setText(
        (image.getContentType() != null) ? "Image type: " + image.getContentType() : "N/A");
    binding.imageUrl.setText(
        (image.getHref() != null) ? "URL: " + image.getHref() : "N/A");
    binding.imageDateCreated.setText(
        (image.getCreated() != null) ? "Created Date: " + image.getCreated() : "N/A");

    return new AlertDialog.Builder(getContext())
        .setTitle((image.getTitle() != null) ? image.getTitle() : "Untitled")
        .setView(binding.getRoot())
        .setPositiveButton("Close", (dlg, which) -> {

        })
        .create();
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return null;
  }
}