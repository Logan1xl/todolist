package com.example.todolist.controller;



import com.example.todolist.dto.TodoDTO;
import com.example.todolist.service.TodoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Contrôleur REST pour gérer les opérations CRUD sur les tâches (Todo).
 *
 * Ce contrôleur expose les endpoints de l'API pour permettre aux clients
 * (frontend) de gérer les tâches : création, lecture, mise à jour et suppression.
 *
 * Base URL : /api/todos
 *
 * @author MBONGO BOLLO
 * @version 1.0
 * @since 2025-11-16
 */
@RestController
@RequestMapping("/api/todos")
@CrossOrigin(origins = "*") // Permet les requêtes CORS depuis n'importe quelle origine
public class TodoController {

    private final TodoService todoService;

    /**
     * Constructeur avec injection de dépendance du service.
     *
     * @param todoService Le service contenant la logique métier
     */
    @Autowired
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    /**
     * Récupère toutes les tâches.
     *
     * <p><b>Endpoint :</b> GET /api/todos</p>
     * <p><b>Réponse :</b> 200 OK avec la liste des tâches</p>
     *
     * @return ResponseEntity contenant la liste de toutes les tâches
     *
     * @apiNote Exemple de réponse :
     * <pre>
     * [
     *   {
     *     "id": 1,
     *     "titre": "Acheter du pain",
     *     "description": "Boulangerie du coin",
     *     "terminee": false,
     *     "dateCreation": "2025-11-16T10:30:00",
     *     "dateModification": "2025-11-16T10:30:00"
     *   }
     * ]
     * </pre>
     */
    @GetMapping
    public ResponseEntity<List<TodoDTO>> getAllTodos() {
        List<TodoDTO> todos = todoService.getAllTodos();
        return ResponseEntity.ok(todos);
    }

    /**
     * Récupère une tâche spécifique par son identifiant.
     *
     * <p><b>Endpoint :</b> GET /api/todos/{id}</p>
     * <p><b>Réponse :</b> 200 OK avec la tâche trouvée, ou 404 NOT FOUND</p>
     *
     * @param id L'identifiant de la tâche (path variable)
     * @return ResponseEntity contenant la tâche trouvée
     *
     * @apiNote Exemple de requête : GET /api/todos/1
     */
    @GetMapping("/{id}")
    public ResponseEntity<TodoDTO> getTodoById(@PathVariable Long id) {
        TodoDTO todo = todoService.getTodoById(id);
        return ResponseEntity.ok(todo);
    }

    /**
     * Crée une nouvelle tâche.
     *
     * <p><b>Endpoint :</b> POST /api/todos</p>
     * <p><b>Réponse :</b> 201 CREATED avec la tâche créée</p>
     *
     * @param todoDTO Les données de la tâche à créer (corps de la requête en JSON)
     * @return ResponseEntity contenant la tâche créée avec son ID
     *
     * @apiNote Exemple de corps de requête :
     * <pre>
     * {
     *   "titre": "Faire les courses",
     *   "description": "Lait, pain, œufs",
     *   "terminee": false
     * }
     * </pre>
     */
    @PostMapping
    public ResponseEntity<TodoDTO> createTodo(@Valid @RequestBody TodoDTO todoDTO) {
        TodoDTO createdTodo = todoService.createTodo(todoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTodo);
    }

    /**
     * Met à jour une tâche existante.
     *
     * <p><b>Endpoint :</b> PUT /api/todos/{id}</p>
     * <p><b>Réponse :</b> 200 OK avec la tâche mise à jour, ou 404 NOT FOUND</p>
     *
     * @param id L'identifiant de la tâche à modifier
     * @param todoDTO Les nouvelles données de la tâche
     * @return ResponseEntity contenant la tâche mise à jour
     *
     * @apiNote Exemple de requête : PUT /api/todos/1
     * <pre>
     * {
     *   "titre": "Faire les courses (urgent)",
     *   "description": "Lait, pain, œufs, fromage",
     *   "terminee": false
     * }
     * </pre>
     */
    @PutMapping("/{id}")
    public ResponseEntity<TodoDTO> updateTodo(
            @PathVariable Long id,
            @Valid @RequestBody TodoDTO todoDTO) {
        TodoDTO updatedTodo = todoService.updateTodo(id, todoDTO);
        return ResponseEntity.ok(updatedTodo);
    }

