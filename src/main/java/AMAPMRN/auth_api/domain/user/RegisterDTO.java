package AMAPMRN.auth_api.domain.user;

public record RegisterDTO(
        String login,
        String password,
        UserRole role,
        String associadoId
) {
}