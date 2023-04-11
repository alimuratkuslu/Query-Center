package com.bizu.querycenter.controller;

import com.bizu.querycenter.dto.Add.AddEmployeeToTeam;
import com.bizu.querycenter.dto.Request.SaveTeamRequest;
import com.bizu.querycenter.dto.Response.TeamResponse;
import com.bizu.querycenter.model.Team;
import com.bizu.querycenter.service.TeamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/team")
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Team> getTeamById(@PathVariable Integer id){
        return ResponseEntity.ok(teamService.getTeamById(id));
    }

    @GetMapping
    public ResponseEntity<List<Team>> getAllTeams(){
        return ResponseEntity.ok(teamService.getAllTeams());
    }

    @GetMapping("/searchTeam")
    public ResponseEntity<Team> getTeamByName(@RequestParam String name){
        return ResponseEntity.ok(teamService.getTeamByName(name));
    }

    @PostMapping
    public ResponseEntity<TeamResponse> saveTeam(@RequestBody SaveTeamRequest request){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(teamService.saveTeam(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeamResponse> updateTeam(@PathVariable Integer id, @RequestBody SaveTeamRequest request){
        return ResponseEntity.ok(teamService.updateTeam(id, request));
    }

    @PostMapping("/addEmployee")
    public ResponseEntity<TeamResponse> addEmployeeToTeam(@RequestBody AddEmployeeToTeam request){
        return ResponseEntity.ok(teamService.addEmployeeToTeam(request));
    }

    @DeleteMapping("/{id}")
    public void deleteTeam(@PathVariable Integer id){
        teamService.deleteTeamById(id);
    }
}
