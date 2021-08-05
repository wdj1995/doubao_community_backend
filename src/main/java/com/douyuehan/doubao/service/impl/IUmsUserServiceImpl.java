package com.douyuehan.doubao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.douyuehan.doubao.common.exception.ApiAsserts;
import com.douyuehan.doubao.jwt.JwtUtil;
import com.douyuehan.doubao.mapper.UmsUserMapper;
import com.douyuehan.doubao.model.dto.LoginDTO;
import com.douyuehan.doubao.model.dto.RegisterDTO;
import com.douyuehan.doubao.model.entity.UmsUser;
import com.douyuehan.doubao.service.IUmsUserService;
import com.douyuehan.doubao.utils.MD5Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;

@Slf4j
@Service
public class IUmsUserServiceImpl
        extends ServiceImpl<UmsUserMapper, UmsUser>
        implements IUmsUserService {

    @Override
    public UmsUser executeRegister(RegisterDTO dto) {
        //查询是否有相同用户名的用户   mybatis中的对象LambdaQueryWrapper:设定查找条件的
        LambdaQueryWrapper<UmsUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UmsUser::getUsername, dto.getName()).or().eq(UmsUser::getEmail, dto.getEmail());
        UmsUser umsUser = baseMapper.selectOne(wrapper);//mybatis plus中的selectOne查找一条记录的方法
        if (!ObjectUtils.isEmpty(umsUser)){
            ApiAsserts.fail("账号或邮箱已存在");
        }
        UmsUser addUser = UmsUser.builder() //@Builder()可以使用如下的方式设置属性
                .username(dto.getName())
                .alias(dto.getName())
                .password(MD5Utils.getPwd(dto.getPass()))
                .email(dto.getEmail())
                .createTime(new Date())
                .status(true)
                .build();
        baseMapper.insert(addUser);
        return addUser;
    }

    @Override
    public UmsUser getUserByUsername(String username) {//获取用户信息
        return baseMapper.selectOne( //查找到一条记录
                new LambdaQueryWrapper<UmsUser>().eq(UmsUser::getUsername, username));
    }

    @Override
    public String executeLogin(LoginDTO dto) {
        String token = null;
        try {
            UmsUser user = getUserByUsername(dto.getUsername());
            String encodePwd = MD5Utils.getPwd(dto.getPassword()); //加密操作
            if(!encodePwd.equals(user.getPassword())) //与数据库加密的密码进行对比
            {
                throw new Exception("密码错误");
            }
            //生成token 使用用户名作为生成token的种子
            token = JwtUtil.generateToken(String.valueOf(user.getUsername()));
        } catch (Exception e) {
            log.warn("用户不存在or密码验证失败=======>{}", dto.getUsername());
        }
        return token;
    }


}
