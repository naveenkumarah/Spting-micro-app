package com.naveen.authapi.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "roles")
@Data
@Setter
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Role {
    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    public enum roles{
    ADMIN,USER
    }

    @Enumerated(EnumType.STRING)
    private roles name;
    public Role(roles name){
        this.name=name;
    }
}
