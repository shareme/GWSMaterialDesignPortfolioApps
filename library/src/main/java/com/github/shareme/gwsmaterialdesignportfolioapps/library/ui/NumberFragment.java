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
package com.github.shareme.gwsmaterialdesignportfolioapps.library.ui;

import android.os.Bundle;
import android.text.InputType;

/**
 * NumberFragment
 * Created by fgrott on 2/16/2016.
 */
@SuppressWarnings("unused")
public class NumberFragment extends TextFragment {
  public static NumberFragment create(String key) {
    Bundle args = new Bundle();
    args.putString(ARG_KEY, key);

    NumberFragment f = new NumberFragment();
    f.setArguments(args);
    return f;
  }


  @Override
  protected void setInputType() {

      mEditTextInput.setInputType(InputType.TYPE_CLASS_NUMBER);

  }

}