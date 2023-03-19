import React, { useState, useEffect } from 'react';
import { DataGrid } from '@mui/x-data-grid';

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
    <div style={{ height: 700, width: '100%' }}>
      <DataGrid rows={employees} columns={columns} getRowId={getRowId} pageSize={5} />
    </div>
  );
};

export default EmployeeList;

