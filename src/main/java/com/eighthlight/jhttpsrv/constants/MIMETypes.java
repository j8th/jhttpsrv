package com.eighthlight.jhttpsrv.constants;

import java.util.*;

public class MIMETypes {
    public static final String HTML = "text/html";
    public static final String PLAIN_TEXT = "text/plain";
    public static final String JPEG = "image/jpeg";
    public static final String JSON = "application/json";
    public static final String GZIP = "application/x-gzip";
    public static final String OCTET_STREAM = "application/octet-stream";

    private static final Map<String, String> MIMEType_FileExtensions;
    static {
        Map<String, String> myMap = new HashMap<String, String>();

        myMap.put("html", HTML);
        myMap.put("htm", HTML);
        myMap.put("txt", PLAIN_TEXT);
        myMap.put("jpeg", JPEG);
        myMap.put("jpg", JPEG);
        myMap.put("gz", GZIP);
        myMap.put("json", JSON);

        MIMEType_FileExtensions = Collections.unmodifiableMap(myMap);
    }

    public static String FileExt2MIMEType(String fileExt) throws IllegalArgumentException {
        if(fileExt.length() > 0 && fileExt.charAt(0) == '.')
            fileExt = fileExt.substring(1);

        String MIMEType = MIMEType_FileExtensions.get(fileExt);
        if(MIMEType != null)
            return MIMEType;
        return OCTET_STREAM;
    }
}