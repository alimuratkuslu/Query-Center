package com.bizu.querycenter.service;

import com.bizu.querycenter.dto.SaveOwnershipRequest;
import com.bizu.querycenter.model.Report;
import com.bizu.querycenter.model.ReportOwnership;
import com.bizu.querycenter.repository.ReportOwnershipRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReportOwnershipService {

    private final ReportOwnershipRepository repository;

    public ReportOwnershipService(ReportOwnershipRepository repository) {
        this.repository = repository;
    }

    public ReportOwnership getReportOwnershipById(Integer id){
        ReportOwnership ownership = repository.findById(id).orElseThrow(RuntimeException::new);

        return ownership;
    }
    
    public ReportOwnership doesEmployeeHaveOwnership(Integer employeeId, Integer reportId){
        List<ReportOwnership> ownerships = getAllOwnerships();

        for (int i = 0; i < ownerships.size(); i++) {
            if(ownerships.get(i).getEmployee().get_id() == employeeId && ownerships.get(i).getReport().get_id() == reportId){
                return ownerships.get(i);
            }
        }

        throw new RuntimeException("Employee with id: " + employeeId + " does not have ownership");
    }

    public void deleteOwnership(Integer employeeId, Integer reportId){
        List<ReportOwnership> ownerships = getAllOwnerships();

        for (int i = 0; i < ownerships.size(); i++) {
            if(ownerships.get(i).getEmployee().get_id() == employeeId && ownerships.get(i).getReport().get_id() == reportId){
                ownerships.remove(ownerships.get(i));
            }
        }
    }

    public List<ReportOwnership> getAllOwnerships(){
        List<ReportOwnership> ownerships = new ArrayList<>();
        repository.findAll().forEach(ownerships::add);

        return ownerships;
    }

    public ReportOwnership saveOwnership(SaveOwnershipRequest request){
        List<ReportOwnership> ownerships = getAllOwnerships();
        int size = ownerships.size() + 2;

        ReportOwnership ownership = ReportOwnership.builder()
                ._id(size)
                .report(request.getReport())
                .employee(request.getEmployee())
                .isOwner(request.isOwner())
                .isRead(request.isRead())
                .isWrite(request.isWrite())
                .isRun(request.isRun())
                .build();

        ReportOwnership fromDB = repository.save(ownership);

        return fromDB;
    }
}
