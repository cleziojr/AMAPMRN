package AMAPMRN.auth_api.domain.user;

public record UserUpdateDTO(String login, String password, UserRole role, Boolean active) {
}