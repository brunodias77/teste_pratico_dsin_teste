package com.brunodias.dsin.controllers;

import com.brunodias.dsin.communications.appointments.RequestCreateAppointment;
import com.brunodias.dsin.entities.Appointment;
import com.brunodias.dsin.useCases.appointments.createAppointment.CreateAppointmentUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/appointment")
@RequiredArgsConstructor
@Tag(name = "Agendamentos", description = "Controller de agendamentos")
public class AppointmentController {

    private final CreateAppointmentUseCase _createAppointmentUseCase;

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Agendamento", description = "rota de criar um novo agendamento, nele o usuario deve estar logado e o agendamento deve acontencer no prazo mamino de 2 dias")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Agendamento bem sucedido", content = {
                    @Content(schema = @Schema(implementation = Appointment.class))
            }),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    })
    public ResponseEntity<?> createAppointment(@RequestBody @Valid RequestCreateAppointment request) {
        try {
            var result = this._createAppointmentUseCase.execute(request);
            return ResponseEntity.ok().body(result);
        } catch (Exception err) {
            return ResponseEntity.status(401).body(err.getMessage());
        }
    }

}
