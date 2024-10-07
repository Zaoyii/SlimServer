package com.zcyi.slimserver.UserPart;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.zcyi.slimserver.Base.ApiResult;
import com.zcyi.slimserver.Util.Constant;
import com.zcyi.slimserver.Util.Mail.MailService;
import com.zcyi.slimserver.Util.UtilMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserServiceImpl userService;

    @Autowired
    private MailService mailService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    @ResponseBody
    public ApiResult<User> userLogin(User user) {
        if (user.getUserName() == null || user.getUserPassword() == null) {
            return ApiResult.failed("参数有误");
        }
        if (!user.getUserName().isEmpty() && !user.getUserPassword().isEmpty()) {
            User login = userService.login(user.getUserName(), user.getUserPassword());
            if (login != null) {
                try {
                    if (login.getUserToken() == null || UtilMethod.verify(login.getUserToken()).getExpiresAt().before(Calendar.getInstance().getTime())) {
                        login.setUserToken(createTokenByUserNameAndUserId(login.getUserName(), login.getUserId()));
                        userService.updateTokenByUserId(login.getUserId(), login.getUserToken());
                    }
                } catch (TokenExpiredException e) {
                    e.printStackTrace();
                    login.setUserToken(createTokenByUserNameAndUserId(login.getUserName(), login.getUserId()));
                    userService.updateTokenByUserId(login.getUserId(), login.getUserToken());
                }
                return ApiResult.success(login);
            }
            return ApiResult.failed("用户名或密码错误");
        } else {
            return ApiResult.failed("用户名或密码为空");
        }
    }

    @PostMapping("/register")
    @ResponseBody
    public ApiResult<String> userRegister(String username, String password, String email, String code) {
        if (username == null || password == null || email == null || code == null) {
            return ApiResult.failed("参数有误");
        }

        if (mailService.unVerifyEmail(email, code, Constant.VERIFY_EMAIL_TYPE_REGISTER)) {
            return ApiResult.failed("验证失败，错误验证码或验证码已失效~");
        }
        if (!checkUserName(username).success) {
            return ApiResult.failed("用户名已存在");
        }
        if (!username.isEmpty() && !password.isEmpty() && !code.isEmpty()) {
            String s = UtilMethod.generateSaltPassword(password);
            userService.addUser(new User(username, s, email));
            return ApiResult.success("注册成功");
        } else {
            return ApiResult.failed("用户名或密码为空");
        }
    }

    @PostMapping("/initUserData")
    @ResponseBody
    public ApiResult<String> initUserData(@RequestHeader("Authorization") String authorization, String nickname, int gender, int age, int weight, int height, int goal) {
        if (nickname == null || gender == -1 || age == -1 || weight == -1 || height == -1 || goal == -1) {
            return ApiResult.failed("参数有误");
        }
        DecodedJWT verify = UtilMethod.verify(authorization);
        String userId = verify.getClaim("userId").asString();
        String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
        Weight weightOb = new Weight();
        weightOb.setWeightUserId(Integer.parseInt(userId));
        weightOb.setWeightData(weight);
        weightOb.setWeightCreateTime(today);
        userService.addUserWeightData(weightOb);
        userService.initUserData(nickname, gender, age, weight, height, goal, Integer.parseInt(userId));
        return ApiResult.success("初始化用户数据成功");
    }

    @PostMapping("/verifyEmail")
    @ResponseBody
    public ApiResult<String> verifyEmail(String email, String code, int type) {

        if (mailService.unVerifyEmail(email, code, type)) {
            return ApiResult.failed("验证失败，错误验证码或验证码已失效~");
        } else {
            return ApiResult.success("验证成功");
        }
    }

    @PostMapping("/checkUserName")
    @ResponseBody
    public ApiResult<User> checkUserName(String username) {
        if (username == null || username.isEmpty()) {
            return ApiResult.failed("参数有误");
        }
        User checkUsername = userService.checkUsername(username);
        if (checkUsername == null) {
            return ApiResult.success("用户名可用");
        } else {
            return ApiResult.failed("用户名不可用");
        }
    }

    @GetMapping("/loadMyself")
    @ResponseBody
    public ApiResult<User> loadMyself(@RequestHeader("Authorization") String authorization) {
        DecodedJWT verify = UtilMethod.verify(authorization);
        String userId = verify.getClaim("userId").asString();
        User entityUser = userService.selectByUserId(Long.parseLong(userId));
        if (entityUser != null) {
            return ApiResult.success(entityUser);
        }
        return ApiResult.failed();
    }

    @PostMapping("/updateEmail")
    @ResponseBody
    public ApiResult<User> updateEmail(@RequestHeader("Authorization") String authorization, String email, String code) {
        if (email == null || code == null) {
            return ApiResult.failed("参数有误");
        }
        if (mailService.unVerifyEmail(email, code, Constant.VERIFY_EMAIL_TYPE_UPDATE)) {
            return ApiResult.failed("验证失败，错误验证码或验证码已失效~");
        }

        DecodedJWT verify = UtilMethod.verify(authorization);
        long userId = Long.parseLong(verify.getClaim("userId").asString());
        userService.updateEmailByUserId(userId, email);
        return ApiResult.success("更换成功");
    }


    @PostMapping("/sendEmail")
    @ResponseBody
    public ApiResult<String> sendEmail(String email, int type) {
        if (mailService.sendMimeMail(email, type)) {
            System.out.println("收到邮箱发送请求：" + email);
            return ApiResult.success("发送成功");
        } else {
            return ApiResult.success("发送失败，请检查网络状态~");
        }
    }

    @PostMapping("/updateAvatar")
    @ResponseBody
    public ApiResult<String> updateAvatar(@RequestHeader String Authorization, MultipartFile avatar) {
        if (avatar != null && avatar.isEmpty()) {
            return ApiResult.failed("参数有误");
        }
        if (Authorization.equals("")) {
            return ApiResult.failed("参数有误");
        }
        DecodedJWT tokenInfo = UtilMethod.getTokenInfo(Authorization);
        Map<String, Claim> claims = tokenInfo.getClaims();
        Claim option1 = claims.get("userId");
        String userId = option1.asString();
        if (userId.equals("")) {
            return ApiResult.failed("参数有误");
        }
        String extension;
        assert avatar != null;
        int lastDotIndex = Objects.requireNonNull(avatar.getOriginalFilename()).lastIndexOf(".");
        if (lastDotIndex != -1) {
            extension = avatar.getOriginalFilename().substring(lastDotIndex).toLowerCase();
        } else {
            return ApiResult.failed("上传失败");
        }
        String fileName = System.currentTimeMillis() + extension;
        String imgUrl = userId + "/" + fileName;
        String path = Constant.USER_AVATAR_URL + imgUrl;
        File avatarFile = new File(path);
        if (!avatarFile.exists()) {
            boolean mkdir = avatarFile.getParentFile().mkdir();
            if (mkdir){
                try {
                    //创建文件
                    boolean newFile = avatarFile.createNewFile();
                    if (newFile){
                        try {
                            avatar.transferTo(avatarFile);
                            userService.updateAvatar(fileName, Integer.parseInt(userId));
                            return ApiResult.success("上传成功", fileName);
                        } catch (IOException e) {
                            e.printStackTrace();
                            return ApiResult.failed("上传失败");
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return ApiResult.failed("上传失败");
                }
            }
        }
        return ApiResult.failed("上传失败");
    }

    @PostMapping("/updateWeight")
    @ResponseBody
    public ApiResult<String> updateWeight(@RequestBody Weight weight) {
        if (weight != null) {
            String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
            weight.setWeightCreateTime(today);
            ArrayList<Weight> weightData = userService.selectUserWeightById(weight.getWeightUserId());
            String weightCreateTime = weightData.get(0).getWeightCreateTime();
            if (weightCreateTime.equals(today)) {
                userService.updateWeightData(weight.getWeightData(), weightData.get(0).getWeightId());
            } else {
                userService.addUserWeightData(weight);
            }

            userService.updateUserWeight(weight.getWeightData(), weight.getWeightUserId());
            return ApiResult.success("更新成功");
        } else {
            return ApiResult.failed("参数有误");
        }
    }

    @GetMapping("/getWeightByUserId")
    @ResponseBody
    public ApiResult<ArrayList<Weight>> getWeightByUserId(long userId) {
        ArrayList<Weight> weight = userService.selectUserWeightById(userId);
        System.out.println(weight + "--=-=--=");
        Collections.reverse(weight);
        if (weight.size() > 0) {
            return ApiResult.success(weight);
        }
        return ApiResult.failed("获取失败，请稍后尝试~");
    }

    @PostMapping("/updateHeight")
    @ResponseBody
    public ApiResult<String> updateHeight(Double height, int userId) {
        if (height > 0) {
            userService.updateUserHeight(height, userId);
            return ApiResult.success("更新成功");
        } else {
            return ApiResult.failed("参数有误");
        }
    }

    @PostMapping("/updateNickname")
    @ResponseBody
    public ApiResult<String> updateNickname(String nickname, int userId) {
        if (nickname.length() > 1) {
            userService.updateUserNickname(nickname, userId);
            return ApiResult.success("更新成功");
        } else {
            return ApiResult.failed("参数有误");
        }
    }

    @GetMapping("/test")
    @ResponseBody
    public ApiResult<String> test() {

        return ApiResult.success("成功");

    }


    private String createTokenByUserNameAndUserId(String userName, Long userId) {
        Map<String, String> tokenInfo = new HashMap<>(5);
        tokenInfo.put("userName", userName);
        tokenInfo.put("userId", String.valueOf(userId));
        return UtilMethod.getToken(tokenInfo);
    }

}
