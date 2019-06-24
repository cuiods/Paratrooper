package edu.tsinghua.paratrooper.bl.serviceimpl;

import edu.tsinghua.paratrooper.bl.service.AuthService;
import edu.tsinghua.paratrooper.bl.vo.AuthVo;
import edu.tsinghua.paratrooper.bl.vo.ResultVo;
import edu.tsinghua.paratrooper.bl.vo.SoldierVo;
import edu.tsinghua.paratrooper.data.entity.TUserEntity;
import edu.tsinghua.paratrooper.data.repository.SoldierRepository;
import edu.tsinghua.paratrooper.data.repository.UserRepository;
import edu.tsinghua.paratrooper.util.constant.ErrorCode;
import edu.tsinghua.paratrooper.util.jwt.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    /**
     * session expires after 24 hours.
     */
    private static final Long EXPIRES = 86400L;

    private static Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Resource
    private UserRepository userRepository;

    @Resource
    private SoldierRepository soldierRepository;

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private JwtUtil jwtUtil;

    @Value("${paratrooper.tokenHead}")
    private String tokenHead;

    /**
     * Login to system
     *
     * @param name     soldier name / id
     * @param password password
     * @return {@link AuthVo}
     */
    @Override
    public ResultVo<AuthVo> login(String name, String password) {
        TUserEntity userEntity = userRepository.findByName(name);
        if (null == userEntity) {
            return new ResultVo<>(ErrorCode.USER_CANNOT_FIND, "找不到该用户.", null);
        }
        if (!userEntity.getPassword().equals(password)) {
            return new ResultVo<>(ErrorCode.USER_WRONG_PASSWORD, "密码错误.", null);
        }

        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(name, password);
        final Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", userEntity.getId());
        claims.put("name", userEntity.getName());
        claims.put("time", new Date().getTime()+"");
        final String token = jwtUtil.generateToken(claims);
        return new ResultVo<>(ErrorCode.SUCCESS,"",
                new AuthVo(tokenHead+token, EXPIRES, new SoldierVo(soldierRepository.findOne(userEntity.getId()))));
    }
}
