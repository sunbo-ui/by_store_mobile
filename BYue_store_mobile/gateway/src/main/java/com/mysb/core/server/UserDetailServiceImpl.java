package com.mysb.core.server;

import com.mysb.core.interfac.LoginFeignClient;
import com.mysb.core.pojo.customer.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserDetailServiceImpl implements ReactiveUserDetailsService {

    @Autowired
    private LoginFeignClient loginFeignClient;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        /*定义权限集合*/
        List<GrantedAuthority> authority = new ArrayList<>();
        SimpleGrantedAuthority role_seller = new SimpleGrantedAuthority("ROLE_USER");
        authority.add(role_seller);
        if (username == null) {
            return null;
        }
        Customer customer = loginFeignClient.findUserByUsername(username);
        if(customer != null){
            if (customer.getUsername().equals(username)) {
                UserDetails user = User.withUsername(customer.getUsername())
                        .password(customer.getPassword())
                        .roles("USER")
                        .build();
                return Mono.just(user);
            }
        }
        return Mono.error(new UsernameNotFoundException("User Not Found"));
    }


}
