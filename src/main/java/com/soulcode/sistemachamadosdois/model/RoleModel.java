package com.soulcode.sistemachamadosdois.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="TB_ROLES")
public class RoleModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;
    private String name;

    public enum Values {
        TECHNICIAN(1L),
        CLIENT(2L);

        long roleId;

        Values(long roleId) {
            this.roleId = roleId;
        }
    }

}
