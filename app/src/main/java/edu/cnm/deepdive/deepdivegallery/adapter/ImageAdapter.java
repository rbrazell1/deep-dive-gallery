package edu.cnm.deepdive.deepdivegallery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import edu.cnm.deepdive.deepdivegallery.BuildConfig;
import edu.cnm.deepdive.deepdivegallery.adapter.ImageAdapter.Holder;
import edu.cnm.deepdive.deepdivegallery.databinding.ItemImageBinding;
import edu.cnm.deepdive.deepdivegallery.model.Image;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<Holder> {

  private final Context context;
  private final List<Image> imageList;
  private final LayoutInflater layoutInflater;
  private final OnImageClickHelper onImageClickHelper;

  public ImageAdapter(Context context, List<Image> imageList,
      OnImageClickHelper onImageClickHelper) {
    this.context = context;
    this.imageList = imageList;
    layoutInflater = LayoutInflater.from(context);
    this.onImageClickHelper = onImageClickHelper;
  }

  @NonNull
  @Override
  public ImageAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent,
      int viewType) {
    ItemImageBinding binding = ItemImageBinding.inflate(layoutInflater, parent, false);
    return new Holder(binding, onImageClickHelper);
  }

  @Override
  public void onBindViewHolder(@NonNull ImageAdapter.Holder holder, int position) {
    holder.bind(position);
  }

  @Override
  public int getItemCount() {
    return imageList.size();
  }


  public class Holder extends RecyclerView.ViewHolder implements OnClickListener {

    private final ItemImageBinding binding;
    private Image image;
    private OnImageClickHelper onImageClickHelper;

    public Holder(ItemImageBinding binding, OnImageClickHelper onImageClickHelper) {
      super(binding.getRoot());
      this.binding = binding;
      this.onImageClickHelper = onImageClickHelper;
      binding.getRoot().setOnClickListener(this);
    }

    private void bind(int position) {
      image = imageList.get(position);
      if (image.getHref() != null) {
        Picasso
            .get()
            .load(String.format(BuildConfig.CONTENT_FORMAT, image.getHref()))
            .into(binding.imageInGallery);
      }
      binding.imageTitle.setText(image.getTitle());
      binding.imageDescription.setText(image.getDescription());
    }

    @Override
    public void onClick(View view) {
      onImageClickHelper.onImageClick(imageList.get(getAdapterPosition()), getAdapterPosition());
    }
  }

  public interface OnImageClickHelper {

    void onImageClick(Image image, int position);
  }

}
