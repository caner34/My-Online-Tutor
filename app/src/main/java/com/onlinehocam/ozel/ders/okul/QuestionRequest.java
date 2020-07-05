package com.onlinehocam.ozel.ders.okul;

public class QuestionRequest
{
    public LessonsAndClasses.CLASSES questionClass;
    public String questionLesson;
    public String demandingStudentName;
    public String demandedTutorName;
    public int questionRequestID;
    public int questionImageID;
    public int pageNo;
    public double appreciatedPrice;
    public double counterPriceDemand;
    public String solutionVideoUrlID;
    public String publisher, bookName, lastDeliveryDate, dueDateForAcceptance, withdrawalExcuse;
    public GlobalVariables.QUESTION_REQUEST_STATE questionRequestState;

    public QuestionRequest(LessonsAndClasses.CLASSES questionClass, String questionLesson,
                           String demandingStudentName, String demandedTutorName, int questionRequestID,
                           int questionImageID, int pageNo, double appreciatedPrice, double counterPriceDemand,
                           String solutionVideoUrlID, String publisher, String bookName,
                           String lastDeliveryDate, String dueDateForAcceptance, GlobalVariables.QUESTION_REQUEST_STATE questionRequestState, String withdrawalExcuse) {
        this.questionClass = questionClass;
        this.questionLesson = questionLesson;
        this.demandingStudentName = demandingStudentName;
        this.demandedTutorName = demandedTutorName;
        this.questionRequestID = questionRequestID;
        this.questionImageID = questionImageID;
        this.pageNo = pageNo;
        this.appreciatedPrice = appreciatedPrice;
        this.counterPriceDemand = counterPriceDemand;
        this.solutionVideoUrlID = solutionVideoUrlID;
        this.publisher = publisher;
        this.bookName = bookName;
        this.lastDeliveryDate = lastDeliveryDate;
        this.dueDateForAcceptance = dueDateForAcceptance;
        this.questionRequestState = questionRequestState;
        this.withdrawalExcuse = withdrawalExcuse;
    }

    @Override
    public String toString() {
        return "QuestionRequest{" +
                "questionClass=" + questionClass +
                ", questionLesson='" + questionLesson + '\'' +
                ", demandingStudentName='" + demandingStudentName + '\'' +
                ", demandedTutorName='" + demandedTutorName + '\'' +
                ", questionRequestID=" + questionRequestID +
                ", questionImageID=" + questionImageID +
                ", pageNo=" + pageNo +
                ", appreciatedPrice=" + appreciatedPrice +
                ", counterPriceDemand=" + counterPriceDemand +
                ", solutionVideoUrlID='" + solutionVideoUrlID + '\'' +
                ", publisher='" + publisher + '\'' +
                ", bookName='" + bookName + '\'' +
                ", lastDeliveryDate='" + lastDeliveryDate + '\'' +
                ", dueDateForAcceptance='" + dueDateForAcceptance + '\'' +
                ", questionRequestState='" + questionRequestState + '\'' +
                ", withdrawalExcuse=" + withdrawalExcuse +
                '}';
    }
}
