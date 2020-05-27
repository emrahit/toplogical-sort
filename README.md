#Task Process orderer

This application helps to user to provide the tasks with commands and then to receive them ordered in the way that they have to be executed.
The examples can be found on the postman collection saved in the reppo with name Sumup.postman_collection.json.
Example input:
```
{
    "tasks": [
        {
            "name": "task-1",
            "command": "touch /tmp/file1"
        },
        {
            "name": "task-2",
            "command": "cat /tmp/file1",
            "requires": [
                "task-3"
            ]
        },
        {
            "name": "task-3",
            "command": "echo 'Hello World!' > /tmp/file1",
            "requires": [
                "task-1"
            ]
        },
        {
            "name": "task-4",
            "command": "rm /tmp/file1",
            "requires": [
                "task-2",
                "task-3"
            ]
        }
    ]
}
```
The result will be different depending on the endpoint:
1. Calling localhost:8080/
   response example:
   ```
   [
       {
           "name": "task-1",
           "command": "touch /tmp/file1"
       },
       {
           "name": "task-3",
           "command": "echo 'Hello World!' > /tmp/file1"
       },
       {
           "name": "task-2",
           "command": "cat /tmp/file1"
       },
       {
           "name": "task-4",
           "command": "rm /tmp/file1"
       }
   ]
   ```
2. Calling localhost:8080/bash
    response example:
    ```
    #!/usr/bin/env bash
    touch /tmp/file1
    echo 'Hello World!' > /tmp/file1
    cat /tmp/file1
    rm /tmp/file1
   ```