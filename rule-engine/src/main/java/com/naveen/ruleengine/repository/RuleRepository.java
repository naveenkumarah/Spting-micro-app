package com.naveen.ruleengine.repository;

import com.naveen.ruleengine.entity.Rule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RuleRepository extends JpaRepository<Rule, Long> {
    Rule findByName(String name);

    List<Rule> findAllByNameOrderByExecutionOrderAsc(String name);
}
