package io.micronaut.examples;

import io.micronaut.examples.model.Team;
import io.micronaut.examples.model.TeamRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

import java.util.Optional;

@Controller("/teams")
public class TeamController {

    private final TeamRepository repository;

    public TeamController(TeamRepository repository) {
        this.repository = repository;
    }

    @Get
    public Iterable<Team> findAll() {
        return repository.findAll();
    }

    @Get("/{id}")
    public HttpResponse<Team> findOne(Long id) {
        Optional<Team> response = repository.findById(id);
        if (response.isPresent()) {
            return HttpResponse.ok(response.get());
        } else {
            return HttpResponse.notFound();
        }
    }

}
