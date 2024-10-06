package com.zcyi.slimserver.UserPart;


import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * @author ZaoYi
 * 用户Dao层
 */
@Mapper
@Repository
public interface UserDao {

    /**
     * 分页查询用户
     *
     * @param page 查询页数
     * @return 用户列表
     */

    @Select("select * from t_user LIMIT #{page}, #{count}")
    @Results({
            @Result(property = "userId", column = "user_id"),
            @Result(property = "userName", column = "user_name"),
            @Result(property = "userNickName", column = "user_nickname"),
            @Result(property = "userGoal", column = "user_goal"),
            @Result(property = "userGender", column = "user_gender"),
            @Result(property = "userAge", column = "user_age"),
            @Result(property = "userNickName", column = "user_nickname"),
            @Result(property = "userAvatar", column = "user_avatar"),
            @Result(property = "userEmail", column = "user_email"),
            @Result(property = "userWeight", column = "user_weight"),
            @Result(property = "userHeight", column = "user_height"),
    })
    ArrayList<User> selectAllUserByPage(int page, int count);


    /**
     * 添加用户
     *
     * @param user 参数
     * @return 1成功 0失败
     */
    @Insert("insert into t_user (user_name,user_password,user_email) " +
            "value (#{userName},#{userPassword},#{userEmail}) ")
    int addUser(User user);

    /**
     * 登录实现
     *
     * @param username     用户名
     * @param userPassword 密码
     * @return 用户对象
     */
    @Select("select * from t_user where user_name = #{username} and user_password = #{userPassword}")
    @Results({
            @Result(property = "userId", column = "user_id"),
            @Result(property = "userName", column = "user_name"),
            @Result(property = "userNickName", column = "user_nickname"),
            @Result(property = "userGoal", column = "user_goal"),
            @Result(property = "userGender", column = "user_gender"),
            @Result(property = "userAge", column = "user_age"),
            @Result(property = "userNickName", column = "user_nickname"),
            @Result(property = "userAvatar", column = "user_avatar"),
            @Result(property = "userEmail", column = "user_email"),
            @Result(property = "userWeight", column = "user_weight"),
            @Result(property = "userHeight", column = "user_height"),
    })
    User selectByUserNamePassword(String username, String userPassword);

    /**
     * count
     *
     * @return count
     */
    @Select("select count(user_id) from t_user")
    int selectUserCount();

    /**
     * 登录实现
     *
     * @param username 用户名
     * @return 用户对象
     */
    @Select("select * from t_user where user_name = #{username}")
    @Results({
            @Result(property = "userId", column = "user_id"),
    })
    User selectByUserName(String username);

    @Select("select * from t_user where user_id = #{userId} ")
    @Results({
            @Result(property = "userId", column = "user_id"),
            @Result(property = "userName", column = "user_name"),
            @Result(property = "userNickName", column = "user_nickname"),
            @Result(property = "userGoal", column = "user_goal"),
            @Result(property = "userGender", column = "user_gender"),
            @Result(property = "userAge", column = "user_age"),
            @Result(property = "userNickName", column = "user_nickname"),
            @Result(property = "userAvatar", column = "user_avatar"),
            @Result(property = "userEmail", column = "user_email"),
            @Result(property = "userWeight", column = "user_weight"),
            @Result(property = "userHeight", column = "user_height"),
    })
    User selectByUserId(long userId);

    /**
     * 更新用户token
     *
     * @param userId    id
     * @param userToken token
     */
    @Update("update t_user set user_token=#{userToken} where user_id= #{userId}")
    void updateByUserId(Long userId, String userToken);

    /**
     * 更新用户邮箱
     *
     * @param userId id
     * @param email  token
     */
    @Update("update t_user set user_email=#{email} where user_id= #{userId}")
    void updateEmailByUserId(Long userId, String email);

    /**
     * 更新头像
     *
     * @param imgUrl url
     * @param userId id
     * @return 1/0
     */
    @Update("update t_user set user_avatar=#{imgUrl} where user_id= #{userId}")
    int updateAvatar(String imgUrl, int userId);

    @Update("update t_user set user_nickname= #{nickname},user_gender= #{gender},user_age= #{age},user_weight= #{weight},user_height= #{height},user_goal= #{goal} where user_id= #{userId}")
    int initUserData(String nickname, int gender, int age, int weight, int height, int goal, int userId);

    /**
     * 更新用户信息
     *
     * @param user 参数
     * @return 1/0
     */
    @Update("update t_user set user_name= #{username},user_password=#{userPassword},user_email=#{userEmail} where user_id= #{userId}")
    int updateUserInfo(User user);

    /**
     * 删除用户信息
     *
     * @param userId
     * @return
     */
    @Delete("delete from t_user where user_id = #{userId}")
    int deleteUserInfo(int userId);


    @Select("Select * from t_weight where weight_user_id = #{userId} ORDER BY weight_id DESC LIMIT 7")
    @Results({
            @Result(property = "weightId", column = "weight_id"),
            @Result(property = "weightData", column = "weight_data"),
            @Result(property = "weightUserId", column = "weight_user_id"),
            @Result(property = "weightCreateTime", column = "weight_create_time"),
    })
    ArrayList<Weight> selectUserWeightById(long userId);


    @Insert("insert into t_weight (weight_data,weight_user_id,weight_create_time) " +
            "value (#{weightData},#{weightUserId},#{weightCreateTime}) ")
    int addUserWeightData(Weight weight);

    @Update("update t_weight set weight_data=#{weight} where weight_id= #{weightId}")
    int updateWeightData(Double weight, long weightId);

    @Update("update t_user set user_height=#{height} where user_id= #{userId}")
    int updateUserHeight(Double height, long userId);

    @Update("update t_user set user_weight=#{weight} where user_id= #{userId}")
    int updateUserWeight(Double weight, long userId);

    @Update("update t_user set user_nickname=#{nickname} where user_id= #{userId}")
    int updateUserNickname(String nickname, long userId);


}