package com.gooplanycol.gooplany.infrastructure.adapters.input.rest;

import com.gooplanycol.gooplany.application.ports.input.CompanyInputPort;
import com.gooplanycol.gooplany.application.ports.input.EventPostInputPort;
import com.gooplanycol.gooplany.domain.model.Company;
import com.gooplanycol.gooplany.domain.model.EventPost;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.mapper.CompanyRestMapper;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.mapper.EventPostRestMapper;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request.CompanyRequest;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request.EventPostRequest;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response.CompanyResponse;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response.EventPostResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/companies")
public class CompanyRestAdapter {

    private final CompanyInputPort companyInputPort;
    private final CompanyRestMapper companyRestMapper;

    private final EventPostInputPort eventPostInputPort;
    private final EventPostRestMapper eventPostRestMapper;


    @GetMapping("/v1/api")
    public List<CompanyResponse> findAll() {
        return companyRestMapper.toCompanyResponseList(companyInputPort.findAll());
    }

    @GetMapping("/v1/api/{id}")
    public CompanyResponse findById(@PathVariable Long id) {
        return companyRestMapper.toCompanyResponse(companyInputPort.findById(id));
    }

    @PostMapping("/v1/api")
    public ResponseEntity<CompanyResponse> save(@Valid @RequestBody CompanyRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(companyRestMapper.toCompanyResponse(companyInputPort.save(companyRestMapper.toCompany(request))));
    }

    @PutMapping("/v1/api/{id}")
    public CompanyResponse update(@PathVariable Long id, @Valid @RequestBody CompanyRequest request) {
        return companyRestMapper.toCompanyResponse(companyInputPort.update(id, companyRestMapper.toCompany(request)));
    }

    @DeleteMapping("/v1/api/{id}")
    public void delete(@PathVariable Long id) {
        companyInputPort.deleteById(id);
    }

    @PostMapping("/v1/api/{companyId}/events")
    public ResponseEntity<EventPostResponse> createEventForCompany(@PathVariable Long companyId, @RequestBody EventPostRequest request) {
        Company company = companyInputPort.findById(companyId);
        EventPost eventPost = eventPostRestMapper.toEventPost(request);
        eventPost.setCompany(company);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(eventPostRestMapper.toEventPostResponse(eventPostInputPort.save(eventPost)));
    }

    @GetMapping("/v1/api/{companyId}/events")
    public ResponseEntity<List<EventPostResponse>> getEventsForCompany(@PathVariable Long companyId) {
        return null;
    }

}
