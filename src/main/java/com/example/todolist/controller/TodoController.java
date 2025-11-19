package com.example.todolist.controller;

import com.example.todolist.dto.TodoDTO;
import com.example.todolist.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
 * Ce contrôleur expose les endpoints de l'API REST pour permettre aux clients
 * (frontend) de gérer les tâches : création, lecture, mise à jour et suppression.
 *
 * Base URL : /api/todos
 *
 * La documentation Swagger est disponible à : http://localhost:8081/swagger-ui.html
 *
 * @author Wulfrid MBONGO
 * @version 1.0
 * @since 2025-11-16
 */
@RestController
@RequestMapping("/api/todos")
@CrossOrigin(origins = "*")
@Tag(
        name = "TodoList API",
        description = "Endpoints pour la gestion complète des tâches (CRUD, recherche, filtrage, statistiques)"
)
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
     * @return ResponseEntity contenant la liste de toutes les tâches
     */
    @GetMapping
    @Operation(
            summary = "Récupérer toutes les tâches",
            description = "Retourne la liste complète de toutes les tâches existantes dans la base de données."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Liste de tâches récupérée avec succès",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TodoDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erreur serveur interne"
            )
    })
    public ResponseEntity<List<TodoDTO>> getAllTodos() {
        List<TodoDTO> todos = todoService.getAllTodos();
        return ResponseEntity.ok(todos);
    }

    /**
     * Récupère une tâche spécifique par son identifiant.
     *
     * @param id L'identifiant de la tâche
     * @return ResponseEntity contenant la tâche trouvée
     */
    @GetMapping("/{id}")
    @Operation(
            summary = "Récupérer une tâche par ID",
            description = "Retourne une tâche spécifique identifiée par son ID. " +
                    "Si l'ID n'existe pas, retourne une erreur 404."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Tâche trouvée et retournée",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TodoDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Tâche non trouvée avec l'ID fourni"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erreur serveur interne"
            )
    })
    public ResponseEntity<TodoDTO> getTodoById(
            @PathVariable
            @Parameter(
                    name = "id",
                    description = "L'identifiant unique de la tâche",
                    required = true,
                    example = "1",
                    in = ParameterIn.PATH
            )
            Long id) {
        TodoDTO todo = todoService.getTodoById(id);
        return ResponseEntity.ok(todo);
    }

    /**
     * Crée une nouvelle tâche.
     *
     * @param todoDTO Les données de la tâche à créer
     * @return ResponseEntity contenant la tâche créée avec son ID
     */
    @PostMapping
    @Operation(
            summary = "Créer une nouvelle tâche",
            description = "Crée et enregistre une nouvelle tâche dans la base de données. " +
                    "Le titre est obligatoire (max 100 caractères). " +
                    "La description est optionnelle (max 500 caractères). " +
                    "La tâche est créée avec le statut 'non terminée' par défaut."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Tâche créée avec succès",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TodoDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Données de requête invalides (validation échouée)"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erreur serveur interne"
            )
    })
    public ResponseEntity<TodoDTO> createTodo(
            @Valid
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Objet TodoDTO contenant les données de la tâche à créer",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = TodoDTO.class,
                                    example = "{\"titre\": \"Faire les courses\", " +
                                            "\"description\": \"Lait, pain, œufs\", " +
                                            "\"terminee\": false}"
                            )
                    )
            )
            TodoDTO todoDTO) {
        TodoDTO createdTodo = todoService.createTodo(todoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTodo);
    }

    /**
     * Met à jour une tâche existante.
     *
     * @param id L'identifiant de la tâche à modifier
     * @param todoDTO Les nouvelles données de la tâche
     * @return ResponseEntity contenant la tâche mise à jour
     */
    @PutMapping("/{id}")
    @Operation(
            summary = "Mettre à jour une tâche",
            description = "Modifie complètement une tâche existante identifiée par son ID. " +
                    "Tous les champs du DTO doivent être fournis. " +
                    "Si l'ID n'existe pas, retourne une erreur 404."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Tâche mise à jour avec succès",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TodoDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Données de requête invalides"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Tâche non trouvée avec l'ID fourni"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erreur serveur interne"
            )
    })
    public ResponseEntity<TodoDTO> updateTodo(
            @PathVariable
            @Parameter(
                    name = "id",
                    description = "L'identifiant unique de la tâche à modifier",
                    required = true,
                    example = "1",
                    in = ParameterIn.PATH
            )
            Long id,
            @Valid
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Objet TodoDTO avec les nouvelles données",
                    required = true
            )
            TodoDTO todoDTO) {
        TodoDTO updatedTodo = todoService.updateTodo(id, todoDTO);
        return ResponseEntity.ok(updatedTodo);
    }

    /**
     * Supprime une tâche.
     *
     * @param id L'identifiant de la tâche à supprimer
     * @return ResponseEntity vide avec statut 204
     */
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Supprimer une tâche",
            description = "Supprime définitivement une tâche de la base de données. " +
                    "Cette action est irréversible. " +
                    "Si l'ID n'existe pas, retourne une erreur 404."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Tâche supprimée avec succès"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Tâche non trouvée avec l'ID fourni"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erreur serveur interne"
            )
    })
    public ResponseEntity<Void> deleteTodo(
            @PathVariable
            @Parameter(
                    name = "id",
                    description = "L'identifiant unique de la tâche à supprimer",
                    required = true,
                    example = "1",
                    in = ParameterIn.PATH
            )
            Long id) {
        todoService.deleteTodo(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Récupère les tâches filtrées par statut.
     *
     * @param terminee Le statut de complétion
     * @return ResponseEntity contenant la liste filtrée
     */
    @GetMapping("/status")
    @Operation(
            summary = "Filtrer les tâches par statut",
            description = "Retourne toutes les tâches filtrées selon leur statut de complétion. " +
                    "Utilisez 'true' pour les tâches terminées ou 'false' pour les tâches en cours."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Liste des tâches filtrées récupérée",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TodoDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erreur serveur interne"
            )
    })
    public ResponseEntity<List<TodoDTO>> getTodosByStatus(
            @RequestParam(name = "terminee")
            @Parameter(
                    name = "terminee",
                    description = "Filtre par statut : true = terminées, false = non terminées",
                    required = true,
                    example = "true",
                    in = ParameterIn.QUERY
            )
            Boolean terminee) {
        List<TodoDTO> todos = todoService.getTodosByStatus(terminee);
        return ResponseEntity.ok(todos);
    }

    /**
     * Recherche des tâches par titre.
     *
     * @param titre Le texte à rechercher
     * @return ResponseEntity contenant la liste des tâches trouvées
     */
    @GetMapping("/search")
    @Operation(
            summary = "Rechercher les tâches par titre",
            description = "Effectue une recherche partielle et insensible à la casse dans les titres des tâches. " +
                    "Retourne toutes les tâches dont le titre contient le texte recherché."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Résultats de recherche retournés",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TodoDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erreur serveur interne"
            )
    })
    public ResponseEntity<List<TodoDTO>> searchTodos(
            @RequestParam(name = "titre")
            @Parameter(
                    name = "titre",
                    description = "Texte à rechercher dans les titres (insensible à la casse)",
                    required = true,
                    example = "course",
                    in = ParameterIn.QUERY
            )
            String titre) {
        List<TodoDTO> todos = todoService.searchTodosByTitre(titre);
        return ResponseEntity.ok(todos);
    }

    /**
     * Marque une tâche comme terminée.
     *
     * @param id L'identifiant de la tâche
     * @return ResponseEntity contenant la tâche mise à jour
     */
    @PatchMapping("/{id}/complete")
    @Operation(
            summary = "Marquer une tâche comme terminée",
            description = "Bascule le statut d'une tâche à 'terminée' (true). " +
                    "Si l'ID n'existe pas, retourne une erreur 404."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Tâche marquée comme terminée",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TodoDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Tâche non trouvée avec l'ID fourni"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erreur serveur interne"
            )
    })
    public ResponseEntity<TodoDTO> markAsCompleted(
            @PathVariable
            @Parameter(
                    name = "id",
                    description = "L'identifiant unique de la tâche à marquer comme terminée",
                    required = true,
                    example = "1",
                    in = ParameterIn.PATH
            )
            Long id) {
        TodoDTO updatedTodo = todoService.markAsCompleted(id);
        return ResponseEntity.ok(updatedTodo);
    }

    /**
     * Récupère les statistiques des tâches.
     *
     * @return ResponseEntity contenant les statistiques
     */
    @GetMapping("/statistics")
    @Operation(
            summary = "Obtenir les statistiques des tâches",
            description = "Retourne des statistiques globales sur les tâches : " +
                    "nombre total, nombre de tâches terminées et nombre de tâches en cours."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Statistiques récupérées avec succès",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    example = "{\"total\": 10, \"terminees\": 6, \"enCours\": 4}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erreur serveur interne"
            )
    })
    public ResponseEntity<Map<String, Long>> getStatistics() {
        Long[] stats = todoService.getStatistics();
        Map<String, Long> response = new HashMap<>();
        response.put("total", stats[0]);
        response.put("terminees", stats[1]);
        response.put("enCours", stats[2]);
        return ResponseEntity.ok(response);
    }
}