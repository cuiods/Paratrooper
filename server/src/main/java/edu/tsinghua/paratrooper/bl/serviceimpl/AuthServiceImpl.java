package edu.tsinghua.paratrooper.bl.serviceimpl;

import edu.tsinghua.paratrooper.bl.service.AuthService;
import edu.tsinghua.paratrooper.bl.vo.AuthVo;
import edu.tsinghua.paratrooper.bl.vo.ResultVo;
import edu.tsinghua.paratrooper.data.entity.TUserEntity;
import edu.tsinghua.paratrooper.data.repository.UserRepository;
import edu.tsinghua.paratrooper.util.constant.ErrorCode;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class AuthServiceImpl implements AuthService {

    /**
     * session expires after 24 hours.
     */
    private static final Long EXPIRES = 86400L;

    private static final String PREFIX = "SOLDIER";

    private static Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private UserRepository userRepository;

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
            return new ResultVo<>(ErrorCode.USER_CANNOT_FIND, "Cannot find user.", null);
        }
        if (!userEntity.getPassword().equals(password)) {
            return new ResultVo<>(ErrorCode.USER_WRONG_PASSWORD, "Wrong password.", null);
        }
        String sessionKey = RandomStringUtils.randomAlphanumeric(64);
        stringRedisTemplate.opsForValue().set(PREFIX+sessionKey, userEntity.getId()+"", EXPIRES, TimeUnit.SECONDS);
        return new ResultVo<>(ErrorCode.SUCCESS,"",new AuthVo(sessionKey, EXPIRES));
    }
}
