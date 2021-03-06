package edu.cnm.deepdive.deepdivegallery.service;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.util.Log;
import edu.cnm.deepdive.deepdivegallery.model.Image;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ImageRepository {

  private final Context context;
  private final DeepDiveGalleryServiceProxy serviceProxy;
  private final GoogleSignInService signInService;
  private final ContentResolver resolver;
  private final MediaType multipartFormType;


  public ImageRepository(Context context) {
    this.context = context;
    serviceProxy = DeepDiveGalleryServiceProxy.getInstance();
    signInService = GoogleSignInService.getInstance();
    resolver = context.getContentResolver();
    multipartFormType = MediaType.parse("multipart/form-data");
  }

  @SuppressWarnings("ResultOfMethodCallIgnored")
  public Single<Image> add(UUID id, Uri uri, String title, String description) {
    File[] filesCreated = new File[1];
    return signInService
        .refreshBearerToken()
        .observeOn(Schedulers.io())
        .flatMap((token) -> {
          try (
              Cursor cursor = resolver.query(uri, null, null, null, null);
              InputStream input = resolver.openInputStream(uri);
          ) {
            MediaType type = MediaType.parse(resolver.getType(uri));
            int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            cursor.moveToFirst();
            String filname = cursor.getString(nameIndex);
            File outputDir = context.getCacheDir();
            File outputFile = File.createTempFile("xfer", null, outputDir);
            Files.copy(input, outputFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            RequestBody fileBody = RequestBody.create(outputFile, type);
            MultipartBody.Part filePart = MultipartBody.Part
                .createFormData("file", filname, fileBody);
            RequestBody titlePart = RequestBody.create(title, multipartFormType);
            if (description != null) {
              RequestBody descriptionPart = RequestBody.create(description, multipartFormType);
              return serviceProxy.post(id,token, filePart, titlePart, descriptionPart);
            } else {
              return serviceProxy.post(id,token, filePart, titlePart);
            }
          }
        })
        .doFinally(() -> {
          if (filesCreated[0] != null) {
            try {
              filesCreated[0].delete();
            } catch (Exception e) {
              Log.e(getClass().getName(), e.getMessage(), e);
            }
          }
        });
  }

  public Single<List<Image>> getAll() {
    return signInService.refreshBearerToken()
        .observeOn(Schedulers.io())
        .flatMap(serviceProxy::getAllImages);
  }
}
