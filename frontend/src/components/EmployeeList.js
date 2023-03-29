import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { DataGrid } from '@mui/x-data-grid';
import Dashboard from './Dashboard';
import SearchEmployee from './SearchEmployee';
import AddIcon from '@mui/icons-material/Add';
import IconButton from '@material-ui/core/IconButton';

const columns = [
  { field: '_id', headerName: 'ID', width: 70 },
  { field: 'name', headerName: 'Full Name', width: 130 },
  { field: 'email', headerName: 'Email', width: 300 },
];

const getRowId = (row) => row._id;

const EmployeeList = () => {
  const [employees, setEmployees] = useState([]);

  useEffect(() => {

    const fetchEmployees = async () => {
        const employeeData = await fetch('/employee');
        const employeeJson = await employeeData.json();
        console.log(employeeJson);
        setEmployees(employeeJson);
    };
    fetchEmployees();

  }, []);

  return (
    <div>
      <Dashboard />
      <SearchEmployee />
      
      <Link style={{position: 'absolute', top: '100px', right: '100px'}} to="/addEmployee">
        <IconButton color='primary' >
          <AddIcon />
        </IconButton>
      </Link>
      <br />
      <div style={{ height: '65vh', width: '100%', display: 'flex', justifyContent: 'center', alignItems: 'center'}}>
        <div style={{width: '80%', height: 450}}>
          <DataGrid rows={employees} columns={columns} getRowId={getRowId} pageSize={5} />
        </div>
      </div>
    </div>
  );
};

export default EmployeeList;

