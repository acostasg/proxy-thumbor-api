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
public class ResizeServiceImagesTest {

    @Mock
    private ResourceService resourceService;

    @Mock
    private ThumborResizeService thumborResizeService;

    private ResizeService resizeService;

    @Before
    public void setUp() throws MalformedURLException {
        URL frontCommon = new URL("\n" +
                "https://img-es.static-test.com/get/photo/sample.jpg");
        resizeService = new ResizeService(resourceService, thumborResizeService);
        when(resourceService.generateTemporaryUrlForImage(anyString())).thenReturn(frontCommon);
    }

    @Test
    public void resizeLegacy() throws Exception {
        this.resizeService.resize("0x0", "imageId.jpg");
    }

    @Test
    public void resize() throws Exception {
        byte[] resize = this.resizeService.resize(Ratio.SQUARE, ImageSize.m, 1, "imageId.jpg");
    }

    @Test
    public void resizeSourceAlias() throws Exception {
        byte[] resize = this.resizeService.resize("es", "test", "imageId.jpg");
    }

    @Test
    public void resizeSourceAliasWithWebp() throws Exception {
        byte[] resize = this.resizeService.resize("es", "test", "imageId.jpg", true);
    }
}