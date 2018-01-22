package com.proxy.thumborapi.web;

import com.proxy.thumborapi.service.ResourceService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(method = RequestMethod.GET, value = "images")
public class ImagesController {

    private final ResourceService resourceService;

    public ImagesController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @RequestMapping("/list")
    public List<String> listObjects(@RequestParam(required = false) String element,
                                    @RequestParam(required = false) String elementName) {
        return resourceService.listObjects(element, elementName);
    }

    @RequestMapping(value = "/read/{imageId:.*}", produces = "image/jpeg")
    public byte[] readImage(@PathVariable String imageId) throws IOException {
        return resourceService.readImage(imageId);
    }
}
