package com.proxy.thumborapi.thumbor;

import feign.Param;
import feign.RequestLine;

public interface ThumborResizeService {

    @RequestLine("GET /healthcheck")
    String health();

    @RequestLine("GET /unsafe/left/top/filters:format(jpeg)/{imageUrl}")
    byte[] originalImage(@Param("imageUrl") String imageUrl);

    @RequestLine("GET /unsafe/left/top/filters:format(webp)/{imageUrl}")
    byte[] originalImageAsWebp(@Param("imageUrl") String imageUrl);

    @RequestLine("GET /unsafe/trim/fit-in/{size}/filters:fill(white):no_upscale():format(jpeg)/{imageUrl}")
    byte[] resizeImage(@Param("size") String size, @Param("imageUrl") String imageUrl);

    @RequestLine("GET /unsafe/trim/fit-in/{size}/filters:fill(white):no_upscale():format(webp)/{imageUrl}")
    byte[] resizeImageWebP(@Param("size") String size, @Param("imageUrl") String imageUrl);
}
