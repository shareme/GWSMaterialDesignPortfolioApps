/*
 * Copyright 2012 Roman Nurik
 * Modifications Copyright(C) 2016 Fred Grott(GrottWorkShop)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.shareme.gwsmaterialdesignportfolioapps.library.pagerwizard.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.shareme.gwsmaterialdesignportfolioapps.library.R;
import com.github.shareme.gwsmaterialdesignportfolioapps.library.pagerwizard.model.Page;

/**
 * ImageFragment
 * Created by fgrott on 2/16/2016.
 */
@SuppressWarnings("unused")
public class ImageFragment extends Fragment {

  private static final String NEW_IMAGE_URI = "new_image_uri";
  private final int GALLERY_REQUEST_CODE =
          Math.abs((short) (hashCode() ^ (hashCode() >>> 16)));
  private final int CAMERA_REQUEST_CODE =
          Math.abs((short) (hashCode() ^ (hashCode() >>> 16)) - 1);

  protected static final String ARG_KEY = "key";

  private PageFragmentCallbacks mCallbacks;
  private String mKey;
  private Page mPage;

  private ImageView imageView;

  private Uri mNewImageUri;

  public static ImageFragment create(String key) {
    Bundle args = new Bundle();
    args.putString(ARG_KEY, key);

    ImageFragment f = new ImageFragment();
    f.setArguments(args);
    return f;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Bundle args = getArguments();
    mKey = args.getString(ARG_KEY);
    mPage = mCallbacks.onGetPage(mKey);

    if (savedInstanceState != null) {
      String uriString = savedInstanceState.getString(NEW_IMAGE_URI);
      if (!TextUtils.isEmpty(uriString)) {
        mNewImageUri = Uri.parse(uriString);
      }
    }
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    if (mNewImageUri != null) {
      outState.putString(NEW_IMAGE_URI, mNewImageUri.toString());
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_page_image,
            container, false);
    ((TextView) rootView.findViewById(android.R.id.title)).setText(mPage
            .getTitle());

    imageView = (ImageView) rootView.findViewById(R.id.imageView);

    String imageData = mPage.getData().getString(Page.SIMPLE_DATA_KEY);
    if (!TextUtils.isEmpty(imageData)) {
      Uri imageUri = Uri.parse(imageData);
      imageView.setImageURI(imageUri);
    } else {
      imageView.setImageResource(R.drawable.ic_person);
    }

    imageView.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View v) {
        DialogFragment pickPhotoSourceDialog = new DialogFragment() {
          @Override
          public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(
                    getActivity());
            builder.setItems(R.array.image_photo_sources,
                    new DialogInterface.OnClickListener() {

                      @Override
                      public void onClick(DialogInterface dialog,
                                          int which) {
                        switch (which) {
                          case 0:
                            // Gallery
                            Intent photoPickerIntent = new Intent(
                                    Intent.ACTION_GET_CONTENT);
                            photoPickerIntent
                                    .setType("image/*");
                            startActivityForResult(
                                    photoPickerIntent,
                                    GALLERY_REQUEST_CODE);
                            break;

                          default:
                            // Camera
                            mNewImageUri = getActivity()
                                    .getContentResolver()
                                    .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                            new ContentValues());
                            Intent photoFromCamera = new Intent(
                                    MediaStore.ACTION_IMAGE_CAPTURE);
                            photoFromCamera.putExtra(
                                    MediaStore.EXTRA_OUTPUT,
                                    mNewImageUri);
                            photoFromCamera
                                    .putExtra(
                                            MediaStore.EXTRA_VIDEO_QUALITY,
                                            0);
                            startActivityForResult(
                                    photoFromCamera,
                                    CAMERA_REQUEST_CODE);
                            break;
                        }

                      }
                    });
            return builder.create();
          }
        };

        pickPhotoSourceDialog.show(getFragmentManager(),
                "pickPhotoSourceDialog");
      }
    });

    return rootView;
  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);

    if (!(activity instanceof PageFragmentCallbacks)) {
      throw new ClassCastException(
              "Activity must implement PageFragmentCallbacks");
    }

    mCallbacks = (PageFragmentCallbacks) activity;
  }

  @Override
  public void onDetach() {
    super.onDetach();
    mCallbacks = null;
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if(requestCode == CAMERA_REQUEST_CODE) {
      if (resultCode == Activity.RESULT_OK) {
        imageView.setImageURI(mNewImageUri);
        writeResult();
      }
    } else if(requestCode == GALLERY_REQUEST_CODE) {
      if (resultCode == Activity.RESULT_OK && data != null) {
        mNewImageUri = data.getData();
        imageView.setImageURI(mNewImageUri);
        writeResult();
      }
    }
  }

  private void writeResult() {
    mPage.getData().putString(Page.SIMPLE_DATA_KEY,
            (mNewImageUri != null) ? mNewImageUri.toString() : null);
    mPage.notifyDataChanged();

  }

}
