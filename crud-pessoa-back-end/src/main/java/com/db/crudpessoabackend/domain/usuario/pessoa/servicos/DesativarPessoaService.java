package com.db.crudpessoabackend.domain.usuario.pessoa.servicos;

import org.springframework.stereotype.Service;
import com.db.crudpessoabackend.domain.usuario.pessoa.Pessoa;
import com.db.crudpessoabackend.domain.usuario.pessoa.interfaces.IDesativarPessoaService;
import com.db.crudpessoabackend.domain.usuario.pessoa.repositorios.PessoaRepository;
import com.db.crudpessoabackend.infra.excecoes.EntidadeNaoEncontradaException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DesativarPessoaService implements IDesativarPessoaService {
    
    private PessoaRepository pessoaRepository;

    public Pessoa desativar(String cpf) {
        Pessoa pessoa = pessoaRepository.findByCpf(cpf)
                                        .orElseThrow(() -> 
                                        new EntidadeNaoEncontradaException(
                                            "Não foi possível encontrar a pessoa com CPF " + cpf));
        pessoa.setActive(false);
        return pessoaRepository.save(pessoa);
    }
}
