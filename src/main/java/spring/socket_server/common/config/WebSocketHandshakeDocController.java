package spring.socket_server.common.config;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ws-connect")
@Tag(name = "WebSocket", description = "WebSocket 핸드쉐이크용 엔드포인트")
public class WebSocketHandshakeDocController {

    @Operation(
            summary = "WebSocket STOMP 연결 핸드셰이크",
            description = """
        클라이언트는 HTTP(S)로 `/ws-connect` 엔드포인트에 연결을 시도하고, 
        서버는 WebSocket 핸드셰이크 또는 SockJS fallback을 통해 연결을 수립합니다.

        - 초기 연결 주소 (예시):
          - http://13.209.221.128/ws-connect/

        - 연결 성공 시 STOMP 프로토콜 사용 가능
          - 발행: `/pub/**`
          - 구독: `/sub/**`

        - CORS: `*` 허용 (배포 시 주의 필요)
    """
    )

    @ApiResponses({
            @ApiResponse(responseCode = "101", description = "WebSocket 프로토콜 업그레이드 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "403", description = "CORS 정책 위반")
    })
    @GetMapping
    public void handshakeDoc() {
        // 이 메서드는 실제로 호출되지 않으며 문서화용입니다.
        throw new UnsupportedOperationException("This endpoint is for documentation only.");
    }
}
