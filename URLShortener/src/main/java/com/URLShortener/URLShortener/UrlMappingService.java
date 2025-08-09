package com.URLShortener.URLShortener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.time.LocalDateTime;
import java.util.Random;

@Service
public class UrlMappingService {

    @Autowired
    private UrlMappingRepository urlMappingRepository;

    private static final String ALLOWED_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int SHORT_CODE_LENGTH = 7;

    public UrlMapping shortenUrl(String longUrl) {
        String shortCode;

        do {
            shortCode = generateRandomShortCode();
        } while (urlMappingRepository.findByShortCode(shortCode).isPresent());

        UrlMapping urlMapping = new UrlMapping();
        urlMapping.setLongUrl(longUrl);
        urlMapping.setShortCode(shortCode);
        urlMapping.setCreatedAt(LocalDateTime.now());

        return urlMappingRepository.save(urlMapping);
    }

    public Optional<UrlMapping> getLongUrl(String shortCode) {
        return urlMappingRepository.findByShortCode(shortCode);
    }

    public String generateRandomShortCode() {
        Random random = new Random();
        StringBuilder sb=new StringBuilder(SHORT_CODE_LENGTH);
        for (int i=0; i<SHORT_CODE_LENGTH; i++) {
            int randomIndex=random.nextInt(ALLOWED_CHARACTERS.length());
            sb.append(ALLOWED_CHARACTERS.charAt(randomIndex));
        }

        return sb.toString();
    }
}
