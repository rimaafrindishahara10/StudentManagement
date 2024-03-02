package com.studentmanagement.model;

import jakarta.persistence.*;

import java.io.File;
import java.util.Arrays;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int sId;
    private String sName;
    private String className;
    private String fileUploadImg;
    private String isActive;
    private String subject;
    private int marks;

    public Student(int sId, String sName, String className, String fileUploadImg, String isActive, String subject, int marks) {
        this.sId = sId;
        this.sName = sName;
        this.className = className;
        this.fileUploadImg = fileUploadImg;
        this.isActive = isActive;
        this.subject = subject;
        this.marks = marks;
    }



    public Student() {

    }

    public int getsId() {
        return sId;
    }

    public void setsId(int sId) {
        this.sId = sId;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getFileUploadImg() {
        return fileUploadImg;
    }

    public void setFileUploadImg(String fileUploadImg) {
        this.fileUploadImg = fileUploadImg;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }

    @Override
    public String toString() {
        return "Student{" +
                "sId=" + sId +
                ", sName='" + sName + '\'' +
                ", className='" + className + '\'' +
                ", fileUploadImg='" + fileUploadImg + '\'' +
                ", isActive='" + isActive + '\'' +
                ", subject='" + subject + '\'' +
                ", marks=" + marks +
                '}';
    }
}