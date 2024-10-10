package com.zcyi.slimserver.UserPart;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author ZaoYi
 */
@Data
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class User {
    private long userId;
    private String userName;
    private String userNickName;
    private int userGender;
    private int userGoal;
    private int userAge;
    private String userPassword;
    private Double userHeight;
    private Double userWeight;
    private String userAvatar;
    private String userEmail;
    private String userCreateTime;
    private String userUpdateTime;
    private String userToken;

 public User(String userName, String userPassword, String userEmail, String userNickName) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.userEmail = userEmail;
        this.userNickName = userNickName;
    }
}
