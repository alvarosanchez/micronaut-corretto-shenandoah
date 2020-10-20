package io.micronaut.examples;

import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.examples.model.Team;
import io.micronaut.examples.model.TeamRepository;
import io.micronaut.runtime.server.event.ServerStartupEvent;

import javax.inject.Singleton;

@Singleton
public class Bootstrap implements ApplicationEventListener<ServerStartupEvent> {

    private final TeamRepository repository;

    public Bootstrap(TeamRepository repository) {
        this.repository = repository;
    }

    @Override
    public void onApplicationEvent(ServerStartupEvent event) {
        repository.save(new Team("Real Madrid", "Santiago Bernabeu"));
        repository.save(new Team("FC Barcelona", "Camp Nou"));
    }

}
