package com.kabunx.core.entity;

import com.kabunx.core.json.annotation.SplitProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class AuthEntity {
    private Long id;

    @SplitProperty
    private List<String> names;

    @SplitProperty(type = "int")
    private List<Integer> ids;
}
