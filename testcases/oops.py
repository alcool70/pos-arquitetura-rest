from locust import TaskSet, task, tag


class OopsClass(TaskSet):
    @task
    @tag("oops")
    def my_task(self):
        self.client.get("/oops")