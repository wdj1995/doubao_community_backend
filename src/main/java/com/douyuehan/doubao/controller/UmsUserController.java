package com.douyuehan.doubao.controller;

import com.douyuehan.doubao.common.api.ApiResult;
import com.douyuehan.doubao.model.dto.LoginDTO;
import com.douyuehan.doubao.model.dto.RegisterDTO;
import com.douyuehan.doubao.model.entity.UmsUser;
import com.douyuehan.doubao.service.IUmsUserService;
import com.douyuehan.doubao.service.impl.IUmsUserServiceImpl;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

import static com.douyuehan.doubao.jwt.JwtUtil.USER_NAME;

@RestController
@RequestMapping("/ums/user")
public class UmsUserController extends BaseController {
    //在BaseService和BaseController中都使用泛型，到真正创建类的时候才知道具体的对象，对对象进行操作

    @Resource
    private IUmsUserService iUmsUserService;

    /**
     * @responseBody注解的作用是将controller的方法返回的对象通过适当的转换器转换为指定的格式之后，
     * 写入到response对象的body区，通常用来返回JSON数据或者是XML数据。
     *
     * @RequestBody主要用来接收前端传递给后端的json字符串中的数据的(请求体中的数据的)；
     * GET方式无请求体，所以使用@RequestBody接收数据时，前端不能使用GET方式提交数据，而是用POST方式进行提交。
     * 在后端的同一个接收方法里，@RequestBody与@RequestParam()可以同时使用，@RequestBody最多只能有一个，而@RequestParam()可以有多个。
     *
     * @Valid用于验证注解是否符合要求，直接加在变量user之前，在变量中添加验证信息的要求，
     * 当不符合要求时就会在方法中返回message 的错误提示信息。
     * @param dto
     * @return
     */

    @RequestMapping(value="/register", method = RequestMethod.POST)
    public ApiResult<Map<String,Object>> register(@Valid @RequestBody RegisterDTO dto){ //dto必须和客户端一模一样
        UmsUser user = iUmsUserService.executeRegister(dto);
        if (ObjectUtils.isEmpty(user)){
            return ApiResult.failed("账号注册失败");
        }
        Map<String, Object> map = new HashMap<>(16);
        map.put("user",user);
        return ApiResult.success(map);
    }

    @RequestMapping(value = "/login", method=RequestMethod.POST)
    public ApiResult<Map<String,String>> login(@Valid @RequestBody LoginDTO dto){ //dto必须和客户端一模一样
        String token = iUmsUserService.executeLogin(dto);
        if (ObjectUtils.isEmpty(token)){
            return ApiResult.failed("账号密码错误");
        }
        Map<String, String> map = new HashMap<>(16);
        map.put("token",token);
        return ApiResult.success(map,"登录成功");
    }

    @RequestMapping(value = "/info", method=RequestMethod.GET)
    //@RequestHeader 是获取请求头中的数据，通过指定参数 value 的值来获取请求头中指定的参数值
    public ApiResult<UmsUser> getUser(@RequestHeader(value=USER_NAME) String userName){ //dto必须和客户端一模一样
        UmsUser user = iUmsUserService.getUserByUsername(userName);
        return ApiResult.success(user);
    }

}
