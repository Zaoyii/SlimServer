package com.zcyi.slimserver.Util.Mail;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MailVerify {
    String email;
    String code;
    String updateTime;
    int verifyType;

    public MailVerify(String email, String code, int verifyType) {
        this.email = email;
        this.code = code;
        this.verifyType = verifyType;
    }

    @Override
    public String toString() {
        return "MailVerify{" +
                "email='" + email + '\'' +
                ", code='" + code + '\'' +
                ", verifyType=" + verifyType +
                '}';
    }
}
