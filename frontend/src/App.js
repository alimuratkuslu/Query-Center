import './App.css';
import EmployeeList from './components/EmployeeList';
import ReportList from './components/ReportList';
import Dashboard from './components/Dashboard';
import SearchReport from './components/SearchReport';
import { BrowserRouter as Router, Route, Link, Routes } from 'react-router-dom';

function App() {
  return (
    <Router>
      <Routes>
        <Route exact path='/' element={<Dashboard />} />
        <Route exact path='/employee' element={<EmployeeList />} />
        <Route exact path='/report' element={<ReportList />} />
        <Route exact path='/searchReport' element={<SearchReport />} />
      </Routes>
    </Router>
  );
}

export default App;


