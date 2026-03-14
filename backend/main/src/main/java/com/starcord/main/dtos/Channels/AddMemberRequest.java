package com.starcord.main.dtos.Channels;

import java.util.Set;

public class AddMemberRequest {
    private Set<Long> members;

    public Set<Long> getMembers() { return members; }
    public void setMembers(Set<Long> members) { this.members = members; }
    public void addMember(Long member) { this.members.add(member); }
}
