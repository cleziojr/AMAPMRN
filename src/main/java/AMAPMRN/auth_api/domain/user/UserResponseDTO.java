package AMAPMRN.auth_api.domain.user;

public record UserResponseDTO(
        String id,
        String login,
        UserRole role,
        boolean active,
        String associadoId,
        String nomeAssociado
) {
    public UserResponseDTO(User user){
        this(
                user.getId(),
                user.getLogin(),
                user.getRole(),
                user.isEnabled(),
                (user.getAssociado() != null) ? user.getAssociado().getId() : null,
                (user.getAssociado() != null) ? user.getAssociado().getNome() : "Não vinculado"
        );
    }
}