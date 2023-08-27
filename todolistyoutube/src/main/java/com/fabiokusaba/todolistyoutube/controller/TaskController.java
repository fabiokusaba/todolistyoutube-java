package com.fabiokusaba.todolistyoutube.controller;

import com.fabiokusaba.todolistyoutube.model.Task;
import com.fabiokusaba.todolistyoutube.service.TaskService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//primeira coisa que a gente tem que fazer é colocar essa notation de RestController, colocar esse endpoint aqui a
//nossa RequestMapping e falar que ela vai ser "/api/v1"
@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
@Slf4j
public class TaskController {

    //e aqui a gente tem que chamar a TaskService que ela que vai ser chamada, então o controller vai chamar a
    //service, a service chama a repository, e a repository chama a camada de dados e aí comunica com o banco
    //colocamos Autowired também
    TaskService taskService;

    //criando o nosso endpoint de post então a gente vai ter um PostMapping que vai ter esse "/tasks" e se der
    //tudo certo ele vai retornar o ResponseStatus created
    //vamos ter o createTask que vai receber um RequestBody com as informações da task e aí ele vai chamar o nosso
    //taskService para criar a nossa task
    @PostMapping("/tasks")
    @ResponseStatus(HttpStatus.CREATED)
    public Task createTask(@RequestBody Task task) {
        log.info("Criando uma nova tarefa com as informações [{}]", task);
        return taskService.createTask(task);
    }

    //na get que vai retornar todas as tarefas nós vamos colocar "/tasks", ResponseStatus OK
    //aqui vou colocar que vai ser um public List<Task> que vai se chamar getAllTasks e ela vai me retornar
    //taskService listAllTasks
    @GetMapping("/tasks")
    @ResponseStatus(HttpStatus.OK)
    public List<Task> getAllTasks() {
        log.info("Listando todas as tarefas cadastradas");
        return taskService.listAllTasks();
    }

    //agora vamos fazer o get by id
    //aqui vai ser um ResponseEntity e não uma List, o nome vai ser getTaskById e eu recebo algumas coisas para a
    //gente poder filtrar então ao invés de receber um body vou receber um PathVariable que é o que vou estar
    //passando no nosso id, o valor dele vai ser um value "id" do tipo Long id
    //o nosso retorno também muda que eu vou fazer um findTaskById
    @GetMapping("/tasks/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Task> getTaskById(@PathVariable (value = "id") Long id) {
        log.info("Buscando tarefa com o id [{}]", id);
        return taskService.findTaskById(id);
    }

    //criando o nosso endpoint na controller e como aqui eu estou atualizando vai mudar e ser um PutMapping e aqui
    //além de eu passar o id eu tenho que passar o que estou atualizando então eu tenho que passar também um
    //RequestBody de Task, daí com essas duas informações eu vou chamar o meu taskService e o método updateTaskById
    //passando a task e o id
    @PutMapping("/tasks/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Task> updateTaskById(@PathVariable (value = "id") Long id, @RequestBody Task task) {
        log.info("Atualizando a tarefa com id [{}] as novas informações são: [{}]", id, task);
        return taskService.updateTaskById(task, id);
    }

    //criando nosso endpoint do delete
    @DeleteMapping("/tasks/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Object> deleteTaskById(@PathVariable (value = "id") Long id) {
        log.info("Excluindo tarefa com o id [{}]", id);
        return taskService.deleteById(id);
    }
}
