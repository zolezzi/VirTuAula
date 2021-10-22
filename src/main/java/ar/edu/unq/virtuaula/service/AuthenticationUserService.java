package ar.edu.unq.virtuaula.service;

import javax.transaction.Transactional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import ar.edu.unq.virtuaula.dto.AccountTypeDTO;
import ar.edu.unq.virtuaula.dto.AuthRequestDTO;
import ar.edu.unq.virtuaula.dto.JwtResponseDTO;
import ar.edu.unq.virtuaula.model.Account;
import ar.edu.unq.virtuaula.model.StudentAccount;
import ar.edu.unq.virtuaula.model.User;
import ar.edu.unq.virtuaula.util.JwtTokenUtil;
import ar.edu.unq.virtuaula.util.MapperUtil;
import ar.edu.unq.virtuaula.vo.AccountVO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthenticationUserService {

    private final JwtUserDetailsService jwtUserDetailsService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    private final MapperUtil mapperUtil;

    public JwtResponseDTO login(AuthRequestDTO authRequest) {
        authenticate(authRequest.getUsername(), authRequest.getPassword());

        User userDetails = (User) jwtUserDetailsService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);
        AccountVO account = userDetails.getAccount() == null ? null : createAccountVO(userDetails.getAccount());
        return new JwtResponseDTO(userDetails.getUsername(), token, account);
    }

    private AccountVO createAccountVO(Account account) {
        AccountVO accountVO = new AccountVO();
        AccountTypeDTO accountType = mapperUtil.getMapper().map(account.getAccountType(), AccountTypeDTO.class);
        accountVO.setAccountId(account.getId());
        accountVO.setAccountType(accountType);
        return accountVO;
    }

    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }
}
