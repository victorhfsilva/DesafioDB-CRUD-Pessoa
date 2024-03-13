package com.db.crudpessoabackend.domain.usuario.pessoa.servicos;

import com.db.crudpessoabackend.domain.usuario.contato.Contato;
import com.db.crudpessoabackend.domain.usuario.contato.repositorios.ContatoRepository;
import com.db.crudpessoabackend.domain.usuario.pessoa.Pessoa;
import com.db.crudpessoabackend.domain.usuario.pessoa.interfaces.IAtualizarPessoaService;
import com.db.crudpessoabackend.domain.usuario.pessoa.repositorios.PessoaRepository;
import com.db.crudpessoabackend.infra.excecoes.ErroDePersistenciaException;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class AtualizarPessoaService implements IAtualizarPessoaService{

    private PessoaRepository pessoaRepository;
    private ContatoRepository contatoRepository;
    private BuscarPessoaPorCpf buscarPessoaPorCpf;

    @Override
    public Pessoa atualizar(String cpf, Pessoa novaPessoa, Pessoa editor) {
                Pessoa pessoaSalva = buscarPessoaPorCpf.buscarPorCpf(cpf);

                pessoaSalva.setNome(novaPessoa.getNome());
                pessoaSalva.setSobrenome(novaPessoa.getSobrenome());
                pessoaSalva.setSenha(novaPessoa.getSenha());
                pessoaSalva.setPapel(novaPessoa.getPapel());
                pessoaSalva.setDataDeNascimento(novaPessoa.getDataDeNascimento());
                pessoaSalva.setAtualizadoAs(LocalDateTime.now());
                pessoaSalva.setAtualizadoPor(editor.getContato().getEmail());

                try {
                    atualizarContato(pessoaSalva,novaPessoa);
                    return pessoaRepository.save(pessoaSalva); 
                } catch (Exception ex){
                    throw new ErroDePersistenciaException("Não foi possível persistir " + novaPessoa.getNome(), ex.getMessage());
                }

    }

    private void atualizarContato(Pessoa pessoaSalva, Pessoa novaPessoa) {
        Contato novoContato = novaPessoa.getContato();
        Contato contatoSalvo = pessoaSalva.getContato();
        if (!contatoSalvo.equals(novoContato)){
            novoContato.setId(contatoSalvo.getId());
            pessoaSalva.setContato(novaPessoa.getContato());  
            contatoRepository.save(pessoaSalva.getContato());
        }
    }
}
