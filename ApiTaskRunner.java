import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

@Component
public class ApiTaskRunner implements CommandLineRunner {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Starting application task...");

        // Step 1: Get the Webhook URL and JWT Token
        String generateWebhookUrl = "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        // Replace with your actual details
        Map<String, String> requestBody = Map.of(
            "name", "Your Full Name",
            "regNo", "YOUR_REG_NUMBER",
            "email", "your.email@example.com"
        );

        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);
        
        // Post the request and get the response as a Map
        Map<String, String> response = restTemplate.postForObject(generateWebhookUrl, requestEntity, Map.class);
        
        String webhookUrl = response.get("webhook");
        String accessToken = response.get("accessToken");
        
        System.out.println("Received Webhook URL: " + webhookUrl);
        System.out.println("Received Access Token: " + accessToken);
        
        // Step 2: Solve the SQL Problem and Submit the Query
        // Replace this with the actual SQL query you solved
        String finalQuery = "YOUR_SQL_QUERY_HERE";

        HttpHeaders submissionHeaders = new HttpHeaders();
        submissionHeaders.setContentType(MediaType.APPLICATION_JSON);
        submissionHeaders.setBearerAuth(accessToken); // Set the JWT as a Bearer token

        Map<String, String> submissionBody = Map.of(
            "finalQuery", finalQuery
        );

        HttpEntity<Map<String, String>> submissionEntity = new HttpEntity<>(submissionBody, submissionHeaders);

        // Submit the final solution
        String submissionUrl = "https://bfhldevapigw.healthrx.co.in/hiring/testWebhook/JAVA";
        String submissionResponse = restTemplate.postForObject(submissionUrl, submissionEntity, String.class);
        
        System.out.println("Submission Response: " + submissionResponse);
        System.out.println("Application task completed.");
    }
}