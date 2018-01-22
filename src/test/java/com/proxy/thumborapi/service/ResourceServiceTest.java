package com.proxy.thumborapi.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectListing;
import com.proxy.thumborapi.config.SourcesServiceProperties;
import com.proxy.thumborapi.config.ThumborServiceProperties;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ResourceServiceTest {

    @Mock
    private AmazonS3 amazonS3;

    private ResourceService resourceService;

    @Before
    public void setUp() throws Exception {
        ThumborServiceProperties properties = new ThumborServiceProperties();
        SourcesServiceProperties sourcesServiceProperties = new SourcesServiceProperties();
        properties.setS3BucketName("myBucket");
        resourceService = new ResourceServiceAmazonS3(amazonS3, properties, sourcesServiceProperties);
        ObjectListing ol = new ObjectListing();
        when(amazonS3.listObjects(anyString(), anyString())).thenReturn(ol);
    }

    @Test
    public void listObjects() throws Exception {
        resourceService.listObjects("event", "test-element");
    }

    @Test
    public void readImage() throws Exception {
    }

    @Test
    public void generateTemporaryUrlForImage() throws Exception {
        resourceService.generateTemporaryUrlForImage("imageId");
    }

}