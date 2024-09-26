package net.engineeringdigest.journalApp.Service;

import net.engineeringdigest.journalApp.Repo.FootballCategoryRepository;
import net.engineeringdigest.journalApp.entity.FootballCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class SportApiService {

    private final RestTemplate restTemplate = new RestTemplate();

    public ResponseEntity<String> makeGetRequestWithHeaders(String url) {
        // Set up the headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-rapidapi-key", "3dce9a4405msh2c075ea66c19e31p1ea634jsnbe2025e47b4f");
        headers.set("x-rapidapi-host", "linkedin-data-api.p.rapidapi.com");
        headers.set("Accept", "application/json");

        // Create HttpEntity (with headers, no body for GET)
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Make the GET request with exchange
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        HttpStatus statusCode = response.getStatusCode();   // Get the status code
        HttpHeaders responseHeaders = response.getHeaders(); // Get the response headers
        String body = response.getBody();

        System.out.println(statusCode);
        // Return the entire response (headers, body, and status)
        return response;
    }

    public ResponseEntity<String> makePostRequest(String url,String body) {
        // Set up the headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-rapidapi-key", "3dce9a4405msh2c075ea66c19e31p1ea634jsnbe2025e47b4f");
        headers.set("x-rapidapi-host", "linkedin-data-api.p.rapidapi.com");
        headers.set("Content-Type", "application/json");


        // Create HttpEntity (with headers and body for POST)
        HttpEntity<Object> entity = new HttpEntity<>(body,headers);

        // Make the POST request with exchange
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        // Return the entire response (headers, body, and status)
        return response;
    }

}
