package com.ygz.aspen.context;

import com.ygz.aspen.model.sys.User;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AspenContext {

    private User user;

    private Map<String, Object> context = new ConcurrentHashMap<>();

    public AspenContext(){
    }

    public AspenContext(final User user){
        this.user = user;
    }

    public Object getContext(String key){
        return this.context.get(key);
    }

    public void addContext(String key, Object value){
        this.context.put(key, value);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
