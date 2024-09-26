package net.engineeringdigest.journalApp.Controllers;

import net.engineeringdigest.journalApp.Service.SportApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/public/sport")
public class SportApiController {
    @Autowired
    private SportApiService sportApiService;

    @GetMapping
    public ResponseEntity<String> getLinedIndata() {
        return sportApiService.makeGetRequestWithHeaders("https://linkedin-data-api.p.rapidapi.com/?username=adamselipsky");
    }

    @PostMapping
    public ResponseEntity<?> getUserInfo(){
        return sportApiService.makePostRequest("https://linkedin-data-api.p.rapidapi.com/search-posts-by-hashtag","{\\\"hashtag\\\":\\\"golang\\\",\\\"sortBy\\\":\\\"REV_CHRON\\\",\\\"start\\\":\\\"0\\\",\\\"paginationToken\\\":\\\"\\\"}");
    }
}
