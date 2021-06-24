package com.example.demo.Controller;

import com.example.demo.DTO.OkResponse;
import com.example.demo.DTO.SocketAndUser;
import com.example.demo.DTO.TokenResponse;
import com.example.demo.DTO.UserWebSocket;
import com.example.demo.Redis.RedisRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

public class MyHandler extends TextWebSocketHandler {

    @Autowired
    private RedisRepository redisRepository;

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {

        String inputData = new String(message.asBytes());
        UserWebSocket userWebSocket = new Gson().fromJson(inputData, UserWebSocket.class);

        WebClient webClient = WebClient.create();

        if(userWebSocket.getUrl().equals("login")) {
            TokenResponse tokenResponse = webClient
                    .post()
                    .uri("http://localhost:8080/login?username=" + userWebSocket.getUsername() +
                            "&password=" + userWebSocket.getPassword())
                    .retrieve()
                    .bodyToMono(TokenResponse.class).block();
            SocketAndUser socketAndUser = new SocketAndUser();
            socketAndUser.setSocketId(session.getId());
            socketAndUser.setUsername(userWebSocket.getUsername());

            redisRepository.add(socketAndUser);

            WebSocketMessage<String> webSocketMessage = new TextMessage("login " + userWebSocket.getUsername());
            session.sendMessage(webSocketMessage);
        }

        if (userWebSocket.getUrl().equals("logout")) {
            OkResponse okResponse = webClient.
                    post()
                    .uri("http://localhost:8080/logout?name="+userWebSocket.getUsername())
                    .retrieve()
                    .bodyToMono(OkResponse.class)
                    .block();
            WebSocketMessage<String> webSocketMessage = new TextMessage("logout " + userWebSocket.getUsername());
            session.sendMessage(webSocketMessage);
            redisRepository.deleteSocket(session.getId());
        }

    }
}
