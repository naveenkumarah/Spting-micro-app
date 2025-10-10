package com.naveen.ruleengine.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "rules")
@Getter
@Setter
public class Rule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) // removed unique = true
    private String name;

    @Column(nullable = false)
    private String expression;

    private String description;

    private String objectType; // e.g., "Email", "Order", etc.
    private Integer executionOrder;

    @Column(columnDefinition = "TEXT")
    private String outputJson; // JSON string with business values to return on match

    public String getOutputJson() { return outputJson; }
    public void setOutputJson(String outputJson) { this.outputJson = outputJson; }
}
