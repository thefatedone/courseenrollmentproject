package com.alex.enrollment.course.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "course_enrollment")
public class CourseEnrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer enrollmentId;
    private Integer enrollmentStudentId;
    private Integer enrollmentCourseId;
    private Date enrollmentStartDate;
    private Date enrollmentEndDate;
    private Date enrollmentDate;

    public CourseEnrollment() {
    }

    public Integer getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(Integer enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public Integer getEnrollmentStudentId() {
        return enrollmentStudentId;
    }

    public void setEnrollmentStudentId(Integer enrollmentStudentId) {
        this.enrollmentStudentId = enrollmentStudentId;
    }

    public Integer getEnrollmentCourseId() {
        return enrollmentCourseId;
    }

    public void setEnrollmentCourseId(Integer enrollmentCourseId) {
        this.enrollmentCourseId = enrollmentCourseId;
    }

    public Date getEnrollmentStartDate() {
        return enrollmentStartDate;
    }

    public void setEnrollmentStartDate(Date enrollmentStartDate) {
        this.enrollmentStartDate = enrollmentStartDate;
    }

    public Date getEnrollmentEndDate() {
        return enrollmentEndDate;
    }

    public void setEnrollmentEndDate(Date enrollmentEndDate) {
        this.enrollmentEndDate = enrollmentEndDate;
    }

    public Date getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(Date enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }
}
