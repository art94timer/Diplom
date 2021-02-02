package com.art.dip.utility.client;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("gun")
@Component
public class MailGunClient  {

    @Value("${mail.api.key}")
    private String apiKey;

    @Value("${mail.domain}")
    private String domain;

    @Value("${hello-university}")
    private String from;



    public  JsonNode sendText(String email,String subject,String text) throws UnirestException {
        HttpResponse<JsonNode> request = Unirest.post("https://api.mailgun.net/v3/" + domain+ "/messages")
                .basicAuth("api", apiKey)
                .queryString("from", from)
                .queryString("to", email)
                .queryString("subject",subject)
                .queryString("text", text)
                .asJson();
        return request.getBody();
    }



    public  JsonNode sendHtml(String email,String subject,String text) throws UnirestException {
        HttpResponse<JsonNode> request = Unirest.post("https://api.mailgun.net/v3/" + domain+ "/messages")
			.basicAuth("api", apiKey)
                .queryString("from", from)
                .queryString("to", email)
                .queryString("subject",subject)
                .queryString("html", text)
                .asJson();
        return request.getBody();
    }


}
