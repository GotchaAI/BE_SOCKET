package spring.socket_server.domain.room.dto;

import spring.socket_server.domain.game.enumType.AiMode;
import spring.socket_server.domain.game.enumType.GameMode;

public record CreateRoomRequest(
        String title,
        boolean hasPassword,
        String password,
        AiMode aimode,
        GameMode gameMode
) {}
