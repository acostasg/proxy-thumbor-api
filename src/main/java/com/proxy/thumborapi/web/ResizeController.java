package com.proxy.thumborapi.web;

import com.proxy.thumborapi.exception.BadRequestException;
import com.proxy.thumborapi.exception.NotFoundException;
import com.proxy.thumborapi.model.ImageSize;
import com.proxy.thumborapi.model.Ratio;
import com.proxy.thumborapi.service.ResizeService;
import com.proxy.thumborapi.service.ResizeServiceBuilder;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
public class ResizeController {

    private final ResizeServiceBuilder resizeServiceBuilder;
    private ResizeService resizeService;

    @Autowired
    public ResizeController(
            ResizeService resizeService,
            ResizeServiceBuilder resizeServiceBuilder
    ) {
        this.resizeService = resizeService;
        this.resizeServiceBuilder = resizeServiceBuilder;
    }

    @RequestMapping(value = "/resize/{ratioKey}/{imageSize}/{pixelRatio}/{imageId:.*}", method = RequestMethod.GET)
    public byte[] resize(@PathVariable String ratioKey, @PathVariable ImageSize imageSize, int pixelRatio, String imageId) throws IOException {

        Ratio ratio = Ratio.fromKey(ratioKey);
        if (ratio == null) {
            throw new BadRequestException("Ratio has to be in the approved list");
        }

        return resizeService.resize(ratio, imageSize, pixelRatio, imageId);
    }

    @RequestMapping(value = "/resize/{source}/{alias}//{imageId:.*}", method = RequestMethod.GET)
    public byte[] resize(
            @PathVariable String source,
            @PathVariable String alias,
            @PathVariable String imageId,
            @RequestParam(required = false, defaultValue = "false") boolean webp
    ) throws IOException {

        try {
            byte[] image = resizeServiceBuilder.buildImages()
                    .resize(source, alias, imageId, webp);

            if (image == null)
                throw new BadRequestException("Invalid source or alias");

            return image;
        } catch (FeignException e) {
            if (e.status() == 404) {
                throw new NotFoundException("Image not found");
            }
            throw e;
        }

    }

}
