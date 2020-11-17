import gevent, logging
from locust import HttpUser, events, task, tag
from locust.env import Environment
from locust.stats import stats_printer, stats_history
from locust.log import setup_logging


# setup_logging("DEBUG", None)


class SomeClass(HttpUser):
    host = "https://thiscatdoesnotexist.com"

    @task
    @tag("Test")
    def my_task(self):
        self.client.get("/")


@events.init.add_listener
def on_init(**kwargs):
    logging.info(">>> On Init")


@events.quitting.add_listener
def on_quit(**kwargs):
    logging.info(">>> On quit")


@events.test_start.add_listener
def on_test_start(**kwargs):
    logging.info(">>> On test start")


@events.test_stop.add_listener
def on_test_stop(**kwargs):
    logging.info(">>> On test stop")