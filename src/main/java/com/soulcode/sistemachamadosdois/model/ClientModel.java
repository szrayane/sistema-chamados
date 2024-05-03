package com.soulcode.sistemachamadosdois.model;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
@DiscriminatorValue("client")
public class ClientModel extends UserModel {

    private String endereco;
}