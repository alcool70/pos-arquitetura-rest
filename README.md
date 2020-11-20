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

Locust permite trabalhar em linha de comando ou como biblioteca. Nesse momento, usaremos o modo de linha de comando, passando para o Locust um arquivo de configuração. Com isso, podemos versionar essa configuração, de forma a possibilitar um histórico da mesma.

Executamos uma instância primária do Locust com a seguinte linha:

```bash
locust  --config=configs/main.conf &>logs/main.log  &
------  -------- ----------------- --------------- ---
   1        2           3                4          5
```
Para as instâncias secundárias, executamos a seguinte linha:
```bash
for i in {1..5}; do (locust --config=configs/nodes.conf &>logs/node$i.log  & ) ; done
-------------------  ------ -------- ------------------ ----------------- ---  ------
        6               1       2            3                  4          5     6
```

**Legenda**
* 1: comando para executar o locust
* 2: argumento para indicar um arquivo de configuração
* 3: valor do argumento `--config`
* 4: redireção do `shell`, para enviar o texto do terminal para um arquivo.
* 5: execução em *background*
* 6: estrutura de repetição (`for`) para *bash script*

Os comandos

#### Helper Script

O *helper script* [`run`](./run) aceita dois argumentos mutuamente exclusivos:

* `setup` (`run setup`): prepara o ambiente para desenvolvimento, criando um `venv`, de forma que os pacotes usados pelo projeto estejam disponíveis apenas para ele, não comprometendo assim os pacotes do sistema operacional, caso Linux.

* `tests` (`run tests`): cria uma instância primária do Locust, baseando-se na configuração presente no arquivo [`main.conf`](./configs/main.conf). Logo após, inicializa 5 instâncias secundárias do Locust, configurando-as com o arquivo [`nodes.conf`](./configs/nodes.conf). Essas instâncias secundárias se comunicam com a primária, permitindo assim paralelizar até 100 usuários, 20 em cada instância secundária criada. Este script gravam *logs* de execução para as instâncias primária e secundárias na pasta `logs` e registra o resultado da execução dos testes em arquivos CSV na pasta `results`.

### Milestones

- Precisamos visualizar os resultados dos testes como gráficos. Para tanto, pretendemos estender esse projeto, de forma a possibilitar a renderização dos arquivos CSV gerados, usando a biblioteca [bokeh](https://bokeh.org/).