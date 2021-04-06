package edu.cnm.deepdive.deepdivegallery.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import edu.cnm.deepdive.deepdivegallery.adapter.ImageAdapter;
import edu.cnm.deepdive.deepdivegallery.adapter.ImageAdapter.OnImageClickHelper;
import edu.cnm.deepdive.deepdivegallery.databinding.FragmentImageBinding;
import edu.cnm.deepdive.deepdivegallery.model.Image;
import edu.cnm.deepdive.deepdivegallery.viewmodel.GalleryViewModel;
import java.util.UUID;

public class ImageFragment extends Fragment implements OnImageClickHelper {

  private FragmentImageBinding binding;
  private GalleryViewModel galleryViewModel;
  private UUID galleryId;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    binding = FragmentImageBinding.inflate(inflater);
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    galleryViewModel = new ViewModelProvider(getActivity()).get(GalleryViewModel.class);
    if (getArguments() != null) {
      ImageFragmentArgs args = ImageFragmentArgs.fromBundle(getArguments());
      galleryId = UUID.fromString(args.getGalleryImages());
    }
    galleryViewModel.getGallery(galleryId);
    galleryViewModel.getGallery().observe(getViewLifecycleOwner(), (gallery) -> {
      if (gallery.getImageList() != null) {
        binding.imageRecyclerView.setAdapter(new ImageAdapter(
            getContext(),
            gallery.getImageList(),
            this));
      }
    });
  }

  @Override
  public void onImageClick(Image image, int position) {
    ImageDialogFragment fragment = ImageDialogFragment.newInstance(image);
    fragment.show(getChildFragmentManager(), fragment.getClass().getName());
  }
}