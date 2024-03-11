package com.db.crudpessoabackend.controller.endereco;

import org.springframework.web.bind.annotation.RestController;
import com.db.crudpessoabackend.domain.usuario.endereco.Endereco;
import com.db.crudpessoabackend.domain.usuario.endereco.dto.EnderecoDTO;
import com.db.crudpessoabackend.domain.usuario.endereco.dto.EnderecoRespostaDTO;
import com.db.crudpessoabackend.domain.usuario.endereco.interfaces.IEnderecoService;
import com.db.crudpessoabackend.domain.usuario.endereco.utils.EnderecoUtils;
import com.db.crudpessoabackend.domain.usuario.pessoa.Pessoa;
import com.db.crudpessoabackend.domain.usuario.pessoa.interfaces.IPessoaService;
import com.db.crudpessoabackend.infra.seguranca.interfaces.ITokenService;
import com.db.crudpessoabackend.infra.seguranca.utils.TokenUtils;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import java.time.LocalDateTime;

@RestController
@RequestMapping(value = "/usuario/endereco")
@AllArgsConstructor
public class EnderecoUsuarioController {
    
    private ITokenService tokenService;
    private TokenUtils tokenUtils;
    private IPessoaService pessoaService;
    private IEnderecoService enderecoService;
    private EnderecoUtils enderecoUtils;

    @PostMapping("/adicionar")
    public ResponseEntity<EnderecoRespostaDTO> adicionarEndereco(
                @RequestHeader("Authorization") String headerAutorizacao, 
                @RequestBody EnderecoDTO enderecoDTO) {
        String token = tokenUtils.validarToken(headerAutorizacao);
        String cpf = tokenService.getSubject(token);
        Pessoa pessoa = pessoaService.buscarPorCpf(cpf);
        pessoa.setUpdatedAt(LocalDateTime.now());
        pessoa.setUpdatedBy(pessoa.getContato().getEmail());
        Endereco endereco = enderecoDTO.converterParaEntidadeComDono(pessoa);
        Endereco enderecoSalvo = enderecoService.adicionar(endereco);
        EnderecoRespostaDTO resposta = new EnderecoRespostaDTO(enderecoSalvo);
        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<EnderecoRespostaDTO> excluirEndereco(
                @RequestHeader("Authorization") String headerAutorizacao, 
                @PathVariable("id") Long id){
        Pessoa pessoa = enderecoUtils.validarPermissaoDeAlterarEndereco(headerAutorizacao, id);
        pessoaService.atualizar(pessoa.getCpf(), pessoa);
        Endereco endereco = enderecoService.excluir(id);
        EnderecoRespostaDTO resposta = new EnderecoRespostaDTO(endereco);
        return ResponseEntity.status(HttpStatus.OK).body(resposta);
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<EnderecoRespostaDTO> atualizarEndereco(
                @RequestHeader("Authorization") String headerAutorizacao, 
                @PathVariable("id") Long id, 
                @RequestBody EnderecoDTO enderecoDTO){
        Pessoa pessoa = enderecoUtils.validarPermissaoDeAlterarEndereco(headerAutorizacao, id);
        pessoaService.atualizar(pessoa.getCpf(), pessoa);
        Endereco novoEndereco = enderecoDTO.converterParaEntidadeComDono(pessoa);
        Endereco enderecoAtualizado = enderecoService.atualizar(id, novoEndereco);
        EnderecoRespostaDTO resposta = new EnderecoRespostaDTO(enderecoAtualizado);
        return ResponseEntity.status(HttpStatus.OK).body(resposta);
    }
}
