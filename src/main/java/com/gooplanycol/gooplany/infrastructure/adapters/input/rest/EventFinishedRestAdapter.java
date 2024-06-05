package com.gooplanycol.gooplany.infrastructure.adapters.input.rest;

import com.gooplanycol.gooplany.application.ports.input.EventFinishedInputPort;
import com.gooplanycol.gooplany.domain.exception.EventFinishedException;
import com.gooplanycol.gooplany.domain.model.request.EventFinishedRequest;
import com.gooplanycol.gooplany.domain.model.request.EventParticipantRequest;
import com.gooplanycol.gooplany.domain.model.response.EventFinishedResponse;
import com.gooplanycol.gooplany.domain.model.response.EventParticipantResponse;
import com.gooplanycol.gooplany.domain.model.response.EventPostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/eventFinished")
public class EventFinishedRestAdapter {

    private final EventFinishedInputPort eventFinishedInputPort;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody EventFinishedRequest eventFinishedRequest) {
        try {
            EventFinishedResponse eventFinishedResponse = eventFinishedInputPort.save(eventFinishedRequest);
            return new ResponseEntity<>(eventFinishedResponse, HttpStatus.CREATED);
        } catch (EventFinishedException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{id}/edit")
    public ResponseEntity<?> edit(@RequestBody EventFinishedRequest saleRequestDTO, @PathVariable("id") Long id) {
        try {
            EventFinishedResponse responseDTO = eventFinishedInputPort.edit(saleRequestDTO, id);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } catch (EventFinishedException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        try {
            EventFinishedResponse responseDTO = eventFinishedInputPort.findById(id);
            return new ResponseEntity<>(responseDTO, HttpStatus.FOUND);
        } catch (EventFinishedException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/{offset}/{pageSize}")
    public ResponseEntity<?> findAll(@PathVariable Integer offset, @PathVariable Integer pageSize) {
        try {
            List<EventFinishedResponse> list = eventFinishedInputPort.findAll(offset, pageSize);
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (EventFinishedException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find")
    public ResponseEntity<?> findAllByDefault() {
        try {
            List<EventFinishedResponse> list = eventFinishedInputPort.findAll(0, 10);
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (EventFinishedException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{id}/add/EventParticipant")
    public ResponseEntity<?> addProduct(@RequestBody EventParticipantRequest eventParticipantRequest, @PathVariable("id") Long id) {
        try {
            EventFinishedResponse eventFinishedResponse = eventFinishedInputPort.addEventParticipant(eventParticipantRequest, id);
            return new ResponseEntity<>(eventFinishedResponse, HttpStatus.OK);
        } catch (EventFinishedException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{eventFinishedId}/remove/eventParticipant/{eventParticipantId}")
    public ResponseEntity<?> removeEventParticipant(@PathVariable("eventFinishedId") Long eventFinishedId, @PathVariable("eventParticipantId") Long eventParticipantId) {
        try {
            EventFinishedResponse eventFinishedResponse = eventFinishedInputPort.removeEventParticipant(eventFinishedId, eventParticipantId);
            return new ResponseEntity<>(eventFinishedResponse, HttpStatus.FOUND);
        } catch (EventFinishedException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}/find/eventParticipants/{offset}/{pageSize}")
    public ResponseEntity<?> findEventParticipant(@PathVariable("id") Long id, @PathVariable Integer offset, @PathVariable Integer pageSize) {
        try {
            List<EventParticipantResponse> list = eventFinishedInputPort.findEventParticipants(id, offset, pageSize);
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (EventFinishedException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/find/eventParticipants")
    public ResponseEntity<?> findEventParticipantsByDefault(@PathVariable("id") Long id) {
        try {
            List<EventParticipantResponse> list = eventFinishedInputPort.findEventParticipants(id, 0, 10);
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (EventFinishedException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/{id}/eventPost")
    public ResponseEntity<?> findEventPost(@PathVariable("id") Long id) {
        try {
            EventPostResponse eventPostResponse = eventFinishedInputPort.findEventPost(id);
            return new ResponseEntity<>(eventPostResponse, HttpStatus.FOUND);
        } catch (EventFinishedException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
