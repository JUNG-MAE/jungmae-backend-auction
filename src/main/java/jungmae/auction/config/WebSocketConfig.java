package jungmae.auction.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    // 메시지 브로커 설정
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {

        // 클라이언트가 구독할 수 있는 간단한 메시지 브로커를 활성화
        // /topic: 퍼블리시-구독 방식으로 여러 클라이언트에게 메시지를 전송할 때 사용됩니다. 모든 구독자가 메시지를 받을 수 있습니다.
        // /queue: 특정 클라이언트에게 개인적으로 메시지를 전송할 때 사용
        config.enableSimpleBroker("/topic");    // 클라이언트가 메시지를 받을 수 있는 경로

        // 클라이언트가 서버로 메시지를 보낼 때 사용할 경로의 접두사를 설정
        config.setApplicationDestinationPrefixes("/app");   // 클라이언트가 /app/bid/{id} 로 메시지를 보냄.
    }

    // 클라이언트가 WebSocket 연결을 설정할 수 있는 엔드포인트를 등록
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 웹소켓 엔드포인트 "/auction" 설정 및 SockJS사용
        registry.addEndpoint("/auction")    // /auction 이라는 URL로 WebSocket 연결을 수립할 수 있도록 설정
                .setAllowedOrigins("*"); // CORS 설정
//                .withSockJS();   // client가 sockjs로 개발되어 있을 때만 필요, client가 java면 필요없음
    }
}
