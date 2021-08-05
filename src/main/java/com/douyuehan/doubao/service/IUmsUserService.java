package com.douyuehan.doubao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.douyuehan.doubao.model.dto.LoginDTO;
import com.douyuehan.doubao.model.dto.RegisterDTO;
import com.douyuehan.doubao.model.entity.UmsUser;

public interface IUmsUserService extends IService<UmsUser> {

    /**
     * 自定义注册功能
     * @return
     */
    UmsUser executeRegister(RegisterDTO dto);

    /**
     * 获取用户信息
     * @param username
     * @return
     */
    UmsUser getUserByUsername(String username);

    /**
     *用户登录
     * @param dto
     * @return
     */
    String executeLogin(LoginDTO dto);
}
