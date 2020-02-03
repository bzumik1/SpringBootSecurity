package com.znamenacek.jakub.springBootSecurity.security;

import com.google.common.collect.Sets;
import static com.znamenacek.jakub.springBootSecurity.security.ApplicationUserPermission.*;

import java.util.Set;

public enum ApplicationUserRole {
    ADMIN(Sets.newHashSet(STUDENT_READ,STUDENT_WRITE,COURSE_READ,COURSE_WRITE)),
    ADMINTRAINEE(Sets.newHashSet(STUDENT_READ,COURSE_READ)),
    STUDENT(Sets.newHashSet());

    private final Set<ApplicationUserPermission> permissions;

    private ApplicationUserRole (Set<ApplicationUserPermission> permissions){ //private constructor because only one object of enum can be created
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermission> getPermission(){
        return permissions;
    }
}
