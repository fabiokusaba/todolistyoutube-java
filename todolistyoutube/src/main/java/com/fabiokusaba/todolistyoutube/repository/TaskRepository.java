package com.fabiokusaba.todolistyoutube.repository;

import com.fabiokusaba.todolistyoutube.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//aqui na classe TaskRepository nós vamos colocar essa notation Repository para indicar para o spring que ela é
//uma classe responsável por ter o papel de repository e também a gente vai colocar que ela extende do
//JpaRepository que é para o spring data jpa entender que ela é responsável por fazer a comunicação entre a nossa
//Task que é a nossa camada de modelo, nossa entidade de dados e o banco de dados, e eu coloco qual o tipo da nossa
//chave primária que é um Long
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
