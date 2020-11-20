from locust import TaskSet, task, tag


class Database(TaskSet):
    @task
    @tag("db")
    def my_task(self):
        self.client.get("/db")