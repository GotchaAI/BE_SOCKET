
    const schema = {
  "asyncapi": "2.6.0",
  "info": {
    "title": "Gotcha WebSocket API",
    "version": "1.0.0",
    "description": "WebSocket 기반 실시간 게임 통신 명세"
  },
  "servers": {
    "production": {
      "url": "wss://gotcha.com/ws-connect",
      "protocol": "ws",
      "description": "WebSocket 핸드셰이크 엔드포인트"
    }
  },
  "channels": {
    "room/join": {
      "subscribe": {
        "operationId": "onUserJoin",
        "summary": "유저가 방에 입장",
        "message": {
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
          },
          "x-parser-message-name": "<anonymous-message-1>"
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
  