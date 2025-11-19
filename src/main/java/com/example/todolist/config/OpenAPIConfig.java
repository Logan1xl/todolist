package com.example.todolist.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuration OpenAPI 3.0 / Swagger pour la documentation automatique de l'API.
 *
 * Cette classe configure la génération automatique de la documentation Swagger UI.
 * La documentation est accessible à l'adresse : http://localhost:8081/swagger-ui.html
 * Le fichier OpenAPI en JSON est disponible à : http://localhost:8081/v3/api-docs
 *
 * @author MBONGO BOLLO
 * @version 1.0
 * @since 2025-11-16
 */
@Configuration
public class OpenAPIConfig {

    /**
     * Configure la documentation OpenAPI/Swagger de l'application.
     *
     * Cette méthode crée un bean OpenAPI contenant toutes les informations
     * générales de l'API, les serveurs et les métadonnées.
     *
     * @return Un objet OpenAPI complètement configuré
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                // Configuration générale de l'API
                .info(new Info()
                        .title("API TodoList")
                        .version("1.0.0")
                        .description("API REST CRUD pour la gestion complète d'une liste de tâches (Todo List). " +
                                "Cette API permet de créer, lire, mettre à jour et supprimer des tâches, " +
                                "ainsi que de rechercher et filtrer les tâches selon divers critères.")
                        .termsOfService("http://swagger.io/terms/")

                        // Informations de contact
                        .contact(new Contact()
                                .name("Support TodoList API")
                                .email("support@todolist-api.com")
                                        .url("https://github.com/Logan1xl/todolist"))

                                // Licence
                                .license(new License()
                                        .name("Apache 2.0")
                                        .url("https://www.apache.org/licenses/LICENSE-2.0.html"))
                        )

                        // Configuration des serveurs
                        .servers(List.of(
                                // Serveur local (développement)
                                new Server()
                                        .url("http://localhost:8081")
                                        .description("Serveur de développement local"),

                                // Serveur de production (à adapter selon votre infrastructure)
                                new Server()
                                        .url("https://api.todolist.com")
                                        .description("Serveur de production"),

                                // Serveur de test (optionnel)
                                new Server()
                                        .url("https://test-api.todolist.com")
                                        .description("Serveur de test"))
                        ));
    }
}