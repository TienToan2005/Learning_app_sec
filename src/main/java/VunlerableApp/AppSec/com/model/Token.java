package VunlerableApp.AppSec.com.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tokens")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String refreshToken;
    private boolean revoked = false;
    private LocalDateTime expireDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
