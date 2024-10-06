package com.zcyi.slimserver.Util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.zcyi.slimserver.TaskPart.Task;
import com.zcyi.slimserver.TaskPart.TaskAssignment;
import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.*;

public class UtilMethod {
    //SING签名的设置
    public static final String SING = "zrercndfd";

    public static String getToken(Map<String, String> map) {
        //设置令牌的过期时间
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE, 15);

        //创建JWT builder
        JWTCreator.Builder builder = JWT.create();

        //payload
        map.forEach(builder::withClaim);
        return builder.withExpiresAt(instance.getTime()).sign(Algorithm.HMAC256(SING));
    }

    public static DecodedJWT verify(String token) {
        return JWT.require(Algorithm.HMAC256(SING)).build().verify(token);
    }

    public static DecodedJWT getTokenInfo(String token) {
        return JWT.require(Algorithm.HMAC256(SING)).build().verify(token);
    }

    /**
     * @Description 生成盐和加盐后的MD5码，并将盐混入到MD5码中,对MD5密码进行加强
     **/
    public static String generateSaltPassword(String password) {
        //将盐加到明文中，并生成新的MD5码
        password = md5Hex(password + SING);
        return password;
    }

    /**
     * @Description 生成MD5密码
     **/
    private static String md5Hex(String src) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bs = md5.digest(src.getBytes());
            return new String(new Hex().encode(bs));
        } catch (Exception e) {
            return null;
        }
    }

    public static Map<String, String> generateSevenDay() {
        Map<String, String> result = new HashMap();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0); // 设置小时为0
        calendar.set(Calendar.MINUTE, 0);      // 设置分钟为0
        calendar.set(Calendar.SECOND, 0);      // 设置秒钟为0
        calendar.set(Calendar.MILLISECOND, 0); // 设置毫秒为0
        Date currentDate = calendar.getTime();

        // 设置开始日期为当前日期
        Date startDate = currentDate;

        // 将日期增加一周得到结束日期
        calendar.add(Calendar.WEEK_OF_YEAR, 1);
        Date endDate = calendar.getTime();

        // 定义日期格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 格式化日期并打印开始日期和结束日期
        String formattedStartDate = dateFormat.format(startDate);
        String formattedEndDate = dateFormat.format(endDate);
        result.put("startTime", formattedStartDate);
        result.put("endTime", formattedEndDate);
        return result;
    }

    public static ArrayList<TaskAssignment> setTaskName(ArrayList<TaskAssignment> taskAssignments, ArrayList<Task> tasks) {
        if (tasks == null || tasks.size() == 0) {
            return null;
        }
        if (taskAssignments == null || taskAssignments.size() == 0) {
            return null;
        }
        for (int i = 0; i < tasks.size(); i++) {
            for (int j = 0; j < taskAssignments.size(); j++) {
                if (tasks.get(i).getTaskId() == taskAssignments.get(j).getTaskId()) {
                    taskAssignments.get(j).setTaskName(tasks.get(i).getTaskName());
                    taskAssignments.get(j).setTaskExample(tasks.get(i).getTaskExample());
                    taskAssignments.get(j).setTaskTime(tasks.get(i).getTaskTime());
                }
            }
        }
        return taskAssignments;
    }
}