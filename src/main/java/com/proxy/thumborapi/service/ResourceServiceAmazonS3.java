package com.proxy.thumborapi.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.util.IOUtils;
import com.proxy.thumborapi.config.SourcesServiceProperties;
import com.proxy.thumborapi.config.ThumborServiceProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Primary
@Service
public class ResourceServiceAmazonS3 implements ResourceService {

    private final SourcesServiceProperties sourcesServiceProperties;
    private final AmazonS3 amazonS3;
    private final String bucketName;

    public ResourceServiceAmazonS3(
            AmazonS3 amazonS3,
            ThumborServiceProperties thumborServiceProps,
            SourcesServiceProperties sourcesServiceProperties
    ) {
        this.amazonS3 = amazonS3;
        this.bucketName = thumborServiceProps.getS3BucketName();
        this.sourcesServiceProperties = sourcesServiceProperties;
    }

    @Override
    public List<String> listObjects(String element, String elementName) {
        ObjectListing listing;
        if (StringUtils.hasLength(element)) {
            String prefix = element + "/";
            if (StringUtils.hasLength(elementName)) {
                prefix += elementName + "/";
            }
            listing = amazonS3.listObjects(bucketName, prefix);
        } else {
            listing = amazonS3.listObjects(bucketName);
        }

        List<S3ObjectSummary> objectSummaries = listing.getObjectSummaries();
        return objectSummaries
                .stream()
                .map(S3ObjectSummary::getKey)
                .collect(Collectors.toList());
    }

    @Override
    public byte[] readImage(String imageId) throws IOException {
        S3Object s3Object = amazonS3.getObject(bucketName, imageId);
        byte[] bytes = IOUtils.toByteArray(s3Object.getObjectContent());
        s3Object.close();
        return bytes;
    }

    @Override
    public URL generateTemporaryUrlForImage(String imageId) {
        // Get the image
        Instant expiration = Instant.now().plus(4, ChronoUnit.HOURS);

        return amazonS3
                .generatePresignedUrl(
                        bucketName, imageId, Date.from(expiration), com.amazonaws.HttpMethod.GET);
    }

    @Override
    public String sizeBySourceAndAlias(String source, String alias) {

        if (!this.sourcesServiceProperties.containsKey(source)) {
            return null;
        }

        if (!this.sourcesServiceProperties.hasSize(source, alias)) {
            return null;
        }

        return this.sourcesServiceProperties.getSize(source, alias);

    }
}
