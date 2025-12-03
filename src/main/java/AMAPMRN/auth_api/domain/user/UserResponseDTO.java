package AMAPMRN.auth_api.domain.user;

public record UserResponseDTO(String id, String login, UserRole role, boolean active) {
    public UserResponseDTO(User user){
        this(user.getId(), user.getLogin(), user.getRole(), user.isEnabled());
    }
}