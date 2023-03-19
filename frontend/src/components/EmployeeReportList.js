import React, { useState, useEffect } from 'react';

const EmployeeReportList = () => {
  const [employees, setEmployees] = useState([]);
  const [reports, setReports] = useState([]);
  const [ownerships, setOwnerships] = useState([]);

  useEffect(() => {
    const fetchEmployeeAndReportData = async () => {
      const employeeData = await fetch('/employee');
      const reportData = await fetch('/report');
      const reportOwnershipData = await fetch('/ownership');
      const employeeJson = await employeeData.json();
      const reportJson = await reportData.json();
      const ownershipJson = await reportOwnershipData.json();
      console.log(ownershipJson);
      setEmployees(employeeJson);
      setReports(reportJson);
      setOwnerships(ownershipJson)
    };
    fetchEmployeeAndReportData();
  }, []);

  return (
    <div>
      <h1>Employee - Report - Ownership List</h1>
      <h2>Employees</h2>
      {employees.map((employee) => (
        <li key={employee._id}>
            {[employee.name, employee.email].join(' - ')}
        </li>
      ))}
      <h2>Reports</h2>
      {reports.map((report) => (
        <li key={report._id}>
            {[report.name]}
        </li>
      ))}
      <h2>Ownerships</h2>
      {ownerships.map((ownership) => (
        <li key={ownership._id}>
            {[ownership.report.name, ownership.employee.name, ownership.owner].join(' - ')}
        </li>
      ))}
    </div>
  );
};

export default EmployeeReportList;
