package com.db.crudpessoabackend.domain.usuario.pessoa;

import org.springframework.stereotype.Service;

import com.db.crudpessoabackend.domain.usuario.contato.Contato;
import com.db.crudpessoabackend.domain.usuario.contato.ContatoRepository;
import com.db.crudpessoabackend.domain.usuario.endereco.EnderecoRepository;
import com.db.crudpessoabackend.domain.usuario.pessoa.interfaces.IRegistrarPessoaService;
import com.db.crudpessoabackend.infra.excecoes.ErroDePersistencia;

@Service
public class RegistrarPessoaService implements IRegistrarPessoaService{

    private PessoaRepository pessoaRepository;
    private EnderecoRepository enderecoRepository;
    private ContatoRepository contatoRepository;

    public RegistrarPessoaService(PessoaRepository pessoaRepository, 
                            EnderecoRepository enderecoRepository, 
                            ContatoRepository contatoRepository){
        this.pessoaRepository = pessoaRepository;
        this.enderecoRepository = enderecoRepository;
        this.contatoRepository = contatoRepository;
    }

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
                                    new ErroDePersistencia("Não foi possível persistir " + pessoa.getNome()));
        
        } catch (Exception ex){
            throw new ErroDePersistencia("Não foi possível persistir " + pessoa.getNome());
        }

    }
    

}
