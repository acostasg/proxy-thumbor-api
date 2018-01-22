package com.proxy.thumborapi.service;

import com.proxy.thumborapi.config.SourcesServiceProperties;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.net.URL;

@RunWith(MockitoJUnitRunner.class)
public class ResourceServiceImagesTest {

    private final SourcePropertiesMother sourcePropertiesMother = new SourcePropertiesMother();
    private ResourceService resourceService;

    @Before
    public void setUp() throws Exception {

        SourcesServiceProperties sourcesServiceProperties = new SourcesServiceProperties();

        sourcesServiceProperties.setSources(sourcePropertiesMother.getProperties());

        this.resourceService = new ResourceServiceImages(
                sourcesServiceProperties
        );

    }

    @Test
    public void generateTemporaryUrlForImage() throws Exception {
        resourceService.sizeBySourceAndAlias("es", "test");
        URL url = resourceService.generateTemporaryUrlForImage("imageId");

        Assert.assertNotNull(url);

    }

    @Test
    public void sizeBySourceAndAlias() throws Exception {
        resourceService.sizeBySourceAndAlias("es", "test");
    }

    @Test
    public void sizeBySourceAndAliasException() throws Exception {
        resourceService.sizeBySourceAndAlias("es", "fail");
    }

}