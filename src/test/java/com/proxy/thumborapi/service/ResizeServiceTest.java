package com.proxy.thumborapi.service;

import com.proxy.thumborapi.model.ImageSize;
import com.proxy.thumborapi.model.Ratio;
import com.proxy.thumborapi.thumbor.ThumborResizeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.net.MalformedURLException;
import java.net.URL;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ResizeServiceTest {

    @Mock
    private ResourceService resourceService;

    @Mock
    private ThumborResizeService thumborResizeService;

    private ResizeService resizeServiceAmazonS3;

    private URL amazonUrl;

    @Before
    public void setUp() throws MalformedURLException {
        amazonUrl = new URL("https://test-product-images.s3-eu-west-1.amazonaws.com/sampleImageHash.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20170821T102202Z&X-Amz-SignedHeaders=host&X-Amz-Expires=43199&X-Amz-Credential=SAMPLECREDENCIALFeu-west-1%2Fs3%2Faws4_request&X-Amz-Signature=SAMPLESIGNATURE");
        resizeServiceAmazonS3 = new ResizeService(resourceService, thumborResizeService);
        when(resourceService.generateTemporaryUrlForImage(anyString())).thenReturn(amazonUrl);
    }


    @Test
    public void resizeLegacy() throws Exception {
        resizeServiceAmazonS3.resize("0x0", "imageId.jpg");
    }

    @Test
    public void resize() throws Exception {

        byte[] resize = resizeServiceAmazonS3.resize(Ratio.SQUARE, ImageSize.m, 1, "imageId.jpg");


    }

}