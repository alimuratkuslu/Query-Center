import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { DataGrid } from '@mui/x-data-grid';
import Dashboard from './Dashboard';
import SearchOwnership from './SearchOwnership';
import AddIcon from '@mui/icons-material/Add';
import IconButton from '@material-ui/core/IconButton';
import { Checkbox } from '@mui/material';

const getRowId = (row) => row._id;

const ReportOwnershipList = () => {
  const [ownerships, setOwnerships] = useState([]);

  useEffect(() => {

    const fetchOwnerships = async () => {
        const ownershipData = await fetch('/ownership');
        const ownershipJson = await ownershipData.json();
        console.log(ownershipJson);
        setOwnerships(ownershipJson);
    };
    fetchOwnerships();

  }, []);

  return (
    <div>
      <Dashboard />
      <SearchOwnership />
      
      <Link style={{position: 'absolute', top: '100px', right: '100px'}} to="/addOwnership">
        <IconButton color='primary' >
          <AddIcon />
        </IconButton>
      </Link>
      <br />
      <div style={{ height: '65vh', width: '100%', display: 'flex', justifyContent: 'center', alignItems: 'center'}}>
        <div style={{width: '80%', height: 450}}>
          <DataGrid rows={ownerships} columns={[
            { field: '_id', headerName: 'ID', width: 70 },

            { field: 'report.name', headerName: 'Report Name', width: 130, valueGetter: (params) => (
              params.row.report.name
            ), },
            { field: 'employee.name', headerName: 'Employee Name', width: 130, valueGetter: (params) => (
              params.row.employee.name
            ), },

            { field: 'owner', headerName: 'Ownership', width: 100, renderCell: (params) => (
                <Checkbox checked={params.value} disabled />
              ),
            },
            { field: 'read', headerName: 'Read', width: 50, renderCell: (params) => (
                <Checkbox checked={params.value} disabled />
              ),
            },
            { field: 'write', headerName: 'Write', width: 50, renderCell: (params) => (
                <Checkbox checked={params.value} disabled />
              ),
            },
            { field: 'run', headerName: 'Run', width: 50, renderCell: (params) => (
                <Checkbox checked={params.value} disabled />
              ),
            },
          ]} getRowId={getRowId} pageSize={5} />
        </div>
      </div>
    </div>
  );
};

export default ReportOwnershipList;

