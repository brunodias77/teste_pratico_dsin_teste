package com.brunodias.dsin.controllers;

import com.brunodias.dsin.communications.appointments.RequestCreateAppointment;
import com.brunodias.dsin.dtos.BaseResponseDTO;
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
    @Operation(summary = "Agendamento", description = "Rota para criar um novo agendamento. O usuário deve estar logado e o agendamento deve ocorrer no prazo mínimo de 2 dias.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Agendamento bem sucedido", content = {
                    @Content(schema = @Schema(implementation = BaseResponseDTO.class))
            }),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas"),
            @ApiResponse(responseCode = "400", description = "Erro de validação do agendamento"),
            @ApiResponse(responseCode = "404", description = "Serviço não encontrado")
    })
    public ResponseEntity<BaseResponseDTO> createAppointment(@RequestBody @Valid RequestCreateAppointment request) {
        try {
            var result = this._createAppointmentUseCase.execute(request);
            return ResponseEntity.status(result.getStatus()).body(result);
        } catch (Exception err) {
            return ResponseEntity.status(400).body(BaseResponseDTO.builder()
                    .status(400)
                    .message(err.getMessage())
                    .build());
        }
    }
}
