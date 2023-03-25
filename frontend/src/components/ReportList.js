import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { DataGrid } from '@mui/x-data-grid';
import Dashboard from './Dashboard';
import SearchReport from './SearchReport';
import AddIcon from '@mui/icons-material/Add';
import IconButton from '@material-ui/core/IconButton';

const columns = [
  { field: '_id', headerName: 'ID', width: 70 },
  { field: 'name', headerName: 'Report Name', width: 130 },
  { field: 'sqlQuery', headerName: 'SQL Query', width: 130 },
  {
    field: 'employees',
    headerName: 'Employees',
    width: 600,
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
    <div style={{ height: 600, width: '100%' }}>
      <Dashboard />
      <SearchReport />

      <Link style={{position: 'absolute', top: '100px', right: '100px'}} to="/addReport">
        <IconButton color='primary' >
          <AddIcon />
        </IconButton>
      </Link>
      <br />
      <DataGrid rows={reports} columns={columns} getRowId={getRowId} pageSize={5} />
    </div>
  );
};

export default ReportList;

