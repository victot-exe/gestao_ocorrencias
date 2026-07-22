package com.victot.gestao_ocorrencias.config.security;

import com.victot.gestao_ocorrencias.repository.PessoaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AutenticationService implements UserDetailsService {

    private final PessoaRepository pessoaRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return pessoaRepository.findByCpf(username)
                .orElseThrow(()-> new UsernameNotFoundException("Usuario ou senha incorretos"));
    }
}
