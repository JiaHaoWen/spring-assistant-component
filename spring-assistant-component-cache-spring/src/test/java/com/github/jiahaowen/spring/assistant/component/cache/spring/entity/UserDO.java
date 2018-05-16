package com.github.jiahaowen.spring.assistant.component.cache.spring.entity;

import java.io.Serializable;

public class UserDO implements Serializable {

    private static final long serialVersionUID = 1932703849895844645L;

    private Long id;

    private String name;

    private String password;

    private Integer status;

    public static final Builder builder() {

        Builder builder = new Builder();

        return builder;
    }

    public static class Builder {

        private final UserDO userDO = new UserDO();

        private Builder() {}

        public Builder id(Long id) {
            userDO.setId(id);
            return this;
        }

        public Builder name(String name) {
            userDO.setName(name);
            return this;
        }

        public Builder password(String password) {
            userDO.setPassword(password);
            return this;
        }

        public Builder status(Integer status) {
            userDO.setStatus(status);
            return this;
        }

        public UserDO build() {
            return this.userDO;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
