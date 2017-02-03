package com.kosarict.config;

import com.kosarict.dao.RoleDao;
import com.kosarict.dao.UserDao;
import com.kosarict.entity.Permission;
import com.kosarict.entity.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

	static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);
	
	@Autowired
	private UserDao userService;

	@Autowired
    private RoleDao roleDao;


	
	@Override
	public UserDetails loadUserByUsername(String userName)
			throws UsernameNotFoundException {
		Users user = userService.findUserByUserName(userName);
		logger.info("User : {}", user);
		if(user==null){
			logger.info("User not found");
			throw new UsernameNotFoundException("Username not found");
		}
			return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
				 true, true, true, true, getGrantedAuthorities(user));
	}

	
	private List<GrantedAuthority> getGrantedAuthorities(Users user) {
        Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

        List<Permission> permissionList = roleDao.getRolePermissionList(user.getUserId());

        // Build user's authorities
        for (Permission permission : permissionList) {

            setAuths.add(new SimpleGrantedAuthority(permission.getTitle()));
        }

        List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(
                setAuths);

        return Result;
    }
	
}
 