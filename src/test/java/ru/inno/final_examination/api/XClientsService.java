package ru.inno.final_examination.api;

import ru.inno.final_examination.model.Company;
import ru.inno.final_examination.model.Employee;

import java.io.IOException;
import java.util.List;

public interface XClientsService {
    List<Company> getAllCompanyList() throws IOException;

    List<Company> getActiveCopmanyList() throws IOException;

    int addCompany() throws IOException;

    void deleteCompany(int companyId) throws IOException;

    int addEmployee(int companyId) throws IOException;

    List<Employee> getEmployeeList();

    List<Employee> getEmployeeByCompanyId(int companyId) throws IOException;

}