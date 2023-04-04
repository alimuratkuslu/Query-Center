import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { DataGrid } from '@mui/x-data-grid';
import Dashboard from '../Dashboard';
import SearchTeam from './SearchTeam';
import AddIcon from '@mui/icons-material/Add';
import IconButton from '@material-ui/core/IconButton';

const columns = [
  { field: '_id', headerName: 'ID', width: 70 },
  { field: 'name', headerName: 'Team Name', width: 130 },
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
  { field: 'teamMail', headerName: 'Team Mail', width: 130 },
];

const getRowId = (row) => row._id;

const TeamList = () => {
  const [teams, setTeams] = useState([]);

  useEffect(() => {

    const fetchTeams = async () => {
        const teamData = await fetch('/team');
        const teamJson = await teamData.json();
        console.log(teamJson);
        setTeams(teamJson);
    };
    fetchTeams();

  }, []);

  return (
    <div>
      <Dashboard />
      <SearchTeam />

      <Link style={{position: 'absolute', top: '100px', right: '100px'}} to="/addTeam">
        <IconButton color='primary' >
          <AddIcon />
        </IconButton>
      </Link>
      <br />
      <div style={{ height: '65vh', width: '100%', display: 'flex', justifyContent: 'center', alignItems: 'center'}}>
        <div style={{width: '80%', height: 450}}>
          <DataGrid rows={teams} columns={columns} getRowId={getRowId} pageSize={5} />
        </div>
      </div>
    </div>
  );
};

export default TeamList;

