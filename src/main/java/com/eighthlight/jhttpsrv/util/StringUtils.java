package com.eighthlight.jhttpsrv.util;

import java.util.HashMap;
import java.util.Map;

public class StringUtils {
    /*
     * Credit where credit's due:
     * http://stackoverflow.com/a/3571239
     */
    public static String getFileExtension(String path) {
        String extension = "";

        int i = path.lastIndexOf('.');
        int p = Math.max(path.lastIndexOf('/'), path.lastIndexOf('\\'));

        if (i > p) {
            extension = path.substring(i+1);
        }

        return extension;
    }

    public static Map<String, String> parseHttpFormData(String formData) {
        Map<String, String> result = new HashMap<String, String>();
        if(formData == null)
            return result;

        String[] tokens = formData.split("&");
        for(int i = 0; i < tokens.length; i++) {
            String[] subtokens = tokens[i].split("=");
            if(subtokens.length == 2)
                result.put(subtokens[0], subtokens[1]);
        }

        return result;
    }
}