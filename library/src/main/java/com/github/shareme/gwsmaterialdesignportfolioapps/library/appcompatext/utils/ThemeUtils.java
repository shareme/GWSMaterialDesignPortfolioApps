/*
 * Copyright (C) 2015 The Android Open Source Project
 * Modifications Copyright(C) 2016 Fred Grott(GrottWorkShop)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.shareme.gwsmaterialdesignportfolioapps.library.appcompatext.utils;

import android.content.Context;
import android.content.res.TypedArray;

/**
 * ThemeUtils
 * Created by fgrott on 2/16/2016.
 */
@SuppressWarnings("unused")
public class ThemeUtils {
  private static final int[] TEMP_ARRAY = new int[1];

  public static int getThemeAttrColor(Context context, int attr) {
    TEMP_ARRAY[0] = attr;
    TypedArray a = context.obtainStyledAttributes(null, TEMP_ARRAY);
    try {
      return a.getColor(0, 0);
    } finally {
      a.recycle();
    }
  }
}
