package io.micronaut.examples;

import io.micronaut.examples.model.Team;
import io.micronaut.examples.model.TeamRepository;
import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import javax.inject.Inject;

@MicronautTest
public class TeamRepositorySpec {

    @Inject
    TeamRepository repository;

    @Test
    void testItCanSaveTeams() {
        Assertions.assertEquals(0, repository.count());

        Team rma = new Team("Real Madrid", "Santiago Bernabeu");
        Team fcb = new Team("FC Barcelona", "Camp Nou");

        repository.save(rma);
        repository.save(fcb);

        Assertions.assertNotNull(rma.getId());
        Assertions.assertNotNull(fcb.getId());

        Assertions.assertEquals(1, rma.getId());
        Assertions.assertEquals(2, fcb.getId());

        Assertions.assertEquals(2, repository.count());
    }

}
