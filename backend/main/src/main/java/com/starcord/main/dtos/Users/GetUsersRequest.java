package com.starcord.main.dtos.Users;

import java.util.Set;

public class GetUsersRequest {
    private Set<Long> users;

    public Set<Long> getUsers() { return users; }
    public void setUsers(Set<Long> users) { this.users = users; }
}
