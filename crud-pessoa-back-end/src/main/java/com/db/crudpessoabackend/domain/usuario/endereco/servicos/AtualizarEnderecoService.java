package com.db.crudpessoabackend.domain.usuario.endereco.servicos;

import org.springframework.stereotype.Service;
import com.db.crudpessoabackend.domain.usuario.endereco.Endereco;
import com.db.crudpessoabackend.domain.usuario.endereco.interfaces.IAtualizarEnderecoService;
import com.db.crudpessoabackend.domain.usuario.endereco.repositorios.EnderecoRepository;
import com.db.crudpessoabackend.domain.usuario.pessoa.Pessoa;
import com.db.crudpessoabackend.domain.usuario.pessoa.repositorios.PessoaRepository;
import com.db.crudpessoabackend.infra.excecoes.EntidadeNaoEncontradaException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AtualizarEnderecoService implements IAtualizarEnderecoService {

    private EnderecoRepository enderecoRepository;
    private BuscarEnderecoPorId buscarEnderecoPorId;
    private PessoaRepository pessoaRepository;

    @Override
    public Endereco atualizar(Long id, Endereco novoEndereco) {
       Endereco enderecoSalvo = buscarEnderecoPorId.buscarPorId(id);

        Pessoa novaPessoa = novoEndereco.getPessoa();
        Pessoa pessoaSalva = enderecoSalvo.getPessoa();

        validaSePessoaExiste(novaPessoa);

        enderecoSalvo.setBairro(novoEndereco.getBairro());
        enderecoSalvo.setCep(novoEndereco.getCep());
        enderecoSalvo.setCidade(novoEndereco.getCidade());
        enderecoSalvo.setComplemento(novoEndereco.getComplemento());
        enderecoSalvo.setEstado(novoEndereco.getEstado());
        enderecoSalvo.setNumero(novoEndereco.getNumero());
        enderecoSalvo.setRua(novoEndereco.getRua());
        
        if (!pessoaSalva.getCpf().equals(novaPessoa.getCpf())){
            enderecoSalvo.setPessoa(novaPessoa);  
        }
        
        return enderecoRepository.save(enderecoSalvo);
    }

    private void validaSePessoaExiste(Pessoa novaPessoa) {
        if (!pessoaRepository.findByCpf(novaPessoa.getCpf()).isPresent()){
            throw new EntidadeNaoEncontradaException(
                "Não foi possível encontrar a pessoa: " + novaPessoa);
        }
    }
    
}
