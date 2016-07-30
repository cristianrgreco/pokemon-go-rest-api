package com.crgreco.pgalerts.pokevision;

import com.crgreco.pgalerts.domain.Pokemon;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.core.GenericType;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

public class PokevisionResourceTest {

    private static final Pokevision pokevision = mock(Pokevision.class);

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new PokevisionResource(pokevision))
            .build();

    @Before
    public void setup() {
        when(pokevision.fetchPokemon(1.0, -1.0)).thenReturn(asList(
                new Pokemon(1L, "001", 1.1, -1.1, 1000001),
                new Pokemon(2L, "002", 1.2, -1.2, 1000002),
                new Pokemon(3L, "003", 1.3, -1.3, 1000003)
        ));
    }

    @After
    public void tearDown() {
        reset(pokevision);
    }
    
    @Test
    public void shouldReturnListOfPokemon() {
        List<Pokemon> pokemon = resources.client().target("/pokevision")
                .queryParam("latitude", 1.0)
                .queryParam("longitude", -1.0)
                .request()
                .get(new GenericType<List<Pokemon>>() {});

        assertThat(pokemon, is(asList(
                new Pokemon(1L, "001", 1.1, -1.1, 1000001),
                new Pokemon(2L, "002", 1.2, -1.2, 1000002),
                new Pokemon(3L, "003", 1.3, -1.3, 1000003)
        )));
    }
}
