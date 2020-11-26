package rest.base

import io.restassured.RestAssured
import io.restassured.config.RedirectConfig.redirectConfig
import io.restassured.config.RestAssuredConfig.config
import io.restassured.filter.log.RequestLoggingFilter
import io.restassured.filter.log.ResponseLoggingFilter
import org.junit.jupiter.api.BeforeAll

open class BaseTest {

    companion object {
        @BeforeAll
        @JvmStatic
        fun setupAll() {
            RestAssured.config = config()
                        .redirect(
                                redirectConfig()
                                        .followRedirects(true)
                                        .and()
                                        .maxRedirects(3)
                        );
            RestAssured.filters(RequestLoggingFilter(), ResponseLoggingFilter())
            RestAssured.baseURI = "https://swapi.dev"
            RestAssured.basePath = "api"
//            RestAssured.rootPath = "api"
        }
    }
}