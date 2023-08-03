package com.wsh.domain.vo;

import com.wsh.domain.entity.Role;
import com.wsh.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UserRoleVo {
    private List<Long> roleIds;
    private List<Role> roles;
    private User user;
}
