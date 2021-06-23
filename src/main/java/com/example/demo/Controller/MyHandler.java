package com.example.demo.Controller;

import com.example.demo.DTO.UserWebSocket;
import com.google.gson.Gson;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class MyHandler extends TextWebSocketHandler {
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) { // obj WebSocket ???

        String inputData = new String(message.asBytes());
        UserWebSocket userWebSocket = new Gson().fromJson(inputData, UserWebSocket.class);

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("username", userWebSocket.getUsername());
        formData.add("password", userWebSocket.getPassword());

        WebClient webClient = WebClient.create();
        //TokenResponse tokenResponse =
                webClient
                .post()
                .uri("http://localhost:8080/login?username="+userWebSocket.getUsername() +
                        "&password="+userWebSocket.getPassword())
                //.header(HttpHeaders.AUTHORIZATION, "Basic dGVzdDpjbGllbnQtc2VjcmV0")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                //.body(BodyInserters.fromFormData(formData))
                .retrieve();

    }
}
