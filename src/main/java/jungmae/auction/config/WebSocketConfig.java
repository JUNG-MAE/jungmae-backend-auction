package jungmae.auction.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");    // 메시지 브로커 설정
        config.setApplicationDestinationPrefixes("/app");   // 클라이언트에서 보낼 메시지의 접두사
    }

    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/auction").withSockJS();
    }
}
