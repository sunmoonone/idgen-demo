package me.smn.idgen.model;

import java.util.Date;

public class User {
    private Long id;
    private String nick;
    private Date createAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public static class Builder {
        private User obj;

        public Builder() {
            this.obj = new User();
            this.obj.setCreateAt(new Date());
        }


        public Builder id(Long id) {
            obj.setId(id);
            return this;
        }

        public Builder nick(String nick) {
            obj.setNick(nick);
            return this;
        }

        public User build() {
            return this.obj;
        }

    }

    public static User.Builder builder() {
        return new User.Builder();
    }

}
