import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import { Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, Card } from '@material-ui/core';
import { Snackbar, Alert, Autocomplete, TextField, Button } from '@mui/material';
import Dashboard from '../Dashboard';
import IconButton from '@material-ui/core/IconButton';
import PlayCircleIcon from '@mui/icons-material/PlayCircle';
import CreateIcon from '@mui/icons-material/Create';

function ReportSchedules() {
  const { selectedReport } = useParams();
  const [schedules, setSchedules] = useState([]);
  const [allSchedules, setAllSchedules] = useState([]);
  const [selectedSchedule, setSelectedSchedule] = useState(null);
  const [selectedReportName, setSelectedReportName] = useState('');
  const [selectedReportObject, setSelectedReportObject] = useState(null);
  const [selectedReportQuery, setSelectedReportQuery] = useState(null);
  const [showSuccessMessage, setShowSuccessMessage] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {

    const fetchSchedules = async () => {
        const scheduleData = await fetch('/schedule');
        const scheduleJson = await scheduleData.json();
        setAllSchedules(scheduleJson);
    };
    fetchSchedules();

    const fetchReportSchedules = async () => {
        const scheduleData = await fetch('/report/' + selectedReport);
        const scheduleJson = await scheduleData.json();
        await setSchedules(scheduleJson.schedules);
        await setSelectedReportName(scheduleJson.name);
        await setSelectedReportObject(scheduleJson); 
        await setSelectedReportQuery(scheduleJson.sqlQuery);
        console.log("Selected Report Name: ", selectedReportName);
    };
    fetchReportSchedules();

  }, []);

  const sendEmail = async (schedule) => {
    try {
      const response = await axios.post('/email/sendEmail', { 
        report: selectedReportObject, 
        schedule: schedule,
        filter: selectedReportQuery, 
        projection: '{ _id: 1, name: 1, email: 1 }'});
      setShowSuccessMessage(true);
      const result = response.data;
      console.log(result);

    } catch (error) {
      console.error('There was a problem with the fetch operation:', error);
    }
  };

  const addScheduleToReport = async () => {
    try {
      console.log("Selected Report Id: ", selectedReport);
      const response = await axios.post('/report/addSchedule', { 
        reportId: selectedReport, 
        scheduleId: selectedSchedule._id});
      
      const result = response.data;
      console.log(result);

    } catch (error) {
      console.error('There was a problem with the fetch operation:', error);
    }
  };

  function updateSchedule(selectedSchedule) {
    navigate(`/update-schedule/${selectedSchedule}`);
  }

  return (
    <div>
    <Dashboard />
    <br / >
        <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between', width: '100%' }}>
            <Autocomplete
                id="search-schedule"
                style={{ flex: 1, marginRight: '1rem', marginLeft: '30px'}}
                disablePortal
                options={allSchedules}
                getOptionLabel={option => option.name}
                value={selectedSchedule}
                onChange={(event, value) => setSelectedSchedule(value)}
                renderInput={(params) => (
                    <TextField 
                    {...params} 
                    label="Pick Schedule"
                    margin='normal'
                    style={{ width: '300px'}}
                    variant="outlined" 
                />
                )}
            />
            <Button style={{marginRight: '1300px'}} variant='contained' type='submit' onClick={addScheduleToReport}>Add Schedule</Button>
        </div>
        <br />
        <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center'}}>
            <br />
            <Card style={{ width: '80%', height: '80%', margin: '1rem 0' }}>
                <h3 style={{ margin: '0.5rem' }}> {selectedReportName} </h3>
                <TableContainer component={Paper}>
                    <Table>
                        <TableHead>
                        <TableRow>
                            <TableCell>ID</TableCell>
                            <TableCell>Name</TableCell>
                            <TableCell>Mail Subject</TableCell>
                            <TableCell>Trigger Description</TableCell>
                            <TableCell>Recipients</TableCell>
                            <TableCell>Update Schedule</TableCell>
                            <TableCell>Run Schedule</TableCell>
                        </TableRow>
                        </TableHead>
                        <TableBody>
                        {schedules.map(schedule => (
                            <TableRow key={schedule._id}>
                            <TableCell>{schedule._id}</TableCell>
                            <TableCell>{schedule.name}</TableCell>
                            <TableCell>{schedule.mailSubject}</TableCell>
                            {schedule.triggers.length === 0 ? (
                                <TableCell>-</TableCell>
                                ) : (
                                schedule.triggers.map(trigger => (
                                    <TableCell>{trigger.name}</TableCell>
                                ))
                            )}
                            {schedule.recipients.length === 0 ? (
                                <TableCell>-</TableCell>
                                ) : (
                                <TableCell>{schedule.recipients.join(", ")}</TableCell>
                            )}
                            <TableCell>
                                <IconButton aria-label='update' onClick={() => updateSchedule(schedule._id)}>
                                    <CreateIcon />
                                </IconButton>
                            </TableCell>
                            <TableCell>
                                <IconButton aria-label='run' onClick={() => sendEmail(schedule)}>
                                    <PlayCircleIcon />
                                </IconButton>
                            </TableCell>
                            </TableRow>
                        ))}
                        </TableBody>
                    </Table>
                </TableContainer>
            </Card>
            <Snackbar
                anchorOrigin={{ vertical: 'bottom', horizontal: 'center' }}
                open={showSuccessMessage}
                autoHideDuration={3000}
                onClose={() => setShowSuccessMessage(false)}
            >
                <Alert severity="success" onClose={() => setShowSuccessMessage(false)}>
                    Email sent successfully
                </Alert>
            </Snackbar>
        </div>
    </div>
  );
}

export default ReportSchedules;
