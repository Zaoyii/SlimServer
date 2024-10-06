package com.zcyi.slimserver.UserPart;

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
public class Weight {
    long weightId;
    double weightData;
    long weightUserId;
    String weightCreateTime;
}
