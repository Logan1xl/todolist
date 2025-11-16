package com.example.todolist.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * Entité représentant une tâche (Todo) dans la base de données.
 *
 * Cette classe utilise JPA pour mapper la table 'todos' en base de données.
 * Chaque instance représente une tâche avec son titre, description, statut et dates.
 *
 * @author MBONGO BOLLO
 * @version 1.0
 * @since 2025-11-16
 */
@Entity
@Table(name = "todos")
public class Todo {

    /**
     * Identifiant unique de la tâche.
     * Généré automatiquement par la base de données.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Titre de la tâche.
     * Obligatoire et limité à 100 caractères.
     */
    @NotBlank(message = "Le titre ne peut pas être vide")
    @Size(max = 100, message = "Le titre ne peut pas dépasser 100 caractères")
    @Column(nullable = false, length = 100)
    private String titre;

    /**
     * Description détaillée de la tâche.
     * Optionnelle, limitée à 500 caractères.
     */
    @Size(max = 500, message = "La description ne peut pas dépasser 500 caractères")
    @Column(length = 500)
    private String description;

    /**
     * Statut d'accomplissement de la tâche.
     * Par défaut : false (non terminée).
     */
    @Column(nullable = false)
    private Boolean terminee = false;

    /**
     * Date et heure de création de la tâche.
     * Définie automatiquement lors de la création.
     */
    @Column(name = "date_creation", nullable = false, updatable = false)
    private LocalDateTime dateCreation;

    /**
     * Date et heure de la dernière modification.
     * Mise à jour automatiquement à chaque modification.
     */
    @Column(name = "date_modification")
    private LocalDateTime dateModification;

    /**
     * Constructeur par défaut requis par JPA.
     */
    public Todo() {
    }

    /**
     * Constructeur avec paramètres pour faciliter la création d'instances.
     *
     * @param titre Le titre de la tâche
     * @param description La description de la tâche
     * @param terminee Le statut de la tâche
     */
    public Todo(String titre, String description, Boolean terminee) {
        this.titre = titre;
        this.description = description;
        this.terminee = terminee;
    }

    /**
     * Méthode appelée automatiquement avant la persistance de l'entité.
     * Initialise la date de création.
     */
    @PrePersist
    protected void onCreate() {
        dateCreation = LocalDateTime.now();
        dateModification = LocalDateTime.now();
    }

    /**
     * Méthode appelée automatiquement avant la mise à jour de l'entité.
     * Met à jour la date de modification.
     */
    @PreUpdate
    protected void onUpdate() {
        dateModification = LocalDateTime.now();
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

    /**
     * Représentation textuelle de l'objet Todo.
     *
     * @return Une chaîne décrivant la tâche
     */
    @Override
    public String toString() {
        return "Todo{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", description='" + description + '\'' +
                ", terminee=" + terminee +
                ", dateCreation=" + dateCreation +
                ", dateModification=" + dateModification +
                '}';
    }
}