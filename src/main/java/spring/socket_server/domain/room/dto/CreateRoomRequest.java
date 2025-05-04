package spring.socket_server.domain.room.dto;

import jakarta.validation.constraints.NotBlank;
import spring.socket_server.domain.game.enumType.AiMode;
import spring.socket_server.domain.game.enumType.GameMode;

public record CreateRoomRequest(
        @NotBlank String roomId,
        @NotBlank String title,
        boolean hasPassword,
        String password,
        @NotBlank AiMode Aimode,
        @NotBlank GameMode gameMode
) {}
