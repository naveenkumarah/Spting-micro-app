package com.naveen.spring.workflow;

import org.flowable.idm.api.IdmIdentityService;
import org.flowable.idm.api.Privilege;
import org.flowable.idm.api.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WorkflowApplication {
    public static void main(String[] args) {
        SpringApplication.run(WorkflowApplication.class);
    }

    /*
    implements CommandLineRunner
    protected final IdmIdentityService idmIdentityService;

    public WorkflowApplication(IdmIdentityService idmIdentityService) {
        this.idmIdentityService = idmIdentityService;
    }
    @Override
    public void run(String... args) throws Exception {
        createUserIfNotExists("flowfest");
        createUserIfNotExists("flowfest-actuator");
        createUserIfNotExists("flowfest-rest");

        if (idmIdentityService.createPrivilegeQuery().privilegeName("ROLE_REST").count() == 0) {
            Privilege restPrivilege = idmIdentityService.createPrivilege("ROLE_REST");
            idmIdentityService.addUserPrivilegeMapping(restPrivilege.getId(), "flowfest-rest");
        }

        if (idmIdentityService.createPrivilegeQuery().privilegeName("ROLE_ACTUATOR").count() == 0) {
            Privilege restPrivilege = idmIdentityService.createPrivilege("ROLE_ACTUATOR");
            idmIdentityService.addUserPrivilegeMapping(restPrivilege.getId(), "flowfest-actuator");
        }
    }
    protected void createUserIfNotExists(String username) {
        if (idmIdentityService.createUserQuery().userId(username).count() == 0) {
            User user = idmIdentityService.newUser(username);
            user.setPassword("test");
            idmIdentityService.saveUser(user);
        }
    }*/
}
