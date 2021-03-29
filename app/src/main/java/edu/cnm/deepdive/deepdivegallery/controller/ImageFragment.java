package edu.cnm.deepdive.deepdivegallery.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import edu.cnm.deepdive.deepdivegallery.R;
import edu.cnm.deepdive.deepdivegallery.databinding.FragmentImageBinding;
import edu.cnm.deepdive.deepdivegallery.viewmodel.ImageViewModel;
import java.util.UUID;

public class ImageFragment extends Fragment {

  private FragmentImageBinding binding;
  private ImageViewModel viewModel;
  private UUID id;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    binding = FragmentImageBinding.inflate(inflater);
    return binding.getRoot();
  }
}