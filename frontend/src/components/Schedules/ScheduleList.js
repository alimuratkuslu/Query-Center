import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { DataGrid } from '@mui/x-data-grid';
import Dashboard from '../Dashboard';
import SearchSchedule from './SearchSchedule';
import AddIcon from '@mui/icons-material/Add';
import IconButton from '@material-ui/core/IconButton';

const columns = [
  { field: '_id', headerName: 'ID', width: 70 },
  { field: 'name', headerName: 'Schedule Name', width: 130 },
  { field: 'mailSubject', headerName: 'Mail Subject', width: 130 },
  { field: 'recipients', headerName: 'Recipients', width: 600 },
  {
    field: 'triggers',
    headerName: 'Triggers',
    width: 600,
    renderCell: (params) => (
      <div style={{ display: 'flex'}}>
        {params.row.triggers && params.row.triggers.length > 0 ? (
            params.row.triggers.map((trigger) => (
              <div key={trigger._id} style={{ marginRight: '10px'}}>
                {trigger.name}
              </div>
            ))
          ) : (
            <div>No triggers found</div>
          )}
      </div>
    ),
  },
];

const getRowId = (row) => row._id;

const ScheduleList = () => {
  const [schedules, setSchedules] = useState([]);

  useEffect(() => {

    const fetchSchedules = async () => {
        const scheduleData = await fetch('/schedule');
        const scheduleJson = await scheduleData.json();
        console.log(scheduleJson);
        setSchedules(scheduleJson);
    };
    fetchSchedules();

  }, []);

  return (
    <div style={{ height: 600, width: '100%' }}>
      <Dashboard />
      <SearchSchedule />

      <Link style={{position: 'absolute', top: '100px', right: '100px'}} to="/addSchedule">
        <IconButton color='primary' >
          <AddIcon />
        </IconButton>
      </Link>
      <br />
      <div style={{ height: '65vh', width: '100%', display: 'flex', justifyContent: 'center', alignItems: 'center'}}>
        <div style={{width: '80%', height: 450}}>
          <DataGrid rows={schedules} columns={columns} getRowId={getRowId} pageSize={5} />
        </div>
      </div>
    </div>
  );
};

export default ScheduleList;

