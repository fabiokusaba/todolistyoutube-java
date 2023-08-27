package com.fabiokusaba.todolistyoutube.service;

import com.fabiokusaba.todolistyoutube.model.Task;
import com.fabiokusaba.todolistyoutube.repository.TaskRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

//primeira coisa que precisamos fazer aqui é falar que ela é uma classe Service, colocar o RequiredArgsConstructor
//e a gente tem que chamar o nosso TaskRepository que ele vai auxiliar a gente a fazer exatamente essa comunicação
//entre as camadas e colocamos a notation do Autowired
@Service
@AllArgsConstructor
public class TaskService {

    private TaskRepository taskRepository;

    //criando tasks e aqui estou informando que vou receber a task como corpo desse método, como parâmetro e aí eu
    //vou fazer a comunicação com o taskRepository para ele executar um save dessa task, concluímos o nosso método
    //da service e agora iremos lá na controller criar o nosso endpoint para a gente criar as nossas tarefas
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    //criando um método para listarmos todas as nossas tasks e como ele vai listar várias vai ser uma List de Task
    //não vou receber nada porque eu vou somente responder com todas as tarefas que eu tiver cadastradas e aí
    //eu coloco que ele vai retornar o nosso taskRepository findAll, após criado o método iremos lá no controller
    public List<Task> listAllTasks() {
        return taskRepository.findAll();
    }

    //criando o método find by id ele não vai retornar uma lista já que o id é chave primária e só existe um por id
    //então colocamos que vai ser um public ResponseEntity de Task que vai fazer o findTaskById e vai receber como
    //parâmetro um Long id
    //aí a gente vai fazer o return que se comunica com a taskRepository findById recebendo esse id e aí vem a parte
    //de fazer o tratamento se der certo responde com ok e o body que a gente está buscando, senão responde com not
    //found
    //basicamente no map estou falando que vou fazer um map com esse retorno, ou seja, vou fazer a busca por esse id
    //pegar esse retorno e transformar ele na tarefa, e aqui temos a nossa lambda expression também ->
    public ResponseEntity<Task> findTaskById(Long id) {
        return taskRepository.findById(id)
                .map(task -> ResponseEntity.ok().body(task))
                .orElse(ResponseEntity.notFound().build());
    }

    //criando um método para atualizar as tarefas se comunicar com o nosso repository pra fazer a atualização de uma
    //tarefa na nossa base de dados
    //então vou criar um método que vai retornar uma Task que vai se chamar updateTaskById e ele vai receber tanto o
    //corpo que a gente quer mudar da task quanto o id que a gente vai buscar pra ser atualizado
    //então o corpo desse método primeiro ele vai fazer o return do taskRepository realizando o findById, depois
    //disso ele vai realizar um map para pegar essas informações e realizar a atualização dessa task que a gente
    //localizou pelo id aqui
    //então vai ser uma taskToUpdate e vou setar os valores para atualização, primeiro vai ser o title, logo em
    //seguida vai ser a description e depois a deadLine que são campos que permitem atualização, o updatedAt assim
    //que for processado a atualização ele vai ser atualizado automaticamente e o createdAt ele não é um campo
    //atualizável
    //feito tudo isso vou retornar uma resposta do status ok me informando que deu certo a atualização e como eu
    //quero ver a resposta do que estou atualizando ao invés de retornar um no content 204 que muitas vezes é
    //utilizado em caso de update eu vou retornar um ok porque eu quero ver que deu certo e eu quero ver o que foi
    //atualizado
    //vou mapear em caso de erro então se der errado eu vou retornar para ele uma ResponseEntity informando que
    //não foi encontrado a task que estou querendo atualizar, e agora vamos fazer o endpoint na controller
    public ResponseEntity<Task> updateTaskById(Task task, Long id) {
        return taskRepository.findById(id)
                .map(taskToUpdate -> {
                    taskToUpdate.setTitle(task.getTitle());
                    taskToUpdate.setDescription(task.getDescription());
                    taskToUpdate.setDeadLine(task.getDeadLine());
                    Task updated = taskRepository.save(taskToUpdate);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }

    //agora iremos criar o método delete e aqui vai funcionar da seguinte maneira vai ser public ResponseEntity
    //e ao invés de responder uma entidade eu vou retornar um Object porque como vou excluir a tarefa não tem como
    //eu retornar o corpo de algo que não existe mais no meu banco
    //vou precisar passar apenas o Long id e vou retornar o taskRepository e primeiro vou fazer p findById porque ele
    //tem que localizar aquela tarefa que eu quero, depois disso a gente vai fazer um map com a taskToDelete e dentro
    //dela vou chamar a taskRepository deleteById passando o id que vou receber lá no meu endpoint e aí eu vou
    //retornar um ResponseEntity noContent
    //em caso de erro ResponseEntity notFound build, e agora vamos fazer o nosso endpoint no nosso controller
    public ResponseEntity<Object> deleteById(Long id) {
        return taskRepository.findById(id)
                .map(taskToDelete -> {
                    taskRepository.deleteById(id);
                    return ResponseEntity.noContent().build();
                }).orElse(ResponseEntity.notFound().build());

    }
}
