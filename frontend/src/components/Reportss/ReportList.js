import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { DataGrid } from '@mui/x-data-grid';
import Dashboard from '../Dashboard';
import SearchReport from './SearchReport';
import AddIcon from '@mui/icons-material/Add';
import IconButton from '@material-ui/core/IconButton';
import { Checkbox } from '@mui/material';

const columns = [
  { field: '_id', headerName: 'ID', width: 70 },
  { field: 'name', headerName: 'Report Name', width: 130 },
  { field: 'sqlQuery', headerName: 'SQL Query', width: 130 },
  { field: 'database.name', headerName: 'Database', width: 130, valueGetter: (params) => {
      if (params.row.database) {
        return params.row.database.name;
      } else {
        return "-";
      }
    }, 
  },
  {
    field: 'employees',
    headerName: 'Employees',
    width: 550,
    renderCell: (params) => (
      <div style={{ display: 'flex'}}>
        {params.row.employees.map((employee) => (
          <div key={employee._id} style={{ marginRight: '10px'}}>
            {employee.name}
          </div>
        ))}
      </div>
    ),
  },
  { field: 'active', headerName: 'Active Status', width: 100, renderCell: (params) => (
    <Checkbox checked={params.value} disabled />
  ),
},
];

const getRowId = (row) => row._id;

const ReportList = () => {
  const [reports, setReport] = useState([]);

  useEffect(() => {

    const fetchReports = async () => {
        const reportData = await fetch('/report');
        const reportJson = await reportData.json();
        console.log(reportJson);
        setReport(reportJson);
    };
    fetchReports();

  }, []);

  return (
    <div>
      <Dashboard />
      <SearchReport />

      <Link style={{position: 'absolute', top: '100px', right: '100px'}} to="/addReport">
        <IconButton color='primary' >
          <AddIcon />
        </IconButton>
      </Link>
      <br />
      <div style={{ height: '65vh', width: '100%', display: 'flex', justifyContent: 'center', alignItems: 'center'}}>
        <div style={{width: '80%', height: 450}}>
          <DataGrid rows={reports} columns={columns} getRowId={getRowId} pageSize={5} />
        </div>
      </div>
    </div>
  );
};

export default ReportList;

