package com.kabunx.core.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "kabunx.global")
public class GlobalProperties {

}
