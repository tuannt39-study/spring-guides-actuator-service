package vn.its.gsactuatorservice.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import vn.its.gsactuatorservice.model.Greeting;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class GreetingController {
    private static final String template = "Hello, %s !";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/")
    @ResponseBody
    public String home(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        String host = request.getServerName();
        String endpointBasePath = "/management";
        StringBuilder sb = new StringBuilder();
        sb.append("<h2>Sprig Boot Actuator</h2>");
        sb.append("<ul>");
        String url = "http://" + host + ":8094" + contextPath + endpointBasePath;
        sb.append("<li><a href='" + url + "'>" + url + "</a></li>");
        sb.append("</ul>");
        return sb.toString();
    }

    @GetMapping("/hello")
    @ResponseBody
    public Greeting sayHello(@RequestParam(name = "name", required = false, defaultValue = "World") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

    @GetMapping("/shutdown")
    @ResponseBody
    public String callActuatorShutdown() {
        String url = "http://tuannt.com:8094/management/shutdown";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> requestBody = new HttpEntity<>("", headers);
        String e = restTemplate.postForObject(url, requestBody, String.class);
        return "Result: " + e;
    }
}
