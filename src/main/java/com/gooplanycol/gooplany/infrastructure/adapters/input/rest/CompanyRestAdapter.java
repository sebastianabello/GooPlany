package com.gooplanycol.gooplany.infrastructure.adapters.input.rest;

import com.gooplanycol.gooplany.application.ports.input.CompanyInputPort;
import com.gooplanycol.gooplany.application.service.CompanyService;
import com.gooplanycol.gooplany.domain.model.Company;
import com.gooplanycol.gooplany.domain.model.EventPost;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.mapper.CompanyRestMapper;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request.CompanyCreateRequest;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response.CompanyResponse;
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
    private final CompanyService companyService;
    private final CompanyRestMapper companyRestMapper;

    @GetMapping("/v1/api")
    public List<CompanyResponse> findAll() {
        return companyRestMapper.toCompanyResponseList(companyInputPort.findAll());
    }

    @GetMapping("/v1/api/{id}")
    public CompanyResponse findById(@PathVariable Long id) {
        return companyRestMapper.toCompanyResponse(companyInputPort.findById(id));
    }

    @PostMapping("/v1/api")
    public ResponseEntity<CompanyResponse> save(@Valid @RequestBody CompanyCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(companyRestMapper.toCompanyResponse(companyInputPort.save(companyRestMapper.toCompany(request))));
    }

    @PutMapping("/v1/api/{id}")
    public CompanyResponse update(@PathVariable Long id, @Valid @RequestBody CompanyCreateRequest request) {
        return companyRestMapper.toCompanyResponse(companyInputPort.update(id, companyRestMapper.toCompany(request)));
    }

    @DeleteMapping("/v1/api/{id}")
    public void delete(@PathVariable Long id) {
        companyInputPort.deleteById(id);
    }

    @PostMapping("/v1/api/{companyId}/events")
    public ResponseEntity<EventPost> createEventForCompany(@PathVariable Long companyId, @RequestBody EventPost eventPost) {
        Company company = companyService.findById(companyId);
        EventPost createdEvent = companyService.createEventPostForCompany(company, eventPost);
        return ResponseEntity.ok(createdEvent);
    }

    @GetMapping("/v1/api/{companyId}/events")
    public ResponseEntity<List<EventPost>> getEventsForCompany(@PathVariable Long companyId) {
        Company company = companyService.findById(companyId);
        List<EventPost> companyEvents = companyService.getEventsForCompany(company);
        return ResponseEntity.ok(companyEvents);
    }

}
