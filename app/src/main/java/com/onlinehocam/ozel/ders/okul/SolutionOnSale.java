package com.onlinehocam.ozel.ders.okul;

import java.util.List;

public class SolutionOnSale
{
    LessonsAndClasses.CLASSES questionClass;
    String questionLesson;
    String demandingStudentName;
    String demandedTutorName;
    int questionRequestID;
    int questionImageID;
    int pageNo;
    double displayPrice;
    double appreciatedPrice;
    double counterPriceDemand;
    String questionAnswerVideoURL;
    String publisher, bookName, lastDeliveryDate, dueDateForAcceptance;
    List<String> commentIDs;


    boolean isShowCommentTriggered;
    CustomSolutionCommentInjector customSolutionCommentInjector;

    public SolutionOnSale(LessonsAndClasses.CLASSES questionClass, String questionLesson,
                          String demandingStudentName, String demandedTutorName, int questionRequestID,
                          int questionImageID, int pageNo, double appreciatedPrice, double counterPriceDemand,
                          String questionAnswerVideoURL, String publisher, String bookName,
                          String lastDeliveryDate, String dueDateForAcceptance, List<String> commentIDs) {
        this.questionClass = questionClass;
        this.questionLesson = questionLesson;
        this.demandingStudentName = demandingStudentName;
        this.demandedTutorName = demandedTutorName;
        this.questionRequestID = questionRequestID;
        this.questionImageID = questionImageID;
        this.pageNo = pageNo;
        this.appreciatedPrice = appreciatedPrice;
        this.counterPriceDemand = counterPriceDemand;
        this.questionAnswerVideoURL = questionAnswerVideoURL;
        this.publisher = publisher;
        this.bookName = bookName;
        this.lastDeliveryDate = lastDeliveryDate;
        this.dueDateForAcceptance = dueDateForAcceptance;
        this.commentIDs = commentIDs;
        this.displayPrice = GlobalVariables.DEFAULT_DISPLAY_PRICE;

        this.isShowCommentTriggered = false;
        this.customSolutionCommentInjector = null;
    }
}
