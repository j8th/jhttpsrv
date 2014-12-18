package com.eighthlight.jhttpsrv.shared;

/**
 * Created by jason on 12/18/14.
 */
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

}
