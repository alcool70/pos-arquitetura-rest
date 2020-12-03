package rest

import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.apache.http.HttpStatus
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.Test
import rest.base.BaseTest
import java.net.URL

class SWApiTest : BaseTest() {

    @Test
    fun `get Planets from Star Wars API and check if has more then one page`() {
        val planets = mutableListOf<Planet>()

        val resp = /*Given { header("X-Test", "1")} */
                When {
                    get("/planets/")
                } Then {
                    statusCode(HttpStatus.SC_OK)
                    body("count", greaterThan(0))
                    body("next", notNullValue())
                }

        planets += resp.extract().path<List<Planet>>("results")

        val count = resp.extract().path<Int>("count")
        assertThat(count, equalTo(60))

        val next = resp.extract().path<String>("next")
        val page: Int = mapOfQueryParams(URL(next).query)["page"]?.toInt() ?: 2
        assertThat(next, equalTo("http://swapi.dev/api/planets/?page=$page"))

        val pages: Int = count / planets.size

        (page..pages).forEach {
            val resp1 = When {
                get("/planets/?page=$it")
            } Then {
                statusCode(HttpStatus.SC_OK)
                body("count", greaterThan(0))
                body("next", notNullValue())
            }

            planets += resp1.extract().path<List<Planet>>("results")
        }

        assertThat(planets.size, equalTo(60))
    }
}