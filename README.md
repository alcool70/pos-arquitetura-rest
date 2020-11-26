# UNIPE

## Pós-graduação em Engenharia e Qualidade de Software

### Arquitetura de Testes REST

Repositório para práticas da disciplina ministrada pelo professor Ms Ricardo Roberto de Lima.

#### Equipe Álcool70
* [Artur Araújo](https://github.com/arturaraujo)
* [Daniel Menezes](https://github.com/dsmenezes)
* [Deam Gaudêncio](https://github.com/deamgaudencioramos)
* [Diego Bandeira](https://github.com/rustnnes)
* [Igor Mendes](https://github.com/igormendes)
* [Victor Demétrio](https://github.com/victordemetrio)

#### Tecnologias usadas
* [Kotlin](https://kotlinlang.org)
* [JUnit 5](https://junit.org/junit5/)
* [REST-assured](https://rest-assured.io)

#### REST API

* #### Arquitetura

    Uma classe abstrata ([BaseTest](./BaseTest.kt)) é a base de todos os testes. Nela, configuramos o RestAssured e definimos alvo (https://swapi.dev) e caminho (/api) dos testes, que concatenados, determinam o endereço para onde as requisições serão executadas através dos verbos HTTP. Assim, podemos coletar os dados da resposta e fazer as devidas asserções.
    
    ```kotlin
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
    ```
     