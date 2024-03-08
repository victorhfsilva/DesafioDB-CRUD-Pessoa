package com.db.crudpessoabackend.domain.usuario.pessoa.servicos;

import com.db.crudpessoabackend.domain.usuario.contato.repositorios.ContatoRepository;
import com.db.crudpessoabackend.domain.usuario.endereco.Endereco;
import com.db.crudpessoabackend.domain.usuario.endereco.repositorios.EnderecoRepository;
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
    private EnderecoRepository enderecoRepository;
    private ContatoRepository contatoRepository;

    @Override
    public Pessoa atualizar(String cpf, Pessoa novaPessoa) {
                Optional<Pessoa> pessoaOptional = pessoaRepository.findByCpf(cpf);

                if (!pessoaOptional.isPresent()) {
                    throw new EntidadeNaoEncontradaException("Não foi possível encontrar a pessoa com CPF " + cpf);
                }

                Pessoa pessoaSalva = pessoaOptional.get();

                novaPessoa.getContato().setId(pessoaSalva.getContato().getId());

                pessoaSalva.setNome(novaPessoa.getNome());
                pessoaSalva.setSobrenome(novaPessoa.getSobrenome());
                pessoaSalva.setSenha(novaPessoa.getSenha());
                pessoaSalva.setPapel(novaPessoa.getPapel());
                pessoaSalva.setDataDeNascimento(novaPessoa.getDataDeNascimento());
                pessoaSalva.setContato(novaPessoa.getContato());
                pessoaSalva.setEnderecos(novaPessoa.getEnderecos());
                
                contatoRepository.save(pessoaSalva.getContato());
                Pessoa pessoaAtualizada = pessoaRepository.save(pessoaSalva);
                pessoaSalva.getEnderecos().stream().forEach(endereco -> {
                    endereco.setPessoa(pessoaAtualizada);
                    enderecoRepository.save(endereco);
                });
                
                return pessoaAtualizada;
    }
    
}
