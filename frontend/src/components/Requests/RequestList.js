import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { DataGrid } from '@mui/x-data-grid';
import Dashboard from '../Dashboard';
import SearchRequest from './SearchRequest';
import AddIcon from '@mui/icons-material/Add';
import IconButton from '@material-ui/core/IconButton';

const columns = [
  { field: '_id', headerName: 'ID', width: 70 },
  { field: 'description', headerName: 'Request Description', width: 400 },
  { 
    field: 'status', 
    headerName: 'Request Status', 
    width: 150,
    renderCell: (params) => {
      let statusColor = '';
      let statusText = '';
      switch(params.value) {
        case 'IN_PROGRESS':
          statusColor = 'orange';
          statusText = 'IN PROGRESS';
          break;
        case 'DONE':
          statusColor = 'green';
          statusText = 'DONE';
          break;
        case 'REJECTED':
          statusColor = 'red';
          statusText = 'REJECTED';
          break;
        default:
          statusText = params.value;
      }
      return (
        <div style={{ 
          border: `1px solid ${statusColor}`,
          padding: '2px 8px',
          borderRadius: '4px',
          color: statusColor,
          fontWeight: 'bold',
          textAlign: 'center',
          width: 'fit-content',
          margin: 'auto'
        }}>
          {statusText}
        </div>
      );
    }
  },
];

const getRowId = (row) => row._id;

const RequestList = () => {
  const [requests, setRequests] = useState([]);

  useEffect(() => {

    const fetchRequests = async () => {
        const requestData = await fetch('/request');
        const requestJson = await requestData.json();
        console.log(requestJson);
        setRequests(requestJson);
    };
    fetchRequests();

  }, []);

  return (
    <div style={{ height: 600, width: '100%' }}>
      <Dashboard />
      <SearchRequest />

      <Link style={{position: 'absolute', top: '100px', right: '100px'}} to="/addRequest">
        <IconButton color='primary' >
          <AddIcon />
        </IconButton>
      </Link>
      <br />
      <div style={{ height: '65vh', width: '100%', display: 'flex', justifyContent: 'center', alignItems: 'center'}}>
        <div style={{width: '80%', height: 450}}>
          <DataGrid rows={requests} columns={columns} getRowId={getRowId} pageSize={5} />
        </div>
      </div>
    </div>
  );
};

export default RequestList;

