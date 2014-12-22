package com.eighthlight.jhttpsrv.shared;

import java.io.File;
import java.util.*;

/**
 * Created by jason on 12/11/14.
 */
public class MIMETypes {
    public static final String HTML = "text/html";
    public static final String PLAIN_TEXT = "text/plain";
    public static final String JPEG = "image/jpeg";

    private static final Map<String, String[]> MIMEType_FileExtensions;
    static {
        Map<String, String[]> myMap = new HashMap<String, String[]>();

        myMap.put(HTML, new String[] {"html", "htm"});
        myMap.put(PLAIN_TEXT, new String[] {"txt"});
        myMap.put(JPEG, new String[] {"jpeg", "jpg"});

        MIMEType_FileExtensions = Collections.unmodifiableMap(myMap);
    }

    public static String FileExt2MIMEType(String fileExt) throws IllegalArgumentException {
        for(Map.Entry<String, String[]> e : MIMEType_FileExtensions.entrySet()) {
            String MIMEType = e.getKey();
            String[] FileExtensions = e.getValue();
            if(Arrays.asList(FileExtensions).contains(fileExt))
                return MIMEType;
        }
        throw new IllegalArgumentException(String.format("'%s' is not a valid file extension.", fileExt));
    }
}
