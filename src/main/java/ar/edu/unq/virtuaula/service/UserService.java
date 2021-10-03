
package ar.edu.unq.virtuaula.service;

import javax.transaction.Transactional;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ar.edu.unq.virtuaula.dto.UserDTO;
import ar.edu.unq.virtuaula.model.User;
import ar.edu.unq.virtuaula.repository.UserRepository;
import ar.edu.unq.virtuaula.util.MapperUtil;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final MapperUtil mapperUtil;
	
    public UserDTO getUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return mapperUtil.getMapper().map(user, UserDTO.class);
    }
    
    public User findById(Long userId) {
    	return userRepository.findById(userId).get();
    }
    
}

