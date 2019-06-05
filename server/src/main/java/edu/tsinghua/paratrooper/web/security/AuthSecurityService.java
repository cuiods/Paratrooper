package edu.tsinghua.paratrooper.web.security;

import edu.tsinghua.paratrooper.data.entity.TUserEntity;
import edu.tsinghua.paratrooper.data.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthSecurityService implements UserDetailsService {

    @Resource
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        TUserEntity entity = userRepository.findByName(s);
        if (entity == null) throw new UsernameNotFoundException("Cannot find user");
        List<GrantedAuthority> authorities = entity.getAuthorityEntities().stream()
                .map(authorityEntity -> new SimpleGrantedAuthority(authorityEntity.getName()))
                .collect(Collectors.toList());
        return new User(entity.getName(),entity.getPassword(),
                true, true, true, true, authorities);
    }
}
