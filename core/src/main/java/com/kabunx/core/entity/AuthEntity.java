package com.kabunx.core.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AuthEntity {
    private Long id;
    private String name;
}
