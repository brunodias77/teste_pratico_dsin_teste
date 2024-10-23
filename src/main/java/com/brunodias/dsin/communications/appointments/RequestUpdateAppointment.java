package com.brunodias.dsin.communications.appointments;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestUpdateAppointment {

    @NotNull(message = "A data e hora do agendamento são obrigatórias.")
    @Future(message = "A data e hora do agendamento devem ser no futuro.")
    private LocalDateTime appointmentDateTime;

    @NotNull(message = "A lista de serviços não pode estar vazia.")
    @NotEmpty(message = "É necessário selecionar ao menos um serviço.")
    private UUID serviceId;
}
