package com.proxy.thumborapi.web;

import com.proxy.thumborapi.service.ResizeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping(value = "api", method = RequestMethod.GET)
@Slf4j
@Deprecated
public class LegacyResizeController {

    public static final String WEBP = ".webp";
    private static final MediaType MIME_WEBP = new MediaType("image", "webp");
    private final ResizeService resizeService;

    public LegacyResizeController(ResizeService resizeService) {
        this.resizeService = resizeService;
    }

    @RequestMapping(value = "/resize/{size}/{imageId:.*}")
    public ResponseEntity<byte[]> resize(@PathVariable String size, @PathVariable String imageId,
                                         @RequestParam(required = false, defaultValue = "false") boolean webp) throws IOException {
        return resizeImage(size, webp, imageId);
    }

    @RequestMapping(value = "/resize/{size}/{element}/{imageId:.*}")
    public ResponseEntity<byte[]> resize(@PathVariable String size, @PathVariable String element, @PathVariable String imageId,
                                         @RequestParam(required = false, defaultValue = "false") boolean webp) throws IOException {
        return resizeImage(size, webp, element, imageId);
    }

    @RequestMapping(value = "/resize/{size}/{element}/{elementName}/{imageId:.*}")
    public ResponseEntity<byte[]> resize(@PathVariable String size, @PathVariable String element,
                                         @PathVariable String elementName, @PathVariable String imageId,
                                         @RequestParam(required = false, defaultValue = "false") boolean webp) throws IOException {
        return resizeImage(size, webp, element, elementName, imageId);
    }


    @RequestMapping(value = "/resize/{size}/{element}/{elementName}/{extra}/{imageId:.*}")
    public ResponseEntity<byte[]> resize(@PathVariable String size, @PathVariable String element,
                                         @PathVariable String elementName, @PathVariable String extra, @PathVariable String imageId,
                                         @RequestParam(required = false, defaultValue = "false") boolean webp) throws IOException {
        return resizeImage(size, webp, element, elementName, extra, imageId);
    }

    private ResponseEntity<byte[]> resizeImage(String size, boolean webp, String... elements) {
        String imagePath = String.join("/", elements);


        byte[] resizedImage = resizeService.resize(size, imagePath, webp);
        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl("max-age=31536000,s-maxage=31536000");
        headers.setContentType(webp ? MIME_WEBP : MediaType.IMAGE_JPEG);
        return new ResponseEntity<byte[]>(resizedImage, headers, HttpStatus.OK);

    }

    @RequestMapping(value = "/countResizes")
    public ConcurrentHashMap<String, AtomicInteger> countResizes() {
        return resizeService.getCountResizes();
    }

    @ExceptionHandler
    public void handleIOException(IOException e, HttpServletResponse response) throws IOException {
        log.error("IOException in resizing ", e);
        response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

}
