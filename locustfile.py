import logging
from locust import HttpUser, events
from locust.user.wait_time import between

from helpers.imports import get_modules_in_package


class UserBehavior(HttpUser):
    host = "https://alcool70-pos-arquitetura-rest.herokuapp.com"
    tasks = get_modules_in_package("testcases")
    wait_time = between(0.5, 8)


@events.init.add_listener
def on_init(**kwargs):
    logging.info(">>> On Init")


@events.test_start.add_listener
def on_test_start(**kwargs):
    logging.info(">>> On test start")


@events.test_stop.add_listener
def on_test_stop(**kwargs):
    logging.info(">>> On test stop")


@events.quitting.add_listener
def _(environment, **kw):
    logging.info(">>> On quit")
    if environment.stats.total.fail_ratio > 0.01:
        logging.error("Test failed due to failure ratio > 1%")
        environment.process_exit_code = 1
    elif environment.stats.total.avg_response_time > 200:
        logging.error("Test failed due to average response time ratio > 200 ms")
        environment.process_exit_code = 1
    elif environment.stats.total.get_response_time_percentile(0.95) > 800:
        logging.error("Test failed due to 95th percentile response time > 800 ms")
        environment.process_exit_code = 1
    else:
        environment.process_exit_code = 0