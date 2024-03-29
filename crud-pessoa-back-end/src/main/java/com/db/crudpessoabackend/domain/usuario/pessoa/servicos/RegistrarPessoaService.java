package com.db.crudpessoabackend.domain.usuario.pessoa.servicos;

import java.util.List;
import org.springframework.stereotype.Service;
import com.db.crudpessoabackend.domain.usuario.contato.Contato;
import com.db.crudpessoabackend.domain.usuario.contato.repositorios.ContatoRepository;
import com.db.crudpessoabackend.domain.usuario.endereco.Endereco;
import com.db.crudpessoabackend.domain.usuario.endereco.repositorios.EnderecoRepository;
import com.db.crudpessoabackend.domain.usuario.pessoa.Pessoa;
import com.db.crudpessoabackend.domain.usuario.pessoa.interfaces.IRegistrarPessoaService;
import com.db.crudpessoabackend.domain.usuario.pessoa.repositorios.PessoaRepository;
import com.db.crudpessoabackend.infra.excecoes.ErroDePersistenciaException;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrarPessoaService implements IRegistrarPessoaService{

    private PessoaRepository pessoaRepository;
    private EnderecoRepository enderecoRepository;
    private ContatoRepository contatoRepository;

    @Override
    public Pessoa registrar(Pessoa pessoa, Pessoa editor) {
        try {
            if (editor != null){
                pessoa.setAtivo(true);
                pessoa.setCriadoAs(LocalDateTime.now());
                pessoa.setCriadoPor(editor.getContato().getEmail());
            }

            Contato contato = pessoa.getContato();
            
            contatoRepository.save(contato);

            List<Endereco> enderecos = pessoa.getEnderecos();
            
            Pessoa pessoaSalva = pessoaRepository.save(pessoa);
    
            enderecos.stream().forEach(endereco -> {
                endereco.setPessoa(pessoaSalva);
                enderecoRepository.save(endereco);
            });

            
            return pessoaRepository.findById(pessoaSalva.getId())
                                    .orElseThrow();

        } catch (Exception ex){
            throw new ErroDePersistenciaException("Não foi possível persistir " + pessoa.getNome(), ex.getMessage());
        }

    }
    

}
