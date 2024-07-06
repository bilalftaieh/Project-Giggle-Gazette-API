package com.gigglegazette.auth_service.security.services;

import com.gigglegazette.auth_service.dto.Permission;
import com.gigglegazette.auth_service.dto.UserData;
import com.gigglegazette.auth_service.service.UserClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserClientService userClientService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserData user = userClientService.getUserByUsername(username);
        List<Permission> permissions = userClientService.getPermissionsByRoleId(user.getRole().getId());
        return UserDetailsImpl.build(user, permissions);
    }
}
