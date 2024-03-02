package com.studentmanagement.services;


import com.studentmanagement.model.Student;
import com.studentmanagement.repositories.StudentRepo;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepo studentRepo;

    public void addStudent(Student student ){



        this.studentRepo.save(student);
    }

    public List<Student> getAllStudent(){
      return this.studentRepo.findAll();
    }

    public void deleteStudent(int sId){
        this.studentRepo.deleteById(sId);

    }


    //Report-Generate Logic:
    public ResponseEntity<byte[]> studentReport(String reportFormat) throws IOException, JRException {
        List<Student> studentList =this.studentRepo.findAll();

        //Load the file:
        File file = ResourceUtils.getFile("classpath:Student_Report.jrxml");

        //Compile the file:
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

        //We need Datasource for get this studentList:
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(studentList);

        //Print the report
        Map<String, Object> parameters = new HashMap<>();
        File filePath = new ClassPathResource("static/img").getFile();
        parameters.put("filePath", filePath);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        //For open the report in browser
            byte[] data = JasperExportManager.exportReportToPdf(jasperPrint);
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.CONTENT_DISPOSITION,"filename=Student_Report.pdf");

        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(data);


    }



}
