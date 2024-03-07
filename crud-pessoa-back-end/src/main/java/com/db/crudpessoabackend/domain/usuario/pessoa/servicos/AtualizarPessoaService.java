package com.db.crudpessoabackend.domain.usuario.pessoa.servicos;

import com.db.crudpessoabackend.domain.usuario.pessoa.Pessoa;
import com.db.crudpessoabackend.domain.usuario.pessoa.interfaces.IAtualizarPessoaService;
import com.db.crudpessoabackend.domain.usuario.pessoa.repositorios.PessoaRepository;
import com.db.crudpessoabackend.infra.excecoes.EntidadeNaoEncontradaException;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AtualizarPessoaService implements IAtualizarPessoaService{

    private PessoaRepository pessoaRepository;

    @Override
    public Pessoa atualizar(String cpf, Pessoa novaPessoa) {
                Optional<Pessoa> pessoaSalva = pessoaRepository.findByCpf(cpf);

                if (!pessoaSalva.isPresent()) {
                    throw new EntidadeNaoEncontradaException("Não foi possível encontrar a pessoa com CPF " + cpf);
                }
                
                Pessoa pessoa = pessoaSalva.get();

                pessoa.setNome(novaPessoa.getNome());
                pessoa.setSobrenome(novaPessoa.getSobrenome());
                pessoa.setSenha(novaPessoa.getSenha());
                pessoa.setPapel(novaPessoa.getPapel());
                pessoa.setDataDeNascimento(novaPessoa.getDataDeNascimento());
                pessoa.setContato(novaPessoa.getContato());
                pessoa.setEnderecos(novaPessoa.getEnderecos());
                
                return pessoaRepository.save(pessoa);
    }
    
}
