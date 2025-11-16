package com.example.todolist.exception;



/**
 * Exception personnalisée levée lorsqu'une ressource n'est pas trouvée.
 *
 * Cette exception est utilisée principalement lorsqu'une tâche (Todo)
 * demandée par son ID n'existe pas dans la base de données.
 *
 * @author MBONGO BOLLO
 * @version 1.0
 * @since 2025-11-16
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Constructeur avec message d'erreur.
     *
     * @param message Le message décrivant l'erreur
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructeur avec message et cause de l'erreur.
     *
     * @param message Le message décrivant l'erreur
     * @param cause La cause originale de l'exception
     */
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}