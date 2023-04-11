package com.bizu.querycenter.controller;

import com.bizu.querycenter.dto.Request.SaveRequestRequest;
import com.bizu.querycenter.dto.Response.RequestResponse;
import com.bizu.querycenter.model.Request;
import com.bizu.querycenter.service.RequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/request")
public class RequestController {

    private final RequestService requestService;

    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Request> getRequestById(@PathVariable Integer id){
        return ResponseEntity.ok(requestService.getRequestById(id));
    }

    @GetMapping
    public ResponseEntity<List<Request>> getAllRequests(){
        return ResponseEntity.ok(requestService.getAllRequests());
    }

    @GetMapping("/searchRequest")
    public ResponseEntity<Request> getRequestByIdParam(@RequestParam String id){
        Integer requestId = Integer.parseInt(id);
        return ResponseEntity.ok(requestService.getRequestById(requestId));
    }

    @PostMapping
    public ResponseEntity<RequestResponse> saveRequest(@RequestBody SaveRequestRequest request){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(requestService.saveRequest(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RequestResponse> updateRequest(@PathVariable Integer id, @RequestBody SaveRequestRequest request){
        return ResponseEntity.ok(requestService.updateRequest(id, request));
    }

    @GetMapping("/reject/{id}")
    public ResponseEntity<RequestResponse> rejectRequest(@PathVariable Integer id){
        return ResponseEntity.ok(requestService.rejectRequest(id));
    }

    @GetMapping("/done/{id}")
    public ResponseEntity<RequestResponse> doneRequest(@PathVariable Integer id){
        return ResponseEntity.ok(requestService.doneRequest(id));
    }

    @DeleteMapping("/{id}")
    public void deleteRequestById(@PathVariable Integer id){
        requestService.deleteRequestById(id);
    }
}
