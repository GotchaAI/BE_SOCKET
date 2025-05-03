
    const schema = {
  "asyncapi": "2.6.0",
  "id": "urn:gotcha:websocket-api",
  "defaultContentType": "application/json",
  "info": {
    "title": "Gotcha WebSocket API",
    "version": "1.0.0",
    "description": "이 문서는 Gotcha 게임 플랫폼의 실시간 WebSocket(STOMP) 통신 명세서입니다.\nSockJS를 통해 WebSocket 연결을 시도하며, 실패 시 fallback 메커니즘을 지원합니다.\n",
    "contact": {
      "name": "Gotcha Dev Team",
      "email": "gotcha@example.com"
    },
    "license": {
      "name": "MIT",
      "url": "https://opensource.org/licenses/MIT"
    }
  },
  "tags": [
    {
      "name": "game",
      "description": "실시간 게임 로비 및 채팅 관련 채널"
    }
  ],
  "servers": {
    "production": {
      "url": "http://13.209.221.128/ws-connect",
      "protocol": "ws",
      "description": "SockJS 기반 STOMP WebSocket 연결을 지원합니다. 기본적으로 WebSocket(ws) 사용, 실패 시 HTTP long-polling 등으로 fallback 됩니다.\n"
    }
  },
  "channels": {
    "room/join": {
      "subscribe": {
        "operationId": "onUserJoin",
        "summary": "유저가 방에 입장했을 때 서버가 브로드캐스트",
        "message": {
          "name": "UserJoined",
          "payload": {
            "type": "object",
            "properties": {
              "roomId": {
                "type": "string",
                "x-parser-schema-id": "<anonymous-schema-2>"
              },
              "userId": {
                "type": "string",
                "x-parser-schema-id": "<anonymous-schema-3>"
              }
            },
            "x-parser-schema-id": "<anonymous-schema-1>"
          }
        }
      }
    }
  },
  "x-parser-spec-parsed": true,
  "x-parser-api-version": 3,
  "x-parser-spec-stringified": true
};
    const config = {"show":{"sidebar":true},"sidebar":{"showOperations":"byDefault"}};
    const appRoot = document.getElementById('root');
    AsyncApiStandalone.render(
        { schema, config, }, appRoot
    );
  