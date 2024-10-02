package com.jbj.jbjapi.domain;

import lombok.Builder;
import lombok.Data;

/**
 * @Author mhw
 * @Version 1.0
 */
@Data
@Builder
public class SysLocalStorage {

    private String url;
    private String fileName;
}
