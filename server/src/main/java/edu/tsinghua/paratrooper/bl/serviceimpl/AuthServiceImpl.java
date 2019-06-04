package edu.tsinghua.paratrooper.bl.serviceimpl;

import edu.tsinghua.paratrooper.bl.service.AuthService;
import edu.tsinghua.paratrooper.bl.vo.AuthVo;
import edu.tsinghua.paratrooper.bl.vo.ResultVo;
import edu.tsinghua.paratrooper.data.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AuthServiceImpl implements AuthService {

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
        return null;
    }
}
