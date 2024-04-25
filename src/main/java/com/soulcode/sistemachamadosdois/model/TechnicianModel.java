package com.soulcode.sistemachamadosdois.model;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
@DiscriminatorValue("technician")
public class TechnicianModel extends UserModel {

    private String department;
    private String phone;

}
