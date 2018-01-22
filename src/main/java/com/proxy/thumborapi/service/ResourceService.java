package com.proxy.thumborapi.service;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public interface ResourceService {

    List<String> listObjects(String element, String elementName);

    byte[] readImage(String imageId) throws IOException;

    URL generateTemporaryUrlForImage(String imageId);

    String sizeBySourceAndAlias(String source, String alias);

}
