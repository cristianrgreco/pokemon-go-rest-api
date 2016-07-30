package com.crgreco.pgalerts.pokevision;

import com.crgreco.pgalerts.PGAlertsApplication;
import com.crgreco.pgalerts.PGAlertsConfiguration;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.client.Client;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;

public class DefaultPokevisionIT {

    @ClassRule
    public static final DropwizardAppRule<PGAlertsConfiguration> RULE =
            new DropwizardAppRule<>(PGAlertsApplication.class, "conf.yml");

    @Test
    public void shouldReturnListOfPokemon() {
        Client httpClient = new JerseyClientBuilder(RULE.getEnvironment()).build("testClient");
        PokevisionConfiguration pokevisionConfiguration = RULE.getConfiguration().getPokevisionConfiguration();

        Pokevision pokevision = new DefaultPokevision(httpClient, pokevisionConfiguration);
        assertThat(pokevision.fetchPokemon(51.497121994573, -0.12494802474976), hasSize(greaterThan(0)));
    }
}
