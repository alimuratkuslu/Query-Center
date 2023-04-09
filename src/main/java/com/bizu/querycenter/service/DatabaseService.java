package com.bizu.querycenter.service;

import com.bizu.querycenter.dto.Add.AddDatabaseToReport;
import com.bizu.querycenter.dto.Request.SaveDatabaseRequest;
import com.bizu.querycenter.dto.Response.DatabaseResponse;
import com.bizu.querycenter.model.Database;
import com.bizu.querycenter.model.Report;
import com.bizu.querycenter.repository.DatabaseRepository;
import com.bizu.querycenter.repository.ReportRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DatabaseService {

    private final DatabaseRepository databaseRepository;
    private final ReportRepository reportRepository;

    public DatabaseService(DatabaseRepository databaseRepository, ReportRepository reportRepository) {
        this.databaseRepository = databaseRepository;
        this.reportRepository = reportRepository;
    }

    public Database getDatabaseById(Integer id){
        Database database = databaseRepository.findById(id).orElseThrow(RuntimeException::new);

        return database;
    }

    public List<Database> getAllDatabases(){
        List<Database> databases = new ArrayList<>();
        databaseRepository.findAll().forEach(databases::add);

        return databases;
    }

    public Database getDatabaseByName(String databaseName){
        Database database = databaseRepository.findByName(databaseName);

        return database;
    }

    public DatabaseResponse saveDatabase(SaveDatabaseRequest request){

        List<Database> databases = getAllDatabases();
        int size = databases.size() + 2;
        List<Report> reports = new ArrayList<>();

        Database database = Database.builder()
                ._id(size)
                .name(request.getName())
                .connectionString(request.getConnectionString())
                .reports(reports)
                .build();

        Database fromDB = databaseRepository.save(database);

        return DatabaseResponse.builder()
                .name(fromDB.getName())
                .connectionString(fromDB.getConnectionString())
                .reports(fromDB.getReports())
                .build();
    }

    public DatabaseResponse updateDatabase(Integer id, SaveDatabaseRequest request){
        Database currentDatabase = getDatabaseById(id);

        currentDatabase.setName(request.getName());
        currentDatabase.setConnectionString(request.getConnectionString());

        databaseRepository.save(currentDatabase);

        return DatabaseResponse.builder()
                .name(currentDatabase.getName())
                .connectionString(currentDatabase.getConnectionString())
                .reports(currentDatabase.getReports())
                .build();
    }

    public DatabaseResponse addDatabaseToReport(AddDatabaseToReport request){
        Database database = getDatabaseById(request.getDatabaseId());
        Report report = reportRepository.findById(request.getReportId()).orElseThrow(RuntimeException::new);
        List<Report> reports = database.getReports();

        reports.add(report);
        database.setReports(reports);
        report.setDatabase(database);

        databaseRepository.save(database);
        reportRepository.save(report);

        return DatabaseResponse.builder()
                .name(database.getName())
                .connectionString(database.getConnectionString())
                .reports(database.getReports())
                .build();
    }

    public void deleteDatabaseById(Integer id){
        if(doesDatabaseExist(id)){
            databaseRepository.deleteById(id);
        }
        else{
            throw new RuntimeException("Database does not exist");
        }
    }

    private boolean doesDatabaseExist(Integer id){
        return databaseRepository.existsById(id);
    }
}
