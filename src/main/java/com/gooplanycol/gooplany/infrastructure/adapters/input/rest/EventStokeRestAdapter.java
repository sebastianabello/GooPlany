package com.gooplanycol.gooplany.infrastructure.adapters.input.rest;

import com.gooplanycol.gooplany.application.ports.input.EventStokeInputPort;
import com.gooplanycol.gooplany.domain.exception.EventStokeException;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.mapper.EventStokeRestMapper;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request.EventStokeRequest;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response.EventStokeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/eventStock")
public class EventStokeRestAdapter {

    private final EventStokeInputPort eventStokeInputPort;
    private final EventStokeRestMapper eventStokeRestMapper;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody EventStokeRequest eventStokeRequest) {
        try {
            EventStokeResponse eventStokeResponse = eventStokeRestMapper.toEventStokeResponse(eventStokeInputPort.save(eventStokeRestMapper.toEventStokeRequest(eventStokeRequest)));
            return new ResponseEntity<>(eventStokeResponse, HttpStatus.CREATED);
        } catch (EventStokeException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}/remove")
    public ResponseEntity<?> remove(@PathVariable("id") Long id) {
        try {
            eventStokeInputPort.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EventStokeException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/{id}/edit")
    public ResponseEntity<?> edit(@RequestBody EventStokeRequest eventStokeRequest, @PathVariable("id") Long id) {
        try {
            EventStokeResponse eventStokeResponse = eventStokeRestMapper.toEventStokeResponse(eventStokeInputPort.edit(eventStokeRestMapper.toEventStokeRequest(eventStokeRequest), id));
            return new ResponseEntity<>(eventStokeResponse, HttpStatus.OK);
        } catch (EventStokeException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find")
    public ResponseEntity<?> findAllByDefault() {
        try {
            List<EventStokeResponse> list = eventStokeRestMapper.toEventStokeResponseList(eventStokeInputPort.findAll(0, 10));
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (EventStokeException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/{offset}/{pageSize}")
    public ResponseEntity<?> findAll(@PathVariable Integer offset, @PathVariable Integer pageSize) {
        try {
            List<EventStokeResponse> list = eventStokeRestMapper.toEventStokeResponseList(eventStokeInputPort.findAll(offset, pageSize));
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (EventStokeException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        try {
            EventStokeResponse eventStokeResponse = eventStokeRestMapper.toEventStokeResponse(eventStokeInputPort.findEventPostById(id));
            return new ResponseEntity<>(eventStokeResponse, HttpStatus.FOUND);
        } catch (EventStokeException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/status/{status}")
    public ResponseEntity<?> findEventStockByEventStatusByDefault(@PathVariable String status) {
        try {
            List<EventStokeResponse> list = eventStokeRestMapper.toEventStokeResponseList(eventStokeInputPort.findEventStokesByStatusEventPost(status, 0, 10));
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (EventStokeException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/status/{status}/{offset}/{pageSize}")
    public ResponseEntity<?> findEventsStockByEnableEventPost(@PathVariable String status, @PathVariable Integer offset, @PathVariable Integer pageSize) {
        try {
            List<EventStokeResponse> list = eventStokeRestMapper.toEventStokeResponseList(eventStokeInputPort.findEventStokesByStatusEventPost(status, offset, pageSize));
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (EventStokeException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/title/{title}")
    public ResponseEntity<?> findEventsStockByTitle(@PathVariable("title") String title) {
        try {
            EventStokeResponse eventStokeResponse = eventStokeRestMapper.toEventStokeResponse(eventStokeInputPort.findEventStockByTitle(title));
            return new ResponseEntity<>(eventStokeResponse, HttpStatus.FOUND);
        } catch (EventStokeException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/change/status/{status}/{id}")
    public ResponseEntity<?> changeStatus(@PathVariable String status, @PathVariable long id) {
        try {
            EventStokeResponse eventStokeResponse = eventStokeRestMapper.toEventStokeResponse(eventStokeInputPort.changeStatus(status, id));
            return new ResponseEntity<>(eventStokeResponse, HttpStatus.OK);
        } catch (EventStokeException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
