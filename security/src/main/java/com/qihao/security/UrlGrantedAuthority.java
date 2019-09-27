package com.qihao.security;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author huangqihao
 * @version 1.0
 * @date 2019/9/26 17:28
 */
@Data
public class UrlGrantedAuthority implements GrantedAuthority {

    private String url;

    public UrlGrantedAuthority(String url) {
        this.url = url;
    }

    @Override
    public String getAuthority() {
        return this.url;
    }
}
