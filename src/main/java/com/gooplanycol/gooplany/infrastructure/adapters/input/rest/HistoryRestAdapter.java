package com.gooplanycol.gooplany.infrastructure.adapters.input.rest;

import com.gooplanycol.gooplany.application.ports.input.HistoryInputPort;
import com.gooplanycol.gooplany.domain.exception.HistoryException;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.mapper.HistoryRestMapper;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request.HistoryRequest;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response.EventFinishedResponse;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response.HistoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/history")
public class HistoryRestAdapter {

    private final HistoryInputPort historyInputPort;
    private final HistoryRestMapper historyRestMapper;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody HistoryRequest historyRequestDTO) {
        try {
            HistoryResponse historyResponseDTO = historyRestMapper.toHistoryResponse(historyInputPort.save(historyRestMapper.toHistoryRequest(historyRequestDTO)));
            return new ResponseEntity<>(historyResponseDTO, HttpStatus.CREATED);
        } catch (HistoryException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}/remove")
    public ResponseEntity<?> remove(@PathVariable("id") Long id) {
        try {
            historyInputPort.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (HistoryException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        try {
            HistoryResponse historyResponseDTO = historyRestMapper.toHistoryResponse(historyInputPort.findById(id));
            return new ResponseEntity<>(historyResponseDTO, HttpStatus.FOUND);
        } catch (HistoryException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/find/{offset}/{pageSize}")
    public ResponseEntity<?> findAll(@PathVariable Integer offset, @PathVariable Integer pageSize) {
        try {
            List<HistoryResponse> list = historyRestMapper.toHistoryResponseList(historyInputPort.findAll(offset, pageSize));
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (HistoryException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find")
    public ResponseEntity<?> findAllByDefault() {
        try {
            List<HistoryResponse> list = historyRestMapper.toHistoryResponseList(historyInputPort.findAll(0, 10));
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (HistoryException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/find/eventFinished/{offset}/{pageSize}")
    public ResponseEntity<?> findEventFinished(@PathVariable("id") Long id, @PathVariable Integer offset, @PathVariable Integer pageSize) {
        try {
            List<EventFinishedResponse> list = historyRestMapper.toEventFinishedResponseList(historyInputPort.findEventFinished(id, offset, pageSize));
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (HistoryException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/find/eventFinished")
    public ResponseEntity<?> findEventFinishedByDefault(@PathVariable("id") Long id) {
        try {
            List<EventFinishedResponse> list = historyRestMapper.toEventFinishedResponseList(historyInputPort.findEventFinished(id, 0, 10));
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (HistoryException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{id}/add/eventFinished")
    public ResponseEntity<?> addEventFinished(@RequestBody EventFinishedResponse eventFinished, @PathVariable("id") Long id) {
        try {
            List<EventFinishedResponse> list = historyRestMapper.toEventFinishedResponseList(historyInputPort.addEventFinished(historyRestMapper.toEventFinishedRequest(eventFinished), id));
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (HistoryException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{historyId}/remove/sale/{eventFinishedId}")
    public ResponseEntity<?> removeSale(@PathVariable("eventFinishedId") Long eventFinishedId, @PathVariable("historyId") Long historyId) {
        try {
            historyInputPort.removeEventFinished(eventFinishedId, historyId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (HistoryException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
