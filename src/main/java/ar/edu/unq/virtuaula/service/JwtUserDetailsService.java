package ar.edu.unq.virtuaula.service;

import java.util.Optional;
import java.util.Vector;

import javax.transaction.Transactional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ar.edu.unq.virtuaula.dto.UserDTO;
import ar.edu.unq.virtuaula.exception.UserNotFoundException;
import ar.edu.unq.virtuaula.exception.UserRegisterException;
import ar.edu.unq.virtuaula.model.User;
import ar.edu.unq.virtuaula.repository.UserRepository;
import ar.edu.unq.virtuaula.util.MapperUtil;
import ar.edu.unq.virtuaula.validator.UserValidator;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final MapperUtil mapperUtil;
    private Vector<String> erros = new Vector<>();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return user;
    }

	public UserDTO register(UserDTO userDto) throws UserRegisterException {
		UserValidator.validateSamePassword(userDto.getPassword(), userDto.getRepeatPassword(), erros);
		User newUser = mapperUtil.getMapper().map(userDto, User.class);
		if(!UserValidator.validate(newUser, erros) || !erros.isEmpty()) {
			throw new UserRegisterException("Error register user: " + erros.toString());
		}
		User userBD = userRepository.findByEmail(newUser.getEmail());
		if(userBD != null) {
			throw new UserRegisterException("There is already a registered user with this email: " + newUser.getEmail());
		}
		newUser = userRepository.save(newUser);
		return mapperUtil.getMapper().map(newUser, UserDTO.class);
	}

	public User findById(Long userId) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User not found with user by ID: " + userId);
        }
        return user.get();
	}

}
