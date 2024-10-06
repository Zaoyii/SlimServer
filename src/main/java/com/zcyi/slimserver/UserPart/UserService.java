package com.zcyi.slimserver.UserPart;


import com.zcyi.slimserver.TaskPart.Task;

import java.util.ArrayList;
import java.util.Map;

/**
 * Service层
 *
 * @author ZaoYi
 */
public interface UserService {

    /**
     * 添加用户
     *
     * @param user 参数
     * @return 1/0
     */
    int addUser(User user);

    /**
     * 登录
     *
     * @param username     用户名
     * @param userPassword 密码
     * @return 用户对象
     */
    User login(String username, String userPassword);

    /**
     * 分页查询用户
     *
     * @param page  第几页
     * @param count 几条
     * @return 用户对象数组
     */
    Map<String, Object> selectAllUserByPage(int page, int count);

    /**
     * 更新token
     *
     * @param userId
     * @param userToken
     */
    void updateTokenByUserId(Long userId, String userToken);

    /**
     * 更新头像
     *
     * @param imgUrl
     * @param userId
     * @return
     */
    int updateAvatar(String imgUrl, int userId);

    /**
     * 更新基本信息
     *
     * @param user
     * @return
     */
    int updateUserInfo(User user);

    /**
     * 检查用户名是否存在
     *
     * @param username 参数
     * @return user
     */
    User checkUsername(String username);


    /**
     * 删除用户信息
     *
     * @param userId
     * @return
     */
    int deleteUserInfo(int userId);

    /**
     * 查询指定token and username用户信息
     *
     * @return
     */
    User selectByUserId(long userId);

    ArrayList<Weight> selectUserWeightById(long userId);

    int addUserWeightData(Weight weight);

    int updateUserHeight(Double height, long userId);

    int updateUserWeight(Double weight, long userId);

    int updateUserNickname(String nickname, long userId);

    int updateWeightData(Double weight, long weightId);
    void updateEmailByUserId(Long userId, String email);
    int initUserData(String nickname, int gender, int age, int weight, int height, int goal, int userId);

}
