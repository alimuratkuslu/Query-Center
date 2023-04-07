import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, Card } from '@material-ui/core';
import Dashboard from '../Dashboard';
import IconButton from '@material-ui/core/IconButton';
import PlayCircleIcon from '@mui/icons-material/PlayCircle';

function ReportSchedules() {
  const { selectedReport } = useParams();
  const [schedules, setSchedules] = useState([]);

  useEffect(() => {
    fetch('/report/' + selectedReport)
      .then(response => response.json())
      .then(data => setSchedules(data.schedules));
  }, []);

  return (
    <div>
    <Dashboard />
    <br / >
        <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center'}}>
            <Card style={{ width: '80%', height: '80%', margin: '1rem 0' }}>
                <h3 style={{ margin: '0.5rem' }}> Schedules: </h3>
                <TableContainer component={Paper}>
                    <Table>
                        <TableHead>
                        <TableRow>
                            <TableCell>ID</TableCell>
                            <TableCell>Name</TableCell>
                            <TableCell>Mail Subject</TableCell>
                            <TableCell>Run Schedule</TableCell>
                        </TableRow>
                        </TableHead>
                        <TableBody>
                        {schedules.map(schedule => (
                            <TableRow key={schedule._id}>
                            <TableCell>{schedule._id}</TableCell>
                            <TableCell>{schedule.name}</TableCell>
                            <TableCell>{schedule.mailSubject}</TableCell>
                            <TableCell>
                                <IconButton aria-label='run'>
                                    <PlayCircleIcon />
                                </IconButton>
                            </TableCell>
                            </TableRow>
                        ))}
                        </TableBody>
                    </Table>
                </TableContainer>
            </Card>
        </div>
    </div>
  );
}

export default ReportSchedules;
