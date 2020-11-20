from locust import TaskSet, task, tag


class Hello(TaskSet):
    @task
    @tag("hello")
    def my_task(self):
        self.client.get("/hello")