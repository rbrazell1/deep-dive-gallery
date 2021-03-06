package edu.cnm.deepdive.deepdivegallery.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import edu.cnm.deepdive.deepdivegallery.NavGraphDirections;
import edu.cnm.deepdive.deepdivegallery.NavGraphDirections.OpenUploadProperties;
import edu.cnm.deepdive.deepdivegallery.R;
import edu.cnm.deepdive.deepdivegallery.adapter.GalleryAdapter;
import edu.cnm.deepdive.deepdivegallery.adapter.GalleryAdapter.OnGalleryClickHelper;
import edu.cnm.deepdive.deepdivegallery.databinding.FragmentGalleryBinding;
import edu.cnm.deepdive.deepdivegallery.model.Image;
import edu.cnm.deepdive.deepdivegallery.viewmodel.GalleryViewModel;
import edu.cnm.deepdive.deepdivegallery.viewmodel.ImageViewModel;
import java.util.List;


public class GalleryFragment extends Fragment implements OnGalleryClickHelper {

  private static final int PICK_IMAGE_REQUEST = 0210;

  private FragmentGalleryBinding binding;
  private GalleryViewModel galleryViewModel;
  private ImageViewModel imageViewModel;
  private GalleryAdapter adapter;


  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
  }

  @Override
  public void onCreateOptionsMenu(@NonNull Menu menu,
      @NonNull MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.menu_gallery, menu);
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    boolean handled = true;
    //noinspection SwitchStatementWithTooFewBranches
    switch (item.getItemId()) {
      case R.id.action_refresh:
        imageViewModel.loadImageList();
        break;
      default:
        handled = super.onOptionsItemSelected(item);
    }
    return handled;
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
      OpenUploadProperties action = NavGraphDirections.openUploadProperties(data.getData());
      Navigation
          .findNavController(binding.getRoot())
          .navigate(action);
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    binding = FragmentGalleryBinding.inflate(inflater, container, false);
    binding.galleryRecyclerview.setAdapter(adapter);
    binding.addImage.setOnClickListener((c) -> pickImage());
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view,
      @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    //noinspection ConstantConditions
    imageViewModel = new ViewModelProvider(getActivity()).get(ImageViewModel.class);
    galleryViewModel = new ViewModelProvider(getActivity()).get(GalleryViewModel.class);
    galleryViewModel
        .getGalleryList()
        .observe(getViewLifecycleOwner(), (galleryList) -> {
          if (galleryList != null) {
            binding
                .galleryRecyclerview
                .setAdapter(new GalleryAdapter(
                    getContext(),
                    galleryList,
                    this::onGalleryClick));
          }
        });
  }

  private void pickImage() {
    Intent intent = new Intent();
    intent.setType("image/*");
    intent.setAction(Intent.ACTION_GET_CONTENT);
    startActivityForResult(Intent.createChooser(intent,
        getString(R.string.pick_image)),
        PICK_IMAGE_REQUEST);
  }

  private void updateGallery(Image image) {
//    List<Image> imageList = adapter.getGalleryList();
//    if (image != null && !imageList.contains(image)) {
//      imageList.add(0, image);
//      adapter.notifyItemInserted(0);
//    }
  }

  private void updateGallery(List<Image> imageList) {
//    adapter.getGalleryList().clear();
//    adapter.getGalleryList().addAll(imageList);
    adapter.notifyDataSetChanged();
  }

  @Override
  public void onGalleryClick(String galleryId, View view) {
    GalleryFragmentDirections.GalleryFragmentToImageFragment toImageFragment =
        GalleryFragmentDirections.galleryFragmentToImageFragment(galleryId);
    toImageFragment.setGalleryImages(galleryId);
    Navigation.findNavController(view).navigate(toImageFragment);
  }
}