package com.fageniuscode.mavendemo.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;

/**
 * @author Ibrahima
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private int id;
    @NotNull(message = "Le nom ne doit pas être vide")
    private String nom;
    @NotNull(message = "Le prénom ne doit pas être vide")
    private String prenom;
    @NotNull(message = "L'adresse email ne doit pas être vide")
    private String email;
    @NotNull(message = "Le mot de passe ne doit pas être vide")
    private String password;
    @NotNull(message = "L'état ne doit pas être vide")
    private int etat;
}
