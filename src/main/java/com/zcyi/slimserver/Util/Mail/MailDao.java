package com.zcyi.slimserver.Util.Mail;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface MailDao {
    @Insert("insert into t_verify_email (user_email,verify_type,user_code) value (#{email},#{verifyType},#{code}) ON DUPLICATE KEY UPDATE user_code=#{code},verify_type=#{verifyType}")
    void addCode(MailVerify mailVerify);

    @Select("select * from t_verify_email where user_email = #{userEmail} and verify_type=#{type}")
    @Results({@Result(property = "code", column = "user_code"),
            @Result(property = "updateTime", column = "update_time")})
    MailVerify selectCode(String userEmail, int type);



}