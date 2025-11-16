package com.example.todolist.service;

import com.example.todolist.dto.TodoDTO;
import com.example.todolist.entity.Todo;
import com.example.todolist.exception.ResourceNotFoundException;
import com.example.todolist.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service gérant la logique métier des opérations CRUD sur les tâches (Todo).
 *
 * Cette classe contient toute la logique métier et fait l'intermédiaire
 * entre le contrôleur et le repository.
 *
 * @author MBONGO BOLLO
 * @version 1.0
 * @since 2025-11-16
 */
@Service
@Transactional
public class TodoService {

    private final TodoRepository todoRepository;

    /**
     * Constructeur avec injection de dépendance du repository.
     *
     * @param todoRepository Le repository pour accéder aux données
     */
    @Autowired
    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    /**
     * Récupère toutes les tâches de la base de données.
     *
     * @return Liste de tous les TodoDTO
     */
    public List<TodoDTO> getAllTodos() {
        return todoRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Récupère une tâche spécifique par son identifiant.
     *
     * @param id L'identifiant de la tâche recherchée
     * @return Le TodoDTO correspondant
     * @throws ResourceNotFoundException Si la tâche n'existe pas
     */
    public TodoDTO getTodoById(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tâche non trouvée avec l'id : " + id));
        return convertToDTO(todo);
    }

    /**
     * Crée une nouvelle tâche dans la base de données.
     *
     * @param todoDTO Les données de la tâche à créer
     * @return Le TodoDTO de la tâche créée avec son ID généré
     */
    public TodoDTO createTodo(TodoDTO todoDTO) {
        Todo todo = convertToEntity(todoDTO);
        // Par défaut, une nouvelle tâche n'est pas terminée
        if (todo.getTerminee() == null) {
            todo.setTerminee(false);
        }
        Todo savedTodo = todoRepository.save(todo);
        return convertToDTO(savedTodo);
    }

    /**
     * Met à jour une tâche existante.
     *
     * @param id L'identifiant de la tâche à modifier
     * @param todoDTO Les nouvelles données de la tâche
     * @return Le TodoDTO de la tâche mise à jour
     * @throws ResourceNotFoundException Si la tâche n'existe pas
     */
    public TodoDTO updateTodo(Long id, TodoDTO todoDTO) {
        Todo existingTodo = todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tâche non trouvée avec l'id : " + id));

        // Mise à jour des champs
        existingTodo.setTitre(todoDTO.getTitre());
        existingTodo.setDescription(todoDTO.getDescription());
        existingTodo.setTerminee(todoDTO.getTerminee());

        Todo updatedTodo = todoRepository.save(existingTodo);
        return convertToDTO(updatedTodo);
    }

    /**
     * Supprime une tâche de la base de données.
     *
     * @param id L'identifiant de la tâche à supprimer
     * @throws ResourceNotFoundException Si la tâche n'existe pas
     */
    public void deleteTodo(Long id) {
        if (!todoRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    "Tâche non trouvée avec l'id : " + id);
        }
        todoRepository.deleteById(id);
    }

    /**
     * Récupère toutes les tâches selon leur statut.
     *
     * @param terminee Le statut recherché (true pour terminées, false pour non terminées)
     * @return Liste des TodoDTO correspondant au statut
     */
    public List<TodoDTO> getTodosByStatus(Boolean terminee) {
        return todoRepository.findByTerminee(terminee)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Recherche des tâches par titre (recherche partielle insensible à la casse).
     *
     * @param titre La chaîne de caractères à rechercher dans le titre
     * @return Liste des TodoDTO dont le titre contient la chaîne recherchée
     */
    public List<TodoDTO> searchTodosByTitre(String titre) {
        return todoRepository.findByTitreContainingIgnoreCase(titre)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Marque une tâche comme terminée.
     *
     * @param id L'identifiant de la tâche à marquer comme terminée
     * @return Le TodoDTO de la tâche mise à jour
     * @throws ResourceNotFoundException Si la tâche n'existe pas
     */
    public TodoDTO markAsCompleted(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tâche non trouvée avec l'id : " + id));
        todo.setTerminee(true);
        Todo updatedTodo = todoRepository.save(todo);
        return convertToDTO(updatedTodo);
    }

    /**
     * Obtient des statistiques sur les tâches.
     *
     * @return Un tableau contenant [total, terminées, non terminées]
     */
    public Long[] getStatistics() {
        Long total = todoRepository.count();
        Long completed = todoRepository.countTerminees();
        Long pending = todoRepository.countNonTerminees();
        return new Long[]{total, completed, pending};
    }

    // ==================== MÉTHODES DE CONVERSION ====================

    /**
     * Convertit une entité Todo en TodoDTO.
     *
     * @param todo L'entité à convertir
     * @return Le DTO correspondant
     */
    private TodoDTO convertToDTO(Todo todo) {
        return new TodoDTO(
                todo.getId(),
                todo.getTitre(),
                todo.getDescription(),
                todo.getTerminee(),
                todo.getDateCreation(),
                todo.getDateModification()
        );
    }

    /**
     * Convertit un TodoDTO en entité Todo.
     *
     * @param todoDTO Le DTO à convertir
     * @return L'entité correspondante
     */
    private Todo convertToEntity(TodoDTO todoDTO) {
        Todo todo = new Todo();
        todo.setId(todoDTO.getId());
        todo.setTitre(todoDTO.getTitre());
        todo.setDescription(todoDTO.getDescription());
        todo.setTerminee(todoDTO.getTerminee());
        return todo;
    }
}