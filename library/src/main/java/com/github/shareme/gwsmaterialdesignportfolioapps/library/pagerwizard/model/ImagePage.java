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
package com.github.shareme.gwsmaterialdesignportfolioapps.library.pagerwizard.model;

import android.app.Fragment;

import com.github.shareme.gwsmaterialdesignportfolioapps.library.pagerwizard.ui.ImageFragment;

/**
 * ImagePage
 * Created by fgrott on 2/16/2016.
 */
@SuppressWarnings("unused")
public class ImagePage extends TextPage {

  public ImagePage(ModelCallbacks callbacks, String title) {
    super(callbacks, title);
  }

  @Override
  public Fragment createFragment() {
    return ImageFragment.create(getKey());
  }

  public ImagePage setValue(String value) {
    mData.putString(SIMPLE_DATA_KEY, value);
    return this;
  }
}
