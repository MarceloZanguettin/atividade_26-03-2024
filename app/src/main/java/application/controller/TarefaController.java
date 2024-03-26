package application.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import application.model.Tarefa;
import application.repository.TarefaRepository;

@RestController
public class TarefaController {
  @Autowired
  private TarefaRepository tarefaRepo;

  @GetMapping("/tarefa")
  public List<Tarefa> getTarefa() {
    return (List<Tarefa>) tarefaRepo.findAll();
  }

  @PostMapping("/tarefa")
  public Tarefa postTarefa(@RequestBody Tarefa tarefa) {
    return tarefaRepo.save(tarefa);
  }

  @GetMapping("/tarefa/{id}")
  public Tarefa getTarefa(@PathVariable Long id) {
    return tarefaRepo.findById(id).get();
  }

  @PutMapping("/tarefa/{id}")
  public Tarefa putTarefa(@RequestBody Tarefa tarefa, @PathVariable Long id) {
    Optional<Tarefa> resposta = tarefaRepo.findById(id);
    if (resposta.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    resposta.get().setDescricao(tarefa.getDescricao());
    resposta.get().setConcluido(tarefa.isConcluido());

    return tarefaRepo.save(resposta.get());
  }

  @DeleteMapping("/tarefa/{id}")
  public void deleteTarefa(@PathVariable Long id) {
    if (tarefaRepo.existsById(id)) {
      tarefaRepo.deleteById(id);
    } else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
  }

}
