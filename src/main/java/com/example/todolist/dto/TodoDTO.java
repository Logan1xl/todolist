package com.example.todolist.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) pour l'entité Todo.
 *
 * Cette classe est utilisée pour transférer les données entre le client et le serveur,
 * permettant de contrôler les informations exposées via l'API.
 *
 * Elle contient également les annotations Swagger pour générer une documentation précise.
 *
 * @author Wulfrid MBONGO
 * @version 1.0
 * @since 2025-11-16
 */
@Schema(
        name = "TodoDTO",
        description = "Représente une tâche (Todo) avec tous ses détails",
        example = "{\n" +
                "  \"id\": 1,\n" +
                "  \"titre\": \"Faire les courses\",\n" +
                "  \"description\": \"Acheter du lait, du pain et des œufs\",\n" +
                "  \"terminee\": false,\n" +
                "  \"dateCreation\": \"2025-11-16T10:30:00\",\n" +
                "  \"dateModification\": \"2025-11-16T10:30:00\"\n" +
                "}"
)
public class TodoDTO {

    /**
     * Identifiant unique de la tâche.
     */
    @Schema(
            name = "id",
            description = "Identifiant unique généré automatiquement par la base de données",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long id;

    /**
     * Titre de la tâche.
     * Obligatoire pour la création et la modification.
     */
    @NotBlank(message = "Le titre ne peut pas être vide")
    @Size(max = 100, message = "Le titre ne peut pas dépasser 100 caractères")
    @Schema(
            name = "titre",
            description = "Le titre ou l'intitulé de la tâche",
            example = "Faire les courses",
            minLength = 1,
            maxLength = 100,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String titre;

    /**
     * Description détaillée de la tâche.
     * Optionnelle.
     */
    @Size(max = 500, message = "La description ne peut pas dépasser 500 caractères")
    @Schema(
            name = "description",
            description = "Une description détaillée et optionnelle de la tâche",
            example = "Acheter du lait, du pain et des œufs",
            maxLength = 500,
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private String description;

    /**
     * Statut d'accomplissement de la tâche.
     */
    @Schema(
            name = "terminee",
            description = "Indique si la tâche est terminée (true) ou non (false). Par défaut : false",
            example = "false",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private Boolean terminee;

    /**
     * Date et heure de création de la tâche.
     */
    @Schema(
            name = "dateCreation",
            description = "Date et heure de création automatique de la tâche au format ISO-8601",
            example = "2025-11-16T10:30:00",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private LocalDateTime dateCreation;

    /**
     * Date et heure de la dernière modification.
     */
    @Schema(
            name = "dateModification",
            description = "Date et heure de la dernière modification de la tâche au format ISO-8601",
            example = "2025-11-16T10:30:00",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private LocalDateTime dateModification;

    /**
     * Constructeur par défaut.
     */
    public TodoDTO() {
    }

    /**
     * Constructeur avec tous les paramètres.
     *
     * @param id L'identifiant de la tâche
     * @param titre Le titre de la tâche
     * @param description La description de la tâche
     * @param terminee Le statut de la tâche
     * @param dateCreation La date de création
     * @param dateModification La date de modification
     */
    public TodoDTO(Long id, String titre, String description, Boolean terminee,
                   LocalDateTime dateCreation, LocalDateTime dateModification) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.terminee = terminee;
        this.dateCreation = dateCreation;
        this.dateModification = dateModification;
    }

    // ==================== GETTERS ET SETTERS ====================

    /**
     * @return L'identifiant de la tâche
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id L'identifiant à définir
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return Le titre de la tâche
     */
    public String getTitre() {
        return titre;
    }

    /**
     * @param titre Le titre à définir
     */
    public void setTitre(String titre) {
        this.titre = titre;
    }

    /**
     * @return La description de la tâche
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description La description à définir
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return true si la tâche est terminée, false sinon
     */
    public Boolean getTerminee() {
        return terminee;
    }

    /**
     * @param terminee Le statut à définir
     */
    public void setTerminee(Boolean terminee) {
        this.terminee = terminee;
    }

    /**
     * @return La date de création de la tâche
     */
    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    /**
     * @param dateCreation La date de création à définir
     */
    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    /**
     * @return La date de dernière modification
     */
    public LocalDateTime getDateModification() {
        return dateModification;
    }

    /**
     * @param dateModification La date de modification à définir
     */
    public void setDateModification(LocalDateTime dateModification) {
        this.dateModification = dateModification;
    }
}