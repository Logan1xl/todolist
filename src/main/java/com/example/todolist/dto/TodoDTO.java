package com.example.todolist.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) pour l'entité Todo.
 *
 * Cette classe est utilisée pour transférer les données entre le client et le serveur,
 * permettant de contrôler les informations exposées via l'API.
 *
 * @author MBONGO BOLLO
 * @version 1.0
 * @since 2025-11-16
 */
public class TodoDTO {

    /**
     * Identifiant unique de la tâche.
     */
    private Long id;

    /**
     * Titre de la tâche.
     * Obligatoire pour la création et la modification.
     */
    @NotBlank(message = "Le titre ne peut pas être vide")
    @Size(max = 100, message = "Le titre ne peut pas dépasser 100 caractères")
    private String titre;

    /**
     * Description détaillée de la tâche.
     * Optionnelle.
     */
    @Size(max = 500, message = "La description ne peut pas dépasser 500 caractères")
    private String description;

    /**
     * Statut d'accomplissement de la tâche.
     */
    private Boolean terminee;

    /**
     * Date et heure de création de la tâche.
     */
    private LocalDateTime dateCreation;

    /**
     * Date et heure de la dernière modification.
     */
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