    /**
     * Supprime une tâche.
     *
     * <p><b>Endpoint :</b> DELETE /api/todos/{id}</p>
     * <p><b>Réponse :</b> 204 NO CONTENT si succès, ou 404 NOT FOUND</p>
     *
     * @param id L'identifiant de la tâche à supprimer
     * @return ResponseEntity vide avec statut 204
     *
     * @apiNote Exemple de requête : DELETE /api/todos/1
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Récupère les tâches filtrées par statut.
     *
     * <p><b>Endpoint :</b> GET /api/todos/status?terminee={true|false}</p>
     * <p><b>Réponse :</b> 200 OK avec la liste filtrée</p>
     *
     * @param terminee Le statut de complétion (true = terminées, false = non terminées)
     * @return ResponseEntity contenant la liste des tâches filtrées
     *
     * @apiNote Exemples de requêtes :
     * <ul>
     *   <li>GET /api/todos/status?terminee=true (tâches terminées)</li>
     *   <li>GET /api/todos/status?terminee=false (tâches en cours)</li>
     * </ul>
     */
    @GetMapping("/status")
    public ResponseEntity<List<TodoDTO>> getTodosByStatus(
            @RequestParam(name = "terminee") Boolean terminee) {
        List<TodoDTO> todos = todoService.getTodosByStatus(terminee);
        return ResponseEntity.ok(todos);
    }

    /**
     * Recherche des tâches par titre (recherche partielle).
     *
     * <p><b>Endpoint :</b> GET /api/todos/search?titre={texte}</p>
     * <p><b>Réponse :</b> 200 OK avec la liste des tâches trouvées</p>
     *
     * @param titre Le texte à rechercher dans les titres (insensible à la casse)
     * @return ResponseEntity contenant la liste des tâches correspondantes
     *
     * @apiNote Exemple : GET /api/todos/search?titre=course
     * Trouvera : "Faire les courses", "Courses urgentes", etc.
     */
    @GetMapping("/search")
    public ResponseEntity<List<TodoDTO>> searchTodos(
            @RequestParam(name = "titre") String titre) {
        List<TodoDTO> todos = todoService.searchTodosByTitre(titre);
        return ResponseEntity.ok(todos);
    }

    /**
     * Marque une tâche comme terminée.
     *
     * <p><b>Endpoint :</b> PATCH /api/todos/{id}/complete</p>
     * <p><b>Réponse :</b> 200 OK avec la tâche mise à jour, ou 404 NOT FOUND</p>
     *
     * @param id L'identifiant de la tâche à marquer comme terminée
     * @return ResponseEntity contenant la tâche mise à jour
     *
     * @apiNote Exemple : PATCH /api/todos/1/complete
     */
    @PatchMapping("/{id}/complete")
    public ResponseEntity<TodoDTO> markAsCompleted(@PathVariable Long id) {
        TodoDTO updatedTodo = todoService.markAsCompleted(id);
        return ResponseEntity.ok(updatedTodo);
    }

    /**
     * Récupère les statistiques des tâches.
     *
     * <p><b>Endpoint :</b> GET /api/todos/statistics</p>
     * <p><b>Réponse :</b> 200 OK avec les statistiques</p>
     *
     * @return ResponseEntity contenant un objet avec les statistiques
     *
     * @apiNote Exemple de réponse :
     * <pre>
     * {
     *   "total": 10,
     *   "terminees": 6,
     *   "enCours": 4
     * }
     * </pre>
     */
    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Long>> getStatistics() {
        Long[] stats = todoService.getStatistics();
        Map<String, Long> response = new HashMap<>();
        response.put("total", stats[0]);
        response.put("terminees", stats[1]);
        response.put("enCours", stats[2]);
        return ResponseEntity.ok(response);
    }
}