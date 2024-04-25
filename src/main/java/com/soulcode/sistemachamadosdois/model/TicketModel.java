package com.soulcode.sistemachamadosdois.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="TB_TICKETS")
public class TicketModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="ticket_id")
    private Long ticketId;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String department;

    @CreationTimestamp
    private Instant creationTimestamp;

    @UpdateTimestamp
    private Instant updatedTimestamp;

    @ManyToOne
    private ClientModel client;

    @ManyToOne
    private TechnicianModel technician;

    private TicketStatus status;

    private Priority priority;

    public enum Priority {
        AGUARDANDO,
        BAIXA,
        MEDIA,
        ALTA;
    }

    public enum TicketStatus {
        OPEN("Aguardando técnico"),
        IN_PROGRESS("Em atendimento"),
        FORWARDED("Escalado para outro setor"),
        CLOSED("Finalizado");

        private final String description;

        TicketStatus(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

}
