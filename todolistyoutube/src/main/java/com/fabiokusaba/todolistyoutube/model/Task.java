package com.fabiokusaba.todolistyoutube.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Date;

//criando as informações referentes a nossa tabela
@Entity //começando informando que é uma entidade de dados pra camada de persistência
@Table(name = "tasks") //nome da tabela
@Setter //após devemos realizar o setter, getter e por último o tostring
@Getter
@ToString
public class Task { //especificando os campos que vão ter nessa tabela

    //informando que o id vai ser a chave primária da nossa tabela e qual o tipo de geração desse valor
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // IDENTITY: vai ser gerado 1, 2, 3, e assim por diante
    private Long id; //chave primária

    //informando o título da nossa tarefa e essa coluna não pode ser nula, portanto devemos informar a notation
    @Column(nullable = false) //não pode ser um valor nulo
    private String title;

    //informando a descrição da nossa tarefa que também não pode ser nula
    @Column(nullable = false)
    private String description;

    //como estamos falando de um gerenciador de tarefas precisamos de uma deadLine, ou seja, qual o prazo máximo
    //para essa tarefa ser concluída
    @Column(nullable = false)
    private LocalDateTime deadLine;

    //também iremos precisar da createdAt e da updatedAt esses dois campos vão ser uma particularidade que eles não
    //são campos que a gente vai informar ali na requisição quando fomos testar pelo PostgreSQL, são campos que a
    //própria aplicação vai preencher pra gente.
    //não vamos passar ele na requisição, mas quando a gente for consultar esse campo ou até quando retornar a
    //response a gente vai ver que ele retornou um timestamp daquilo que foi criado
    //não pode ser um campo nulo e também não pode ser um campo atualizável então uma vez que eu registrei o
    //timestamp não vou conseguir atualizá-lo via requisição somente indo diretamente no banco
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    //e aqui temos a última coluna que é a updatedAt que vou estar falando quando eu tiver uma atualização
    //registre o carimbo dessa updatedAt então quando a gente cria o updated vai ser a mesma data da criação, mas
    //quando a gente chegar no endpoint de atualizar o registro o updated vai ser a data que você fez alguma
    //atualização seja em algum campo que seja permitido, ou seja, que não tenha o updatable = false
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /*
    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDeadLine(LocalDateTime deadLine) {
        this.deadLine = deadLine;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
     */
}
