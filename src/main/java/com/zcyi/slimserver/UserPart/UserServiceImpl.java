package com.zcyi.slimserver.UserPart;


import com.zcyi.slimserver.Util.UtilMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Service
 *
 * @author ZaoYi
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public int addUser(User user) {
        return userDao.addUser(user);
    }

    @Override
    public User login(String username, String userPassword) {
        return userDao.selectByUserNamePassword(username, UtilMethod.generateSaltPassword(userPassword));
    }

    @Override
    public Map<String, Object> selectAllUserByPage(int page, int count) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("Users", userDao.selectAllUserByPage(page, count));
        map.put("AllUserCount", userDao.selectUserCount());
        return map;
    }

    @Override
    public void updateTokenByUserId(Long userId, String userToken) {
        userDao.updateByUserId(userId, userToken);
    }

    @Override
    public int updateAvatar(String imgUrl, int userId) {

        return userDao.updateAvatar(imgUrl, userId);
    }

    @Override
    public int updateUserInfo(User user) {
        return userDao.updateUserInfo(user);
    }

    @Override
    public User checkUsername(String username) {
        return userDao.selectByUserName(username);
    }

    @Override
    public int deleteUserInfo(int userId) {
        return userDao.deleteUserInfo(userId);
    }

    @Override
    public User selectByUserId(long userId) {
        return userDao.selectByUserId(userId);
    }

    @Override
    public ArrayList<Weight> selectUserWeightById(long userId) {
        return userDao.selectUserWeightById(userId);
    }

    @Override
    public int addUserWeightData(Weight weight) {
        return userDao.addUserWeightData(weight);
    }

    @Override
    public int updateUserHeight(Double height, long userId) {
        return userDao.updateUserHeight(height, userId);
    }

    @Override
    public int updateUserWeight(Double weight, long userId) {
        return userDao.updateUserWeight(weight, userId);
    }

    @Override
    public int updateUserNickname(String nickname, long userId) {
        return userDao.updateUserNickname(nickname, userId);
    }

    @Override
    public int updateWeightData(Double weight, long weightId) {
        return userDao.updateWeightData(weight, weightId);
    }

    @Override
    public void updateEmailByUserId(Long userId, String email) {
        userDao.updateEmailByUserId(userId, email);
    }

    @Override
    public int initUserData(String nickname, int gender, int age, int weight, int height, int goal, int userId) {
        return userDao.initUserData(nickname, gender, age, weight, height, goal, userId);
    }


}
