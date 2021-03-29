package edu.cnm.deepdive.deepdivegallery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import edu.cnm.deepdive.deepdivegallery.BuildConfig;
import edu.cnm.deepdive.deepdivegallery.adapter.GalleryAdapter.Holder;
import edu.cnm.deepdive.deepdivegallery.databinding.ItemGalleryBinding;
import edu.cnm.deepdive.deepdivegallery.model.Image;
import java.util.ArrayList;
import java.util.List;


public class GalleryAdapter extends RecyclerView.Adapter<Holder> {

  private final Context context;
  private final List<Image> imageList;
  private final LayoutInflater inflater;

  public GalleryAdapter(Context context) {
    this.context = context;
    imageList = new ArrayList<>();
    inflater = LayoutInflater.from(context);
  }

  public List<Image> getImageList() {
    return imageList;
  }

  @NonNull
  @Override
  public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    ItemGalleryBinding binding = ItemGalleryBinding.inflate(inflater, parent, false);
    return new Holder(binding);
  }

  @Override
  public void onBindViewHolder(@NonNull Holder holder, int position) {
    holder.bind(imageList.get(position));
  }

  @Override
  public int getItemCount() {
    return imageList.size();
  }

  static class Holder extends RecyclerView.ViewHolder {

    private final ItemGalleryBinding binding;

    public Holder(ItemGalleryBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    private void bind(Image image) {
      Picasso.get()
          .load(String.format(BuildConfig.CONTENT_FORMAT, image.getHref()))
          .into(binding.image);
      String title = image.getTitle();
      String filename = image.getName();
      String description = image.getDescription();
      binding.title.setText((title != null) ? title : filename);
      if (description != null) {
        binding.description.setText(description);
        binding.image.setContentDescription(description);
        binding.image.setTooltipText(description);
      } else {
        binding.description.setText("");
        binding.image.setContentDescription("");
        binding.image.setTooltipText("");
      }
    }


  }

}
