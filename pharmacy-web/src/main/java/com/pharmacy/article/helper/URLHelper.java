/*
 * Copyright 2015 Alexander.
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
package com.pharmacy.article.helper;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alexander
 */
public class URLHelper {
    
    public String encodeURL(String url) {
        String result = url.replace("/", "");
        try {
            result = URLEncoder.encode(result, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(URLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
