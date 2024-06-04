package com.gooplanycol.gooplany.infrastructure.adapters.input.rest;

import com.gooplanycol.gooplany.application.ports.input.CreditCardInputPort;
import com.gooplanycol.gooplany.domain.exception.CreditCardException;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.mapper.CreditCardRestMapper;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request.CreditCardRequest;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response.CreditCardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/card")
public class CreditCardRestAdapter {

    private final CreditCardInputPort creditCardInputPort;
    private final CreditCardRestMapper creditCardRestMapper;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody CreditCardRequest requestDTO){
        try {
            CreditCardResponse responseDTO = creditCardRestMapper.toCreditCardResponse(creditCardInputPort.save(creditCardRestMapper.toCreditCard(requestDTO)));
            return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
        } catch (CreditCardException ex){
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{id}/edit")
    public ResponseEntity<?> edit(@RequestBody CreditCardRequest requestDTO, @PathVariable("id") Long id){
        try{
            CreditCardResponse responseDTO = creditCardRestMapper.toCreditCardResponse(creditCardInputPort.edit(creditCardRestMapper.toCreditCard(requestDTO),id));
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        }catch (CreditCardException ex){
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_MODIFIED);
        }
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id){
        try{
            CreditCardResponse responseDTO = creditCardRestMapper.toCreditCardResponse(creditCardInputPort.findById(id));
            return new ResponseEntity<>(responseDTO, HttpStatus.FOUND);
        }catch (CreditCardException ex){
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/number/{number}")
    public ResponseEntity<?> findByNumber(@PathVariable("number") String number){
        try {
            CreditCardResponse responseDTO = creditCardRestMapper.toCreditCardResponse(creditCardInputPort.findCardByNumber(number));
            return new ResponseEntity<>(responseDTO, HttpStatus.FOUND);
        }catch (CreditCardException ex){
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find")
    public ResponseEntity<?> findAllByDefault(@PathVariable Integer offset, @PathVariable Integer pageSize){
        try {
            List<CreditCardResponse> list = creditCardRestMapper.toCreditCardResponseList(creditCardInputPort.findAll(offset, pageSize));
            return new ResponseEntity<>(list,HttpStatus.FOUND);
        }catch (CreditCardException ex){
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/{offset}/{pageSize}")
    public ResponseEntity<?> findAll(@PathVariable Integer offset, @PathVariable Integer pageSize){
        try {
            List<CreditCardResponse> list = creditCardRestMapper.toCreditCardResponseList(creditCardInputPort.findAll(offset, pageSize));
            return new ResponseEntity<>(list,HttpStatus.FOUND);
        }catch (CreditCardException ex){
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/type/{type}/{offset}/{pageSize}")
    public ResponseEntity<?> findByType(@PathVariable("type") String type,@PathVariable Integer offset,@PathVariable Integer pageSize){
        try{
            List<CreditCardResponse> list = creditCardRestMapper.toCreditCardResponseList(creditCardInputPort.findCardsByType(offset, pageSize, type));
            return new ResponseEntity<>(list,HttpStatus.FOUND);
        }catch (CreditCardException ex){
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/type/{type}")
    public ResponseEntity<?> findByTypeByDefault(@PathVariable("type") String type){
        try{
            List<CreditCardResponse> list = creditCardRestMapper.toCreditCardResponseList(creditCardInputPort.findCardsByType(0, 10, type));
            return new ResponseEntity<>(list,HttpStatus.FOUND);
        }catch (CreditCardException ex){
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}/remove")
    public ResponseEntity<?> remove(@PathVariable("id") Long id){
        try{
            creditCardInputPort.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (CreditCardException ex){
            return  new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
}
