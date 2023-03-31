package com.bizu.querycenter.service;

import com.bizu.querycenter.dto.Add.AddEmployeeToTeam;
import com.bizu.querycenter.dto.Request.SaveTeamRequest;
import com.bizu.querycenter.dto.Response.TeamResponse;
import com.bizu.querycenter.model.Employee;
import com.bizu.querycenter.model.Team;
import com.bizu.querycenter.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final EmployeeService employeeService;

    public TeamService(TeamRepository teamRepository, EmployeeService employeeService) {
        this.teamRepository = teamRepository;
        this.employeeService = employeeService;
    }

    public Team getTeamById(Integer id){
        Team team = teamRepository.findById(id).orElseThrow(RuntimeException::new);

        return team;
    }

    public List<Team> getAllTeams(){
        List<Team> teams = new ArrayList<>();
        teamRepository.findAll().forEach(teams::add);

        return teams;
    }

    public TeamResponse saveTeam(SaveTeamRequest request){

        List<Team> teams = getAllTeams();

        int size = teams.size() + 2;

        Team team = Team.builder()
                ._id(size)
                .name(request.getName())
                .teamMail(request.getTeamMail())
                .build();

        Team fromDB = teamRepository.save(team);

        return TeamResponse.builder()
                .name(fromDB.getName())
                .employees(fromDB.getEmployees())
                .teamMail(fromDB.getTeamMail())
                .build();
    }

    public TeamResponse updateTeam(Integer id, SaveTeamRequest request){
        Team currentTeam = getTeamById(id);
        currentTeam.setName(request.getName());
        currentTeam.setTeamMail(request.getTeamMail());

        teamRepository.save(currentTeam);

        return TeamResponse.builder()
                .name(currentTeam.getName())
                .employees(currentTeam.getEmployees())
                .teamMail(currentTeam.getTeamMail())
                .build();
    }

    public TeamResponse addEmployeeToTeam(AddEmployeeToTeam request){
        Employee employee = employeeService.getEmployeeById(request.getEmployeeId());
        Team team = getTeamById(request.getTeamId());

        List<Employee> employees = team.getEmployees();
        employees.add(employee);
        team.setEmployees(employees);

        teamRepository.save(team);

        return TeamResponse.builder()
                .name(team.getName())
                .employees(team.getEmployees())
                .teamMail(team.getTeamMail())
                .build();
    }

    public void deleteTeamById(Integer id){
        Team team = getTeamById(id);

        teamRepository.delete(team);
    }


}
