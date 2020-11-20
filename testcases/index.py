from locust import TaskSet, task, tag


class Index(TaskSet):
    @task
    @tag("index")
    def my_task(self):
        self.client.get("/")