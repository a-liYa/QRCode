/*
 * Copyright (C) 2008 ZXing authors
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

package com.aliya.scanner.client;

import android.app.Activity;

/**
 * The main settings activity.
 *
 * @author dswitkin@google.com (Daniel Switkin)
 * @author Sean Owen
 */
public final class PreferencesActivity extends Activity {

  public static final String KEY_CUSTOM_PRODUCT_SEARCH = "preferences_custom_product_search";

  public static final String KEY_SUPPLEMENTAL = "preferences_supplemental";
  public static final String KEY_AUTO_FOCUS = "preferences_auto_focus";
  public static final String KEY_INVERT_SCAN = "preferences_invert_scan";  
  public static final String KEY_SEARCH_COUNTRY = "preferences_search_country";

  public static final String KEY_DISABLE_CONTINUOUS_FOCUS = "preferences_disable_continuous_focus";
  public static final String KEY_DISABLE_EXPOSURE = "preferences_disable_exposure";
  public static final String KEY_DISABLE_METERING = "preferences_disable_metering";
  public static final String KEY_DISABLE_BARCODE_SCENE_MODE = "preferences_disable_barcode_scene_mode";
  public static final String KEY_AUTO_OPEN_WEB = "preferences_auto_open_web";

}
