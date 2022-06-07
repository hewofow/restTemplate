package ru.kata.resttemplate.demo;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import ru.kata.resttemplate.demo.models.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DemoApplication {
    private static String baseUrl = "http://94.198.50.185:7081/api/users";
    private static RestTemplate restTemplate = new RestTemplate();
    private static List<String> cookies;

    public static void main(String[] args) {
        exchangeMethodsOfRestTemplate();
    }

    private static <T> List<String> getCookies(ResponseEntity<T> responseEntity) {
        return responseEntity.getHeaders().get("Set-Cookie");
    }

    private static void exchangeMethodsOfRestTemplate() {
        Set<HttpMethod> methods = restTemplate.optionsForAllow(baseUrl);
        System.out.println(methods);
        System.out.println(methods.containsAll(Arrays.asList(HttpMethod.GET,
                HttpMethod.HEAD, HttpMethod.POST, HttpMethod.PUT, HttpMethod.OPTIONS)));


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<User> requestEntity = new HttpEntity<>(headers);
        cookies = getUsersByExchangeMethod(requestEntity);

//        headers.set("Cookie", cookies.stream().collect(Collectors.joining(";")));
//        requestEntity = new HttpEntity<>(new User(3L, "James", "Brown", (byte) 100), headers);
//        addUserByExchangeMethod(requestEntity);
//
//        requestEntity = new HttpEntity<>(new User(3L, "Thomas", "Shelby", (byte) 100), headers);
//        changeUserByExchangeMethod(requestEntity);
//
//        requestEntity = new HttpEntity<>(headers);
//        deleteUserByExchangeMethod(requestEntity);
    }

    private static <T> void deleteUserByExchangeMethod(HttpEntity<T> requestEntity) {
        ResponseEntity<String> responseEntity = restTemplate.exchange(baseUrl + "/3",
                HttpMethod.DELETE,
                requestEntity,
                String.class);

        System.out.println("Status code is: " + responseEntity.getStatusCode());
        System.out.println("Response body is: " + responseEntity.getBody());
        System.out.println("Response headers is: " + responseEntity.getHeaders() + "\n");
    }

    private static <T> void changeUserByExchangeMethod(HttpEntity<T> requestEntity) {
        ResponseEntity<String> responseEntity = restTemplate.exchange(baseUrl,
                HttpMethod.PUT,
                requestEntity,
                String.class);

        System.out.println("Status code is: " + responseEntity.getStatusCode());
        System.out.println("Response body is: " + responseEntity.getBody());
        System.out.println("Response headers is: " + responseEntity.getHeaders() + "\n");
    }

    private static <T> void addUserByExchangeMethod(HttpEntity<T> requestEntity) {
        ResponseEntity<String> responseEntity = restTemplate.exchange(baseUrl,
                HttpMethod.POST,
                requestEntity,
                String.class);

        System.out.println("Status code is: " + responseEntity.getStatusCode());
        System.out.println("Response body is: " + responseEntity.getBody());
        System.out.println("Response headers is: " + responseEntity.getHeaders() + "\n");
    }

    private static <T> List<String> getUsersByExchangeMethod(HttpEntity<T> requestEntity) {
        ResponseEntity<User[]> responseEntity = restTemplate.exchange(baseUrl,
                HttpMethod.GET,
                requestEntity,
                User[].class);
//        ResponseEntity<User[]> responseEntity = restTemplate.getForEntity(baseUrl, User[].class);
//        User[] userList = restTemplate.getForObject(baseUrl, User[].class);

        System.out.println("Status code is: " + responseEntity.getStatusCode());
        System.out.print("Response body is: ");

        User[] userList = responseEntity.getBody();
        if (userList != null) {
            Arrays.stream(userList).forEach(System.out::print);
        } else {
            System.out.print("empty");
        }
        System.out.println("Response headers is: " + responseEntity.getHeaders() + "\n");

        return getCookies(responseEntity);
//        return new ArrayList<>();
    }
}
