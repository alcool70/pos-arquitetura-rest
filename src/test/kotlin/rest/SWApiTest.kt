package rest

import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.apache.http.HttpStatus
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import rest.base.BaseTest
import kotlin.collections.LinkedHashMap


class SWApiTest : BaseTest() {

    @Test
    @Disabled
    fun `get Planets from Star Wars API and check if has more then one page`() {
        val planets = mutableListOf<Planet>()

        var page = 1
        var count: Int

        while (true) {
            val resp = /*Given { header("X-Test", "1")} */
                    When {
                        get("/planets/?page=${page++}")
                    } Then {
                        statusCode(HttpStatus.SC_OK)
                        body("count", greaterThan(0))
                    }

            planets += resp.extract().path<List<Planet>>("results")

            count = resp.extract().path("count")

            resp.extract().path<String>("next")?.let {
                assertThat(it, equalTo("http://swapi.dev/api/planets/?page=$page"))
            } ?: break
        }

        assertThat(planets.size, equalTo(count))
    }

    @Test
    @Disabled
    fun `get People from Star Wars API and check if has more then one page`() {
        val people = mutableListOf<People>()

        var page = 1
        var count: Int

        while (true) {
            val resp = /*Given { header("X-Test", "1")} */
                    When {
                        get("/people/?page=${page++}")
                    } Then {
                        statusCode(HttpStatus.SC_OK)
                        body("count", greaterThan(0))
                    }

            people += resp.extract().path<List<People>>("results")

            count = resp.extract().path("count")

            resp.extract().path<String>("next")?.let {
                assertThat(it, equalTo("http://swapi.dev/api/people/?page=$page"))
            } ?: break
        }

        assertThat(people.size, equalTo(count))
    }

    @Test
    fun `search for Luke Skywalker from Star Wars API and check his birth year is 19BBY`() {
        val resp = When {
            get("/people/?search=luke")
        } Then {
            statusCode(HttpStatus.SC_OK)
            body("count", greaterThan(0))
        }

        val people = resp.extract().path<List<People>>("results")
        println(people[0].birth_year)
//        val luke = people[0]
//        println(luke.javaClass)

//        assertThat(luke.birthYear, equalTo("19BBY"))
    }

    @Test
    fun `check if all the movies have at least ten characters`() {
        val resp = When {
            get("/films")
        } Then {
            statusCode(HttpStatus.SC_OK)
            body("count", greaterThan(0))
        }

        val filmsMap = resp.extract().response().jsonPath().getList<Any>("results")

        assertFalse(filmsMap.isEmpty())

        filmsMap.forEach {
            filme -> assertThat(filme, instanceOf(LinkedHashMap::class.java))
            val map = filme as LinkedHashMap<*,*>
            var list = map.get("characters")

            assertThat(list, instanceOf(List::class.java))

            list = list as List<*>
            assertThat(list.size, greaterThan(10))
        }
    }

    @Test
    fun `check if human is listed as a species`() {
        val resp = When {
            get("/species")
        } Then {
            statusCode(HttpStatus.SC_OK)
            body("count", greaterThan(0))
        }

        val speciesMap = resp.extract().response().jsonPath().getList<Any>("results")

        assertFalse(speciesMap.isEmpty())

        var especiesNome = mutableListOf<String>()

        speciesMap.forEach {
            especie -> assertThat(especie, instanceOf(LinkedHashMap::class.java))
            val map = especie as LinkedHashMap<*,*>
            var nome = map.get("name")

            assertThat(nome, instanceOf(String::class.java))
            especiesNome.add(nome as String)
        }

        assertTrue(especiesNome.contains("Human"))
    }
}