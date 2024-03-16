package dev.marvin.savings.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
public class Role implements Serializable {
    private Integer roleId;
    private String roleName;
}
