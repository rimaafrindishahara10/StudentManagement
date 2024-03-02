package com.studentmanagement.controllers;


import com.studentmanagement.model.Student;
import com.studentmanagement.repositories.StudentRepo;
import com.studentmanagement.services.StudentService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;



@Controller
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentRepo studentRepo;

    //Show-Student add form
    @GetMapping("/homePage")
    public String studentDetails(){

        return "studentDetails";
    }

    //Add-student details
    @PostMapping("/addStudent")
    public String addStudent(@ModelAttribute Student student,@RequestParam MultipartFile fileUpload) throws IOException {


       try{

           if (fileUpload.isEmpty()){
               System.out.println("file is empty");
           }
           else {

               student.setFileUploadImg(fileUpload.getOriginalFilename());
               File filePath = new ClassPathResource("static/img").getFile();
               Path path = Paths.get(filePath.getAbsolutePath() + File.separator + fileUpload.getOriginalFilename());
               Files.copy(fileUpload.getInputStream(),path,StandardCopyOption.REPLACE_EXISTING);
               System.out.println(fileUpload.getOriginalFilename());
               System.out.println("Image is uploaded");

           }

           System.out.println("Student"+ student);
           this.studentService.addStudent(student);

       }catch (Exception e){
           System.out.println("ERROR:"+e);
           e.printStackTrace();

       }


        return "redirect:/homePage";
    }


    //Show all - Student List
    @GetMapping("/allStudent")
    public String showAllStudents( Model model){
        model.addAttribute("allStudent",this.studentService.getAllStudent());
        return "allStudent";
    }

    //Delete student details
    @GetMapping("/deleteStudent/{sId}")
    public String deleteStudent(@PathVariable Integer sId){
        this.studentService.deleteStudent(sId);
        return "redirect:/allStudent";
    }

    //OpenUpdatebutton-student details
    @PostMapping("/updateStudent/{sId}")
    public String openUpdateButton(@PathVariable Integer sId, Model m){

        Student upStudent = this.studentRepo.findById(sId).get();

        m.addAttribute("upStudent",upStudent);



        return "updateStudent";
    }


    //Update Student-Details
    @PostMapping("/updateStudent")
    public  String updateStudent(@ModelAttribute Student student, @RequestParam MultipartFile fileUpload )throws Exception{


        try{

            Student oldStudentDetails = this.studentRepo.findById(student.getsId()).get();



//            image---
            if (fileUpload.isEmpty()){
                System.out.println("file is empty");
                student.setFileUploadImg(oldStudentDetails.getFileUploadImg());
            }
            else {

                student.setFileUploadImg(fileUpload.getOriginalFilename());
                File filePath = new ClassPathResource("static/img").getFile();
                Path path = Paths.get(filePath.getAbsolutePath() + File.separator + fileUpload.getOriginalFilename());
                Files.copy(fileUpload.getInputStream(),path,StandardCopyOption.REPLACE_EXISTING);
                System.out.println(fileUpload.getOriginalFilename());


            }


            this.studentRepo.save(student);


        }catch (Exception e){
            e.printStackTrace();
        }

        return "redirect:/allStudent";
    }



    //Jasper Report handler:
    @GetMapping("/report/{format}")
    public ResponseEntity<byte[]> generateReport(@PathVariable String format) throws IOException, JRException {
        ResponseEntity<byte[]> responseEntity = this.studentService.studentReport(format);
        return responseEntity  ;
    }

    //Show the viewPage
    @GetMapping("/viewPage/{sId}")
    public String openViewPage(@PathVariable Integer sId, Model m){

        Student upStudent = this.studentRepo.findById(sId).get();

        m.addAttribute("upStudent",upStudent);





        return "viewPage";
    }


}
