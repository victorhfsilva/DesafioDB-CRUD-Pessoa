package com.db.crudpessoabackend.domain.usuario.pessoa.servicos;

import java.time.LocalDate;
import java.time.Period;
import org.springframework.stereotype.Service;
import com.db.crudpessoabackend.domain.usuario.pessoa.Pessoa;
import com.db.crudpessoabackend.domain.usuario.pessoa.interfaces.IMostrarIdadeService;
import com.db.crudpessoabackend.domain.usuario.pessoa.repositorios.PessoaRepository;
import com.db.crudpessoabackend.infra.excecoes.EntidadeNaoEncontradaException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class MostrarIdadeService implements IMostrarIdadeService {

    private PessoaRepository pessoaRepository;

    @Override
    public int mostrarIdade(String cpf) {
        Pessoa pessoa = pessoaRepository.findByCpf(cpf)
                                    .orElseThrow(() -> 
                                        new EntidadeNaoEncontradaException(
                                        "Não foi possível encontrar a pessoa com CPF" + cpf)
                                    );

        LocalDate dataDeNascimento = pessoa.getDataDeNascimento();
        return Period.between(dataDeNascimento, LocalDate.now()).getYears();
    }
}
