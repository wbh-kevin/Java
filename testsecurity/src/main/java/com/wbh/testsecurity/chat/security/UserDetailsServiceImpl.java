package com.wbh.testsecurity.chat.security;

import com.wbh.testsecurity.chat.entity.Attempt;
import com.wbh.testsecurity.chat.function.Code;
import com.wbh.testsecurity.chat.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        try {
            //1、根据前端传过来的用户名（也可以是手机，邮箱等）,取数据进行查询，得到用户得具体信息
            com.wbh.testsecurity.chat.entity.User user = userMapper.findUser(s);
            Attempt attempt = new Attempt(user.getId());
            if (!attempt.ableToAccess()){
                return new User(s, "null", AuthorityUtils.commaSeparatedStringToAuthorityList("null"));
            }
//            String rawPassword = Code.decrypt(user.getPassword());
            String password = user.getPassword();
            //2、对密码（数据库中得密码都会在注册时进行一个加密，使用passwordEncoder的encode()方法进行加密）进行解析
//            String password = passwordEncoder.encode(rpassword);
            //3、返回上面所说的UserDetails的实现类，里面存储了数据库里的信息，通过它，会去与浏览器传过来的用户信息进行比对，第三个为权限信息，此处也是自定义，实际应该从数据库中查找
            return new User(s, password, AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRole()));
        }catch (Exception e){
            return new User(s, "null", AuthorityUtils.commaSeparatedStringToAuthorityList("null"));
        }
    }
}

