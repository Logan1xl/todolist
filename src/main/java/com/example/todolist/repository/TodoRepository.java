package com.example.todolist.repository;


import com.example.todolist.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Interface Repository pour gérer les opérations de base de données sur l'entité Todo.
 *
 * Cette interface étend JpaRepository qui fournit les méthodes CRUD de base
 * et permet de définir des requêtes personnalisées.
 *
 * @author MBONGO BOLLO
 * @version 1.0
 * @since 2025-11-16
 */
@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

    /**
     * Recherche toutes les tâches par leur statut de complétion.
     *
     * @param terminee Le statut recherché (true pour terminées, false pour non terminées)
     * @return Liste des tâches correspondant au statut
     */
    List<Todo> findByTerminee(Boolean terminee);

    /**
     * Recherche des tâches dont le titre contient une chaîne de caractères (insensible à la casse).
     *
     * @param titre La chaîne de caractères à rechercher dans le titre
     * @return Liste des tâches dont le titre contient la chaîne recherchée
     */
    List<Todo> findByTitreContainingIgnoreCase(String titre);

    /**
     * Compte le nombre de tâches terminées.
     *
     * @return Le nombre de tâches avec terminee = true
     */
    @Query("SELECT COUNT(t) FROM Todo t WHERE t.terminee = true")
    Long countTerminees();

    /**
     * Compte le nombre de tâches non terminées.
     *
     * @return Le nombre de tâches avec terminee = false
     */
    @Query("SELECT COUNT(t) FROM Todo t WHERE t.terminee = false")
    Long countNonTerminees();
}