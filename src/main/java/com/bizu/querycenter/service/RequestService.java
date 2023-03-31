package com.bizu.querycenter.service;

import com.bizu.querycenter.dto.Request.SaveRequestRequest;
import com.bizu.querycenter.dto.Response.RequestResponse;
import com.bizu.querycenter.model.Request;
import com.bizu.querycenter.model.Status;
import com.bizu.querycenter.repository.RequestRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RequestService {

    private final RequestRepository requestRepository;

    public RequestService(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    public Request getRequestById(Integer id){
        Request request = requestRepository.findById(id).orElseThrow(RuntimeException::new);

        return request;
    }

    public List<Request> getAllRequests(){
        List<Request> requests = new ArrayList<>();
        requestRepository.findAll().forEach(requests::add);

        return requests;
    }

    public RequestResponse saveRequest(SaveRequestRequest request){

        List<Request> requests = getAllRequests();

        int size = requests.size() + 2;

        Request request1 = Request.builder()
                ._id(size)
                .description(request.getDescription())
                .status(Status.IN_PROGRESS)
                .build();

        Request fromDB = requestRepository.save(request1);

        return RequestResponse.builder()
                .description(fromDB.getDescription())
                .build();
    }

    public RequestResponse updateRequest(Integer id, SaveRequestRequest request){
        Request currentRequest = getRequestById(id);
        currentRequest.setDescription(request.getDescription());

        requestRepository.save(currentRequest);

        return RequestResponse.builder()
                .description(currentRequest.getDescription())
                .status(currentRequest.getStatus())
                .build();
    }

    public RequestResponse rejectRequest(Integer id){
        Request currentRequest = getRequestById(id);

        currentRequest.setStatus(Status.REJECTED);
        requestRepository.save(currentRequest);

        return RequestResponse.builder()
                .description(currentRequest.getDescription())
                .status(currentRequest.getStatus())
                .build();
    }

    public RequestResponse doneRequest(Integer id){
        Request currentRequest = getRequestById(id);

        currentRequest.setStatus(Status.DONE);
        requestRepository.save(currentRequest);

        return RequestResponse.builder()
                .description(currentRequest.getDescription())
                .status(currentRequest.getStatus())
                .build();
    }

    public void deleteRequestById(Integer id){
        Request request = getRequestById(id);

        requestRepository.delete(request);
    }
}
