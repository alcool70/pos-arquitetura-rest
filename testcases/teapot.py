from locust import TaskSet, task, tag


class Teapot(TaskSet):
    @task
    @tag("teapot")
    def my_task(self):
        self.client.get("/teapot")