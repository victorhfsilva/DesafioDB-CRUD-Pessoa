package com.db.crudpessoabackend.domain.usuario.pessoa.servicos;

import org.springframework.stereotype.Service;

import com.db.crudpessoabackend.domain.usuario.contato.Contato;
import com.db.crudpessoabackend.domain.usuario.contato.repositorios.ContatoRepository;
import com.db.crudpessoabackend.domain.usuario.endereco.repositorios.EnderecoRepository;
import com.db.crudpessoabackend.domain.usuario.pessoa.Pessoa;
import com.db.crudpessoabackend.domain.usuario.pessoa.interfaces.IRegistrarPessoaService;
import com.db.crudpessoabackend.domain.usuario.pessoa.repositorios.PessoaRepository;
import com.db.crudpessoabackend.infra.excecoes.ErroDePersistenciaException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RegistrarPessoaService implements IRegistrarPessoaService{

    private PessoaRepository pessoaRepository;
    private EnderecoRepository enderecoRepository;
    private ContatoRepository contatoRepository;

    @Override
    public Pessoa registrar(Pessoa pessoa) {
        try {
            Contato contato = pessoa.getContato();
            contatoRepository.save(contato);
    
            Pessoa pessoaSalva = pessoaRepository.save(pessoa);
    
            pessoa.getEnderecos().stream().forEach(endereco -> {
                endereco.setPessoa(pessoaSalva);
                enderecoRepository.save(endereco);
            });
            
            return pessoaRepository.findById(pessoaSalva.getId())
                                    .orElseThrow(() -> 
                                    new ErroDePersistenciaException("Não foi possível persistir " + pessoa.getNome()));
        
        } catch (Exception ex){
            throw new ErroDePersistenciaException("Não foi possível persistir " + pessoa.getNome());
        }

    }
    

}
