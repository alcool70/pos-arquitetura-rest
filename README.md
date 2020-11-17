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
* [Python 3.7+](https://www.python.org/)
* [Locust](https://locust.io/)

#### Locust

É uma ferramenta *open source* para testes de carga, onde é possível definir comportamento de usuário como classes em linguagem Python, e executar múltiplas instâncias dessas classes contra um sistema específico.

```python
from locust import HttpUser, between, task


class WebsiteUser(HttpUser):
    wait_time = between(5, 15)

    def on_start(self):
        self.client.post("/login", {
            "username": "test_user",
            "password": ""
        })

    @task
    def index(self):
        self.client.get("/")
        self.client.get("/static/assets.js")

    @task
    def about(self):
        self.client.get("/about/")
```

#### Arquitetura

O script [`run.sh`](./run.sh) cria uma instância primária do Locust, baseando-se na configuração presente no arquivo [`main.conf`](./main.conf). Logo após, inicializa 5 instâncias secundárias do Locust, que se comunicam com a primária, permitindo assim, paralelizar até 100 usuários, 20 em cada instância secundária criada. Este script grava *logs* de execução para a instâncias primária e secundárias na pasta `logs` e registra o resultado da execução dos testes em arquivos CSV na pasta `results`.