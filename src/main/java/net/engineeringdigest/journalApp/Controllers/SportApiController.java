package net.engineeringdigest.journalApp.Controllers;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import net.engineeringdigest.journalApp.Service.SportApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/public/sport")
public class SportApiController {
    @Autowired
    private SportApiService sportApiService;
    @Value("${x-rapidapi-key}") public  String Rapidkey;
    @GetMapping
    public ResponseEntity<String> getLinedIndata() {
        return sportApiService.makeGetRequestWithHeaders("https://linkedin-data-api.p.rapidapi.com/?username=adamselipsky");
    }

    @PostMapping
    public ResponseEntity<?> getUserInfo(){
        return sportApiService.makePostRequest("https://linkedin-data-api.p.rapidapi.com/search-posts-by-hashtag","{\\\"hashtag\\\":\\\"golang\\\",\\\"sortBy\\\":\\\"REV_CHRON\\\",\\\"start\\\":\\\"0\\\",\\\"paginationToken\\\":\\\"\\\"}");
    }

    @PostMapping("voice")
    public ResponseEntity<?> getVoiceFromText() {
        try {
            HttpResponse<String> response = Unirest.post("https://api.elevenlabs.io/v1/text-to-speech/CwhRBWXzGAHq8TQ4Fs17")
                    .header("xi-api-key", Rapidkey )
                    .header("Content-Type", "application/json")
                    .body("{\n  \"model_id\": \"eleven_multilingual_v2\",\n  \"text\": \"hiii my name is pavan, how are you???\",\n  \"voice_settings\": {\n    \"stability\": 0.5,\n    \"style\": 0,\n    \"similarity_boost\": 1\n  }\n}")
                    .asString();

            if (response.getStatus() == 200) {
                return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Error: Unable to process the request", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("voice")
    public ResponseEntity<?> getDAta(){
        HttpResponse<String> response = Unirest.get("https://api.elevenlabs.io/v1/models")
                .header("xi-api-key", Rapidkey)
                .asString();

        return new ResponseEntity<>(response.getBody(),HttpStatus.OK);
    }

}
