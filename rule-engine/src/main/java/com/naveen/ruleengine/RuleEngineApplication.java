package com.naveen.ruleengine;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.naveen.ruleengine.entity.Rule;
import com.naveen.ruleengine.repository.RuleRepository;

@SpringBootApplication
public class RuleEngineApplication {
    public static void main(String[] args) {
        SpringApplication.run(RuleEngineApplication.class, args);
    }

    @Bean
    public CommandLineRunner initRules(RuleRepository ruleRepository) {
        return args -> {
            if (ruleRepository.count() == 0) {
                // Email rules with same name but different priorities
                Rule emailRule1 = new Rule();
                emailRule1.setName("Email_Process_Check");
                emailRule1.setExpression("#root['email'] != null && #root['email']['subject'] != null && #root['email']['subject'].contains('Order')");
                emailRule1.setDescription("Priority 1: email.subject contains 'Order'");
                emailRule1.setObjectType("Email");
                emailRule1.setExecutionOrder(1);
                emailRule1.setOutputJson("{\"processName\": \"EmailProcessOrder\", \"tenant\": \"TenantA\"}");
                ruleRepository.save(emailRule1);

                Rule emailRule2 = new Rule();
                emailRule2.setName("Email_Process_Check");
                emailRule2.setExpression("#root['email'] != null && #root['email']['subject'] != null && #root['email']['subject'].contains('Support')");
                emailRule2.setDescription("Priority 2: email.subject contains 'Support'");
                emailRule2.setObjectType("Email");
                emailRule2.setExecutionOrder(2);
                emailRule2.setOutputJson("{\"processName\": \"EmailProcessSupport\", \"tenant\": \"TenantA\"}");
                ruleRepository.save(emailRule2);

                Rule emailRule3 = new Rule();
                emailRule3.setName("Email_Process_Check");
                emailRule3.setExpression("#root['source'] != null && #root['source']['from'] == 'vip@example.com'");
                emailRule3.setDescription("Priority 3: source.from is vip@example.com");
                emailRule3.setObjectType("Email");
                emailRule3.setExecutionOrder(3);
                emailRule3.setOutputJson("{\"processName\": \"EmailProcessVIP\", \"tenant\": \"TenantA\"}");
                ruleRepository.save(emailRule3);

                // Order rules
                Rule orderRule1 = new Rule();
                orderRule1.setName("Order_Amount_Check");
                orderRule1.setExpression("#root['amount'] > 1000");
                orderRule1.setDescription("Order amount > 1000");
                orderRule1.setObjectType("Order");
                orderRule1.setExecutionOrder(1);
                orderRule1.setOutputJson("{\"processName\": \"OrderProcessHigh\", \"tenant\": \"TenantB\"}");
                ruleRepository.save(orderRule1);

                Rule orderRule2 = new Rule();
                orderRule2.setName("Order_Amount_Check");
                orderRule2.setExpression("#root['amount'] > 500");
                orderRule2.setDescription("Order amount > 500");
                orderRule2.setObjectType("Order");
                orderRule2.setExecutionOrder(2);
                orderRule2.setOutputJson("{\"processName\": \"OrderProcessMedium\", \"tenant\": \"TenantB\"}");
                ruleRepository.save(orderRule2);
            }
        };
    }
}
