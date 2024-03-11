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

    private BuscarPessoaPorCpf buscarPessoaPorCpf;

    @Override
    public int mostrarIdade(String cpf) {
        Pessoa pessoa = buscarPessoaPorCpf.buscarPorCpf(cpf);
        LocalDate dataDeNascimento = pessoa.getDataDeNascimento();
        return Period.between(dataDeNascimento, LocalDate.now()).getYears();
    }
}
