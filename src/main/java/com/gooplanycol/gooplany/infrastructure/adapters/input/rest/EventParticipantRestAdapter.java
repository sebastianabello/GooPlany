package com.gooplanycol.gooplany.infrastructure.adapters.input.rest;

import com.gooplanycol.gooplany.application.ports.input.EventParticipantInputPort;
import com.gooplanycol.gooplany.domain.exception.EventParticipantException;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.mapper.EventParticipantRestMapper;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request.EventParticipantRequest;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response.CreditCardResponse;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response.CustomerResponse;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response.EventParticipantResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/eventParticipant")
public class EventParticipantRestAdapter {

    private final EventParticipantInputPort eventParticipantInputPort;
    private final EventParticipantRestMapper eventParticipantRestMapper;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody EventParticipantRequest eventParticipantRequest) {
        try {
            EventParticipantResponse eventParticipantResponse = eventParticipantRestMapper.toEventParticipantResponse(eventParticipantInputPort.save(eventParticipantRestMapper.toEventParticipantRequest(eventParticipantRequest)));
            return new ResponseEntity<>(eventParticipantResponse, HttpStatus.OK);
        } catch (EventParticipantException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<?> edit(@RequestBody EventParticipantRequest paymentRequestDTO, @PathVariable Long id) {
        try {
            EventParticipantResponse paymentResponseDTO = eventParticipantRestMapper.toEventParticipantResponse(eventParticipantInputPort.edit(eventParticipantRestMapper.toEventParticipantRequest(paymentRequestDTO), id));
            return new ResponseEntity<>(paymentResponseDTO, HttpStatus.OK);
        } catch (EventParticipantException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/change/status/{status}/{id}")
    public ResponseEntity<?> changeStatus(@PathVariable String status, @PathVariable long id) {
        try {
            EventParticipantResponse paymentResponseDTO = eventParticipantRestMapper.toEventParticipantResponse(eventParticipantInputPort.changeStatus(status, id));
            return new ResponseEntity<>(paymentResponseDTO, HttpStatus.OK);
        } catch (EventParticipantException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/id/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            EventParticipantResponse paymentResponseDTO = eventParticipantRestMapper.toEventParticipantResponse(eventParticipantInputPort.findById(id));
            return new ResponseEntity<>(paymentResponseDTO, HttpStatus.FOUND);
        } catch (EventParticipantException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find")
    public ResponseEntity<?> findAllByDefault() {
        try {
            List<EventParticipantResponse> list = eventParticipantRestMapper.toEventParticipantResponseList(eventParticipantInputPort.findAll(0, 10));
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (EventParticipantException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/{offset}/{pageSize}")
    public ResponseEntity<?> findAll(@PathVariable Integer offset, @PathVariable Integer pageSize) {
        try {
            List<EventParticipantResponse> list = eventParticipantRestMapper.toEventParticipantResponseList(eventParticipantInputPort.findAll(offset, pageSize));
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (EventParticipantException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<?> remove(@PathVariable Long id) {
        try {
            eventParticipantInputPort.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EventParticipantException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/status/{status}/{offset}/{pageSize}")
    public ResponseEntity<?> findEventParticipantByStatus(@PathVariable Integer offset, @PathVariable Integer pageSize, @PathVariable String status) {
        try {
            List<EventParticipantResponse> list = eventParticipantRestMapper.toEventParticipantResponseList(eventParticipantInputPort.findEventParticipantsByStatus(offset, pageSize, status));
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (EventParticipantException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/status/{status}")
    public ResponseEntity<?> findEventParticipantByStatusDefault(@PathVariable String status) {
        try {
            List<EventParticipantResponse> list = eventParticipantRestMapper.toEventParticipantResponseList(eventParticipantInputPort.findEventParticipantsByStatus(0, 10, status));
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (EventParticipantException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/{id}/customer")
    public ResponseEntity<?> findEventParticipantCustomer(@PathVariable Long id) {
        try {
            CustomerResponse customerResponseDTO = eventParticipantRestMapper.toCustomerResponse(eventParticipantInputPort.findCustomerEventParticipant(id));
            return new ResponseEntity<>(customerResponseDTO, HttpStatus.FOUND);
        } catch (EventParticipantException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/{id}/card")
    public ResponseEntity<?> findPaymentCard(@PathVariable Long id) {
        try {
            CreditCardResponse creditCardResponseDTO = eventParticipantRestMapper.toCreditCardResponse(eventParticipantInputPort.findCardEventParticipant(id));
            return new ResponseEntity<>(creditCardResponseDTO, HttpStatus.FOUND);
        } catch (EventParticipantException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
