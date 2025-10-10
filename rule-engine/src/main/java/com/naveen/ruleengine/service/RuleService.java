package com.naveen.ruleengine.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.naveen.ruleengine.entity.Rule;
import com.naveen.ruleengine.repository.RuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RuleService {
    @Autowired
    private RuleRepository ruleRepository;

    private final ExpressionParser parser = new SpelExpressionParser();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<Rule> getAllRules() {
        return ruleRepository.findAll();
    }

    public Optional<Rule> getRuleById(Long id) {
        return ruleRepository.findById(id);
    }

    public Rule saveRule(Rule rule) {
        return ruleRepository.save(rule);
    }

    public void deleteRule(Long id) {
        ruleRepository.deleteById(id);
    }

    public Object evaluateRule(String ruleName, Object businessObject) {
        List<Rule> rules = ruleRepository.findAllByNameOrderByExecutionOrderAsc(ruleName);
        if (rules == null || rules.isEmpty()) throw new RuntimeException("No rules found for name: " + ruleName);
        StandardEvaluationContext context = new StandardEvaluationContext(businessObject);
        for (Rule rule : rules) {
            Object result = parser.parseExpression(rule.getExpression()).getValue(context);
            if (result instanceof Boolean && (Boolean) result) {
                try {
                    Map<String, Object> response;
                    if (rule.getOutputJson() != null) {
                        response = objectMapper.readValue(rule.getOutputJson(), Map.class);
                    } else {
                        response = new java.util.HashMap<>();
                    }
                    response.put("status", true);
                    return response;
                } catch (Exception e) {
                    throw new RuntimeException("Invalid outputJson: " + e.getMessage());
                }
            }
        }
        return Map.of("status", false);
    }
}
