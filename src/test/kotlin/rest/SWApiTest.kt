package rest

import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.apache.http.HttpStatus
import org.junit.jupiter.api.Test
import rest.base.BaseTest

class SWApiTest: BaseTest() {

    @Test
    fun getPlanets(){
        Given {
            header("X-Test", "1")
        } When {
            get("/planets/")
        } Then {
            statusCode(HttpStatus.SC_OK)
        }
    }
}