package com.URLShortener.URLShortener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Optional;

@RestController
public class UrlMappingController {
    @Autowired
    private UrlMappingService urlMappingService;

    public record UrlShortenRequest(String longUrl) {}

    @PostMapping("/api/v1/shorten")
    public ResponseEntity<String> shortenUrl(@RequestBody UrlShortenRequest request, HttpServletRequest servletRequest) {
        if (request.longUrl() == null || request.longUrl().isEmpty()) {
            return ResponseEntity.badRequest().body("Long URL cannot be empty");
        }

        UrlMapping urlMapping = urlMappingService.shortenUrl(request.longUrl());
        String baseUrl = servletRequest.getRequestURL().toString().replace(servletRequest.getRequestURI(), "");
        String shortUrl = baseUrl + "/" + urlMapping.getShortCode();

        return ResponseEntity.ok(shortUrl);
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirectToLongUrl(@PathVariable String shortCode) {
        Optional<UrlMapping> urlMappingOptional = urlMappingService.getLongUrl(shortCode);
        if (urlMappingOptional.isPresent()) {
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create(urlMappingOptional.get().getLongUrl()));
            return new ResponseEntity<>(headers, HttpStatus.FOUND);
        }

        else {
            return ResponseEntity.notFound().build();
        }
    }
}
