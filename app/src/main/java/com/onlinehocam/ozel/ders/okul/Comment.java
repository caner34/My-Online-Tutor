package com.onlinehocam.ozel.ders.okul;

class Comment
{
    String questionClass;
    String questionLesson;
    String studentName;
    String tutorName;
    String commentText;
    int studentID;
    int tutorUserID;
    int questionRequestID;
    double rating;

    public Comment(String questionClass, String questionLesson, String studentName, String tutorName, String commentText, int studentID, int tutorUserID, int questionRequestID, double rating) {
        this.questionClass = questionClass;
        this.questionLesson = questionLesson;
        this.studentName = studentName;
        this.tutorName = tutorName;
        this.commentText = commentText;
        this.studentID = studentID;
        this.tutorUserID = tutorUserID;
        this.questionRequestID = questionRequestID;
        this.rating = rating;
    }
}
