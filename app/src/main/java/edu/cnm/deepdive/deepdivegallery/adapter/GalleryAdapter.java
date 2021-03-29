package edu.cnm.deepdive.deepdivegallery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import edu.cnm.deepdive.deepdivegallery.adapter.GalleryAdapter.Holder;
import edu.cnm.deepdive.deepdivegallery.databinding.ItemGalleryBinding;
import edu.cnm.deepdive.deepdivegallery.model.Gallery;
import java.util.List;


public class GalleryAdapter extends RecyclerView.Adapter<Holder> {

  private final Context context;
  private final LayoutInflater inflater;
  private final List<Gallery> galleryList;
  private final OnGalleryClickHelper onGalleryClickHelper;

  public GalleryAdapter(Context context, List<Gallery> galleryList,
      OnGalleryClickHelper onGalleryClickHelper) {
    this.context = context;
    inflater = LayoutInflater.from(context);
    this.galleryList = galleryList;
    this.onGalleryClickHelper = onGalleryClickHelper;
  }

  @NonNull
  @Override
  public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    ItemGalleryBinding binding = ItemGalleryBinding.inflate(inflater, parent, false);
    return new Holder(binding, onGalleryClickHelper);
  }

  @Override
  public void onBindViewHolder(@NonNull Holder holder, int position) {
    holder.bind(position);
  }

  @Override
  public int getItemCount() {
    return galleryList.size();
  }

  class Holder extends RecyclerView.ViewHolder implements OnClickListener {

    private final ItemGalleryBinding binding;
    OnGalleryClickHelper onGalleryClickHelper;
    private Gallery gallery;

    public Holder(ItemGalleryBinding binding, OnGalleryClickHelper onGalleryClickHelper) {
      super(binding.getRoot());
      this.binding = binding;
      this.onGalleryClickHelper = onGalleryClickHelper;
      binding.getRoot().setOnClickListener((OnClickListener) this);
    }

    private void bind(int position) {
      gallery = galleryList.get(position);
      binding.galleryTitle.setText(gallery.getTitle());
      binding.galleryDescription.setText(gallery.getDescription());
      binding.getRoot().setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
      onGalleryClickHelper
          .onGalleryClick(galleryList.get(getAdapterPosition()).getId().toString(), view);
    }
  }

  public interface OnGalleryClickHelper {
    void onGalleryClick(String galleryId, View view);
  }

}
