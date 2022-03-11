package com.kabunx.data.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "kabunx.mybatis")
public class MybatisProperties {
    String timestamp = "datetime";
    String createdColumn = "createdAt";
    String updatedColumn = "updateAt";
}
