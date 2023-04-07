import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import { Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, Card } from '@material-ui/core';
import { Snackbar, Alert } from '@mui/material';
import Dashboard from '../Dashboard';
import IconButton from '@material-ui/core/IconButton';
import PlayCircleIcon from '@mui/icons-material/PlayCircle';

function ReportSchedules() {
  const { selectedReport } = useParams();
  const [schedules, setSchedules] = useState([]);
  const [selectedReportName, setSelectedReportName] = useState('');
  const [selectedReportObject, setSelectedReportObject] = useState(null);
  const [selectedReportQuery, setSelectedReportQuery] = useState(null);
  const [showSuccessMessage, setShowSuccessMessage] = useState(false);

  useEffect(() => {

    const fetchReportSchedules = async () => {
        const scheduleData = await fetch('/report/' + selectedReport);
        const scheduleJson = await scheduleData.json();
        await setSchedules(scheduleJson.schedules);
        await setSelectedReportName(scheduleJson.name);
        await setSelectedReportObject(scheduleJson); // sendEmail method'una schedule da verilebilir
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

  return (
    <div>
    <Dashboard />
    <br / >
        <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center'}}>
            <br />
            <Card style={{ width: '80%', height: '80%', margin: '1rem 0' }}>
                <h3 style={{ margin: '0.5rem' }}> Report Name: {selectedReportName} </h3>
                <TableContainer component={Paper}>
                    <Table>
                        <TableHead>
                        <TableRow>
                            <TableCell>ID</TableCell>
                            <TableCell>Name</TableCell>
                            <TableCell>Mail Subject</TableCell>
                            <TableCell>Trigger Description</TableCell>
                            <TableCell>Run Schedule</TableCell>
                        </TableRow>
                        </TableHead>
                        <TableBody>
                        {schedules.map(schedule => (
                            <TableRow key={schedule._id}>
                            <TableCell>{schedule._id}</TableCell>
                            <TableCell>{schedule.name}</TableCell>
                            <TableCell>{schedule.mailSubject}</TableCell>
                            {schedule.triggers.map(trigger => (
                                <TableCell>{trigger.name}</TableCell>
                            ))}
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


/*



{schedule.triggers.map(trigger => (
                                <TableCell>{trigger.name}</TableCell>
                            ))}
*/