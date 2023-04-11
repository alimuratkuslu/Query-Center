package com.bizu.querycenter.controller;

import com.bizu.querycenter.dto.Add.AddDatabaseToReport;
import com.bizu.querycenter.dto.Request.SaveDatabaseRequest;
import com.bizu.querycenter.dto.Response.DatabaseResponse;
import com.bizu.querycenter.model.Database;
import com.bizu.querycenter.service.DatabaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/database")
public class DatabaseController {

    private final DatabaseService databaseService;

    public DatabaseController(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Database> getDatabaseById(@PathVariable Integer id){
        return ResponseEntity.ok(databaseService.getDatabaseById(id));
    }

    @GetMapping
    public ResponseEntity<List<Database>> getAllDatabases(){
        return ResponseEntity.ok(databaseService.getAllDatabases());
    }

    @GetMapping("/searchDatabase")
    public ResponseEntity<Database> getDatabaseByName(@RequestParam String name){
        return ResponseEntity.ok(databaseService.getDatabaseByName(name));
    }

    @PostMapping
    public ResponseEntity<DatabaseResponse> saveDatabase(@RequestBody SaveDatabaseRequest request){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(databaseService.saveDatabase(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DatabaseResponse> updateDatabase(@PathVariable Integer id, @RequestBody SaveDatabaseRequest request){
        return ResponseEntity.ok(databaseService.updateDatabase(id, request));
    }

    @PostMapping("/addReport")
    public ResponseEntity<DatabaseResponse> addDatabaseToReport(@RequestBody AddDatabaseToReport databaseDto){
        return ResponseEntity.ok(databaseService.addDatabaseToReport(databaseDto));
    }

    @DeleteMapping("/{id}")
    public void deleteDatabase(@PathVariable Integer id){
        databaseService.deleteDatabaseById(id);
    }
}
