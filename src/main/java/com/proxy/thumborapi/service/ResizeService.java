package com.proxy.thumborapi.service;

import com.proxy.thumborapi.config.ThumborServiceProperties;
import com.proxy.thumborapi.model.ImageSize;
import com.proxy.thumborapi.model.Ratio;
import com.proxy.thumborapi.thumbor.ThumborResizeService;
import feign.Feign;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ResizeService {

    private final static int HTTPS_SIZE = "https://".length();
    private final static String ORIGINAL = "0x0";
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ResizeService.class);
    private final ResourceService resourceService;
    private final ThumborResizeService thumborResizeService;
    private final ConcurrentHashMap<String, AtomicInteger> countResizes = new ConcurrentHashMap<>();

    @Autowired
    public ResizeService(ResourceService resourceService, ThumborServiceProperties thumborServiceProps) {
        this(resourceService,
                Feign.builder().target(
                        ThumborResizeService.class,
                        thumborServiceProps.getServiceUrl()
                )
        );
    }


    public ResizeService(ResourceService resourceService, ThumborResizeService thumborResizeService) {
        this.resourceService = resourceService;
        this.thumborResizeService = thumborResizeService;
    }

    public byte[] resize(String size, String imagePath) {
        return resize(size, imagePath, false);
    }

    public byte[] resize(String source, String alias, String imagePath, boolean webp) {
        String size = this.resourceService.sizeBySourceAndAlias(source, alias);

        if (size == null) {
            return null;
        }

        return resize(size, imagePath, webp);
    }

    public byte[] resize(String source, String alias, String imagePath) {
        return this.resize(source, alias, imagePath, false);
    }

    public byte[] resize(String size, String imagePath, boolean webp) {
        addCount(size);
        Long start = System.currentTimeMillis();
        log.info("Resizing {} to {} ", imagePath, size);

        // TODO Check if size is in the list of accepted sizes
        URL imageUrl = resourceService.generateTemporaryUrlForImage(imagePath);
        String transformedImageUrl = imageUrl.toString().substring(HTTPS_SIZE);

        log.info("URL generated in {} ms.", System.currentTimeMillis() - start);

        // TODO Handle Feign issues
        byte[] bytes;
        if (webp) {
            if (ORIGINAL.equals(size)) {
                bytes = thumborResizeService.originalImageAsWebp(transformedImageUrl);
            } else {
                bytes = thumborResizeService.resizeImageWebP(size, transformedImageUrl);
            }

        } else {
            if (ORIGINAL.equals(size)) {
                bytes = thumborResizeService.originalImage(transformedImageUrl);
            } else {
                bytes = thumborResizeService.resizeImage(size, transformedImageUrl);
            }
        }
        log.info("Resizing made in {} ms.", System.currentTimeMillis() - start);

        return bytes;
    }

    private void addCount(String size) {
        AtomicInteger count = countResizes.get(size);
        if (count == null) {
            count = new AtomicInteger();
            countResizes.put(size, count);
        }
        count.incrementAndGet();
    }

    public ConcurrentHashMap<String, AtomicInteger> getCountResizes() {
        return countResizes;
    }

    public byte[] resize(Ratio ratio, ImageSize imageSize, int pixelSize, String imagePath) {
        String size = getSizeForThumbor(ratio, imageSize);
        return resize(size, imagePath);
    }

    /**
     * Work in progress...
     *
     * @param ratio     Ratio
     * @param imageSize ImageSize
     * @return String
     */
    private String getSizeForThumbor(Ratio ratio, ImageSize imageSize) {
        String width = "0";
        String height = "0";
        // Find out the image size that was asked
        if (imageSize != ImageSize.orig) {
            switch (ratio) {
                case PRESERVE_W:
                    width = "orig";
                    height = imageSize.size();
                    break;
                case PRESERVE_H:
                    width = imageSize.size();
                    height = "orig";
                    break;
                case SQUARE:
                    width = height = imageSize.size();
                    break;
                default:


                    break;
            }

        }
        return width + "x" + height;
    }
}