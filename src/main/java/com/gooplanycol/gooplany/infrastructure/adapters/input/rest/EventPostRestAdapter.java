package com.gooplanycol.gooplany.infrastructure.adapters.input.rest;

import com.gooplanycol.gooplany.application.ports.input.EventPostInputPort;
import com.gooplanycol.gooplany.domain.exception.EventPostException;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.mapper.EventPostRestMapper;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request.EventPostRequest;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response.EventPostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/eventPost")
public class EventPostRestAdapter {

    private final EventPostInputPort eventPostInputPort;
    private final EventPostRestMapper eventPostRestMapper;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody EventPostRequest productSoldRequestDTO) {
        try {
            EventPostResponse productSoldResponseDTO = eventPostRestMapper.toEventPostResponse(eventPostInputPort.save(eventPostRestMapper.toEventPostRequest(productSoldRequestDTO)));
            return new ResponseEntity<>(productSoldResponseDTO, HttpStatus.CREATED);
        } catch (EventPostException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{id}/edit")
    public ResponseEntity<?> edit(@RequestBody EventPostRequest productSoldRequestDTO, @PathVariable("id") Long id) {
        try {
            EventPostResponse productSoldResponseDTO = eventPostRestMapper.toEventPostResponse(eventPostInputPort.edit(eventPostRestMapper.toEventPostRequest(productSoldRequestDTO), id));
            return new ResponseEntity<>(productSoldResponseDTO, HttpStatus.OK);
        } catch (EventPostException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}/remove")
    public ResponseEntity<?> remove(@PathVariable("id") Long id) {
        try {
            eventPostInputPort.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EventPostException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        try {
            EventPostResponse productSoldResponseDTO = eventPostRestMapper.toEventPostResponse(eventPostInputPort.findById(id));
            return new ResponseEntity<>(productSoldResponseDTO, HttpStatus.FOUND);
        } catch (EventPostException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/{offset}/{pageSize}")
    public ResponseEntity<?> findAll(@PathVariable Integer offset, @PathVariable Integer pageSize) {
        try {
            List<EventPostResponse> list = eventPostRestMapper.toEventPostResponseList(eventPostInputPort.findAll(offset, pageSize));
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (EventPostException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find")
    public ResponseEntity<?> findAllByDefault() {
        try {
            List<EventPostResponse> list = eventPostRestMapper.toEventPostResponseList(eventPostInputPort.findAll(0, 10));
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (EventPostException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/title/{title}/{offset}/{pageSize}")
    public ResponseEntity<?> findEventsPostByTitle(@PathVariable("title") String title, @PathVariable Integer offset, @PathVariable Integer pageSize) {
        try {
            List<EventPostResponse> list = eventPostRestMapper.toEventPostResponseList(eventPostInputPort.findEventPostByTitle(title, offset, pageSize));
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (EventPostException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/barCode/{barcode}")
    public ResponseEntity<?> findEventsPostByTitleByDefault(@PathVariable("barcode")String barCode){
        try {
            List<EventPostResponse> list = eventPostRestMapper.toEventPostResponseList(eventPostInputPort.findEventPostByTitle(barCode,0,10));
            return new ResponseEntity<>(list,HttpStatus.FOUND);
        }catch (EventPostException ex){
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

}
