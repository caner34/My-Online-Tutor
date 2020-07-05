package com.onlinehocam.ozel.ders.okul;

import android.provider.BaseColumns;

public class DB
{

    public static final String CONS_EMPTY = "empty";
    public static final String CONS_CANNOT_BE_DISPLAYED_NOW_TR = "Şu an görüntülenemiyor";


    public static final String LINE_SEPARATOR = "xLx";
    public static final String COLUMN_SEPARATOR = "xCx";


    //Question Requests
    public static final String YOUTUBE_LINK_INITIAL = "http://www.youtube.com/watch?v=";

    //Objection Reasons
    public static final String KEY_OTHER_REASON = "other";
    public static final String TR_KEY_OTHER_OBJECTION_REASON = "Diğer";



    public static abstract class TutorsTable implements BaseColumns
    {
        public static final String TABLE_NAME = "TUTORS";
        public static final String COL_0_USER_ID = "user_id";
        public static final String COL_1_GENDER = "gender";
        public static final String COL_2_PHOTO_ID = "photo_id";
        public static final String COL_3_AVERAGE_TUTOR_RATING = "average_tutor_rating";
        public static final String COL_4_ANSWERS_DISPLAYED_TIMES = "answers_displayed_times";
        public static final String COL_5_LAST_SCHOOL_NAME = "last_school_name";
        public static final String COL_6_EDUCATION_FIELD = "education_field";
        public static final String COL_7_REAL_NAME = "real_name";
        public static final String COL_8_REAL_SURNAME = "real_surname";
        public static final String COL_9_BIRTH_DATE = "birth_date";
        public static final String COL_10_CITY_OF_RESIDENCY = "city_of_residency_id";
        public static final String COL_11_DISTRICT_OF_RESIDENCY = "district_of_residency_id";
        public static final String COL_12_CITY_OF_REGISTRY = "city_of_registry_id";
        public static final String COL_13_DISTRICT_OF_REGISTRY = "district_of_registry_id";
        public static final String COL_14_PHONE_NUMBER = "phone_number";
        public static final String COL_15_IBAN_NO = "iban_no";
        public static final String COL_16_CLASS_SELECTIONS = "class_selections";
    }

    public static final String isSelectedCLinkIDWithUserName = "is_selected_commission_link_id_with_user_name";
    public static final String generatedMarketerReferenceID = "generatedMarketerReferenceID";
    public static abstract class MarketersTable implements BaseColumns
    {
        public static final String TABLE_NAME = "MARKETERS";
        public static final String COL_0_USER_ID = "user_id";
        public static final String COL_1_GENDER = "gender";
        public static final String COL_2_PHOTO_ID = "photo_id";
        public static final String COL_3_LAST_SCHOOL_NAME = "last_school_name";
        public static final String COL_4_EDUCATION_FIELD = "education_field";
        public static final String COL_5_REAL_NAME = "real_name";
        public static final String COL_6_REAL_SURNAME = "real_surname";
        public static final String COL_7_BIRTH_DATE = "birth_date";
        public static final String COL_8_CITY_OF_RESIDENCY = "city_of_residency_id";
        public static final String COL_9_DISTRICT_OF_RESIDENCY = "district_of_residency_id";
        public static final String COL_10_CITY_OF_REGISTRY = "city_of_registry_id";
        public static final String COL_11_DISTRICT_OF_REGISTRY = "district_of_registry_id";
        public static final String COL_12_PHONE_NUMBER = "phone_number";
        public static final String COL_13_IBAN_NO = "iban_no";
        public static final String COL_14_MARKETING_FIELD_SELECTIONS = "marketing_field_selections";
    }
    public static abstract class MarketerReferenceIdsTable implements BaseColumns
    {
        public static final String TABLE_NAME = "MARKETER_REFERENCE_IDS";
        public static final String COL_0_USER_ID = "user_id";
        public static final String COL_1_MARKETER_REFERENCE_IDS = "generated_marketer_reference_id";
    }


    public static abstract class TutorPerformanceTable implements BaseColumns
    {
        public static final String TABLE_NAME = "TUTOR_PERFORMANCE";
        public static final String COL_0_USER_ID = "user_id";
        public static final String COL_1_OVERALL_RANKING_POINTS = "overall_ranking_points";
        public static final String COL_2_TOTAL_REQUEST_RESPONSE_DURATION_IN_SECONDS = "total_request_response_duration_in_seconds";
        public static final String COL_3_TOTAL_REQUEST_RESPONSE_TIMES = "total_request_response_times";
        public static final String COL_4_AVERAGE_TUTOR_RATING = "average_tutor_rating";
        public static final String COL_5_LAST_SIGN_IN = "last_sign_in";
    }


    public static abstract class StudentsTable implements BaseColumns
    {
        public static final String TABLE_NAME = "STUDENTS";
        public static final String COL_0_USER_ID = "user_id";
        public static final String COL_1_GENDER = "gender";
        public static final String COL_2_PHOTO_ID = "photo_id";
        public static final String COL_3_EDUCATIONAL_ATTAINMENT = "educational_attainment_id";
        public static final String COL_4_LAST_SCHOOL_NAME = "last_school_name";
        public static final String COL_5_EDUCATION_FIELD = "education_field";
        public static final String COL_6_REAL_NAME = "real_name";
        public static final String COL_7_REAL_SURNAME = "real_surname";
        public static final String COL_8_BIRTH_DATE = "birth_date";
        public static final String COL_9_CITY_OF_RESIDENCY = "city_of_residency_id";
        public static final String COL_10_DISTRICT_OF_RESIDENCY = "district_of_residency_id";
        public static final String COL_11_CITY_OF_REGISTRY = "city_of_registry_id";
        public static final String COL_12_DISTRICT_OF_REGISTRY = "district_of_registry_id";
        public static final String COL_13_PHONE_NUMBER = "phone_number";
        public static final String COL_14_CLASS_SELECTIONS = "class_selections";
    }


    public static abstract class TutorClassSelectionsTable implements BaseColumns
    {
        public static final String TABLE_NAME = "TUTOR_CLASS_SELECTIONS";
        public static final String COL_0_USER_ID = "user_id";
        public static final String COL_1_SELECTED_CLASS_ID = "selected_class_id";
    }


    public static abstract class UsersTable implements BaseColumns
    {
        public static final String TABLE_NAME = "USERS";
        public static final String COL_0_USER_ID = "user_id";
        public static final String COL_1_USER_NAME = "user_name";
        public static final String COL_2_PASSWORD = "password";
        public static final String COL_3_PASSWORD_TRIALS = "password_trials";
        public static final String COL_4_UNIQUE_GOOGLE_USER_ID = "unique_google_user_id";
        public static final String COL_5_USER_TYPE_ID = "user_type_id";
        public static final String COL_6_REFERRER_CAMPAIGN_ID = "referrer_campaign_id";
    }


    public static abstract class FavoriteTutorsTable implements BaseColumns
    {
        public static final String TABLE_NAME = "FAVORITE_TUTORS";
        public static final String COL_0_STUDENT_ID = "student_id";
        public static final String COL_1_TUTOR_ID = "tutor_id";
        public static final String COL_2_INSERT_DATE = "insert_date";
    }

    public static abstract class SolutionRatingsTable implements BaseColumns
    {
        public static final String TABLE_NAME = "SOLUTION_RATINGS";
        public static final String COL_0_COMMENTER_STUDENT_ID = "commenter_student_id";
        public static final String COL_1_QUESTION_REQUEST_ID = "question_request_id";
        public static final String COL_2_RATING = "rating";
    }


    public static abstract class CommentsTable implements BaseColumns
    {
        public static final String TABLE_NAME = "COMMENTS";
        public static final String COL_0_COMMENTER_STUDENT_ID = "commenter_student_id";
        public static final String COL_1_QUESTION_REQUEST_ID = "question_request_id";
        public static final String COL_2_COMMENT_TEXT = "comment_text";
    }


    public static abstract class SolutionsOnShoppingCartsTable implements BaseColumns
    {
        public static final String TABLE_NAME = "SOLUTIONS_ON_SHOPPING_CARTS";
        public static final String COL_0_STUDENT_ID = "student_id";
        public static final String COL_1_QUESTION_REQUEST_ID = "question_request_id";
        public static final String COL_2_CART_ADDING_DATE = "cart_adding_date";
        public static final String COL_3_PRICE = "price";
    }


    public static final String CONS_QUERY_PARAMETER_PRODUCT_ID = "product_id";
    public static final String CONS_QUERY_PARAMETER_UNITS = "units";


    public static final String CONS_IS_FREE = "is_free";
    public static abstract class UnitsTable implements BaseColumns
    {
        public static final String TABLE_NAME = "UNITS";
        public static final String COL_0_STUDENT_ID = "student_id";
        public static final String COL_1_QUESTION_REQUEST_UNITS = "question_request_units";
        public static final String COL_2_VIDEO_LECTURE_MINUTE_UNITS = "video_lecture_minute_units";
        public static final String COL_3_FREE_QUESTION_REQUEST_UNITS = "free_question_request_units";
        public static final String COL_4_FREE_VIDEO_LECTURE_MINUTE_UNITS = "free_video_lecture_minute_units";
        public static final String COL_5_LAST_FREE_UNIT_REPLENISHMENT_DATE = "last_free_unit_replenishment_time";
    }


    public static abstract class GlobalIntegerVariablesTable implements BaseColumns
    {
        public static final String TABLE_NAME = "GLOBAL_INTEGER_VARIABLES";
        public static final String COL_0_VARIABLE_ID = "variable_id";
        public static final String COL_1_VARIABLE_VALUE = "variable_value";
    }

    public static abstract class DepositsTable implements BaseColumns
    {
        public static final String TABLE_NAME = "DEPOSITS";
        public static final String COL_0_STUDENT_ID = "student_id";
        public static final String COL_1_QUESTION_REQUEST_UNITS = "question_request_units";
        public static final String COL_2_VIDEO_LECTURE_MINUTE_UNITS = "video_lecture_minute_units";
        public static final String COL_3_QUESTION_REQUEST_SUBSCRIPTION_DURATION_IN_DAYS = "question_request_subscription_duration_in_days";
        public static final String COL_4_DEPOSIT_DATE = "deposit_date";
        public static final String COL_5_DEPOSITED_AMOUNT = "deposited_amount";
    }

    public static abstract class ObjectionsTable implements BaseColumns
    {
        public static final String TABLE_NAME = "OBJECTIONS";
        public static final String COL_0_STUDENT_ID = "student_id";
        public static final String COL_1_QUESTION_REQUEST_ID = "question_request_id";
        public static final String COL_2_OBJECTION_DATE = "objection_date";
        public static final String COL_3_REASON_ID = "reason_id";
        public static final String COL_4_RESULT_ID = "result_id";
        public static final String COL_5_IS_OBJECTION_OBJECTED_BY_TUTOR = "is_objection_objected_by_tutor";
    }




    public static abstract class OtherObjectionsTable implements BaseColumns
    {
        public static final String TABLE_NAME = "OTHER_OBJECTIONS";
        public static final String COL_0_STUDENT_ID = "student_id";
        public static final String COL_1_QUESTION_REQUEST_ID = "question_request_id";
        public static final String COL_2_OTHER_OBJECTION_TEXT = "other_objection_text";
    }



    public static abstract class AbuseReportsTable implements BaseColumns
    {
        public static final String TABLE_NAME = "ABUSE_REPORTS";
        public static final String COL_0_STUDENT_ID = "student_id";
        public static final String COL_1_QUESTION_REQUEST_ID = "question_request_id";
        public static final String COL_2_REPORT_DATE = "abuse_report_date";
        public static final String COL_3_REASON_ID = "reason_id";
        public static final String COL_4_RESULT_ID = "result_id";
    }



    public static abstract class QuestionRequestsTable implements BaseColumns
    {
        public static final String TABLE_NAME = "QUESTION_REQUESTS";
        public static final String COL_0_QUESTION_REQUEST_ID = "question_request_id";
        public static final String COL_1_STUDENT_ID = "student_id";
        public static final String COL_2_TUTOR_ID = "tutor_id";
        public static final String COL_3_CLASS_ID = "class_id";
        public static final String COL_4_LESSON_ID = "lesson_id";
        public static final String COL_5_QUESTION_IMAGE_ID = "question_image_id";
        public static final String COL_6_PAGE_NO = "page_no";
        public static final String COL_7_APPRECIATED_PRICE = "appreciated_price";
        public static final String COL_8_COUNTER_PRICE_DEMAND = "counter_price_demand";
        public static final String COL_9_SOLUTION_VIDEO_URL_ID = "solution_video_url_id";
        public static final String COL_10_PUBLISHER_ID = "publisher_id";
        public static final String COL_11_BOOK_ID = "book_id";
        public static final String COL_12_LAST_DELIVERY_DATE = "last_delivery_date";
        public static final String COL_13_DUE_DATE_FOR_ACCEPTANCE = "due_date_for_acceptance";
        public static final String COL_14_QUESTION_REQUEST_STATE_ID = "question_request_state_id";
        public static final String COL_15_WITHDRAWAL_EXCUSE_ID = "withdrawal_excuse_id";
        public static final String COL_16_REQUEST_POST_DATE = "request_post_date";
        public static final String COL_17_SOLUTION_POST_DATE = "solution_post_date";
    }


    public static abstract class PurchasedSolutionsTable implements BaseColumns
    {
        public static final String TABLE_NAME = "PURCHASED_SOLUTIONS";
        public static final String COL_0_STUDENT_ID = "student_id";
        public static final String COL_1_QUESTION_REQUEST_ID = "question_request_id";
        public static final String COL_2_PURCHASE_DATE = "purchase_date";
        public static final String COL_3_PRICE = "price";
    }


    public static abstract class PaymentRequestsTable implements BaseColumns
    {
        public static final String TABLE_NAME = "PAYMENT_REQUESTS";
        public static final String COL_0_USER_ID = "user_id";
        public static final String COL_1_REQUEST_DATE = "request_date";
        public static final String COL_2_PAYMENT_REQUEST_STATE_ID = "payment_request_state_id";
        public static final String COL_3_REQUESTED_AMOUNT = "requested_amount";
        public static final String COL_4_PAID_AMOUNT = "paid_amount";
    }



    public static abstract class QuestionImagesTable implements BaseColumns
    {
        public static final String TABLE_NAME = "QUESTION_IMAGES";
        public static final String COL_0_QUESTION_IMAGE_ID = "question_image_id";
        public static final String COL_1_IMAGE_DATA = "image_data";
    }


    public static abstract class QuestionMiniImagesTable implements BaseColumns
    {
        public static final String TABLE_NAME = "QUESTION_MINI_IMAGES";
        public static final String COL_0_QUESTION_IMAGE_ID = "question_image_id";
        public static final String COL_1_IMAGE_DATA = "image_data";
    }

    public static final String DIRECTORY = "directory";

    public static abstract class UserPhotosTable implements BaseColumns
    {
        public static final String TABLE_NAME = "USER_PHOTOS";
        public static final String COL_0_PHOTO_ID = "photo_id";
        public static final String COL_1_PHOTO_DATA = "photo_data";
    }

    public static abstract class UserMiniPhotosTable implements BaseColumns
    {
        public static final String TABLE_NAME = "USER_MINI_PHOTOS";
        public static final String COL_0_PHOTO_ID = "photo_id";
        public static final String COL_1_PHOTO_DATA = "photo_data";
    }

    public static abstract class QuestionImagesDataTable implements BaseColumns
    {
        public static final String TABLE_NAME = "QUESTION_IMAGES_DATA";
        public static final String COL_0_QUESTION_IMAGE_ID = "question_image_id";
        public static final String COL_1_IMAGE_DATA_ADDRESS_ID = "image_data_address_id";
    }


    public static abstract class QuestionMiniImagesDataTable implements BaseColumns
    {
        public static final String TABLE_NAME = "QUESTION_MINI_IMAGES_DATA";
        public static final String COL_0_QUESTION_IMAGE_ID = "question_image_id";
        public static final String COL_1_IMAGE_DATA_ADDRESS_ID = "image_data_address_id";
    }


    public static abstract class UserPhotosDataTable implements BaseColumns
    {
        public static final String TABLE_NAME = "USER_PHOTOS_DATA";
        public static final String COL_0_PHOTO_ID = "photo_id";
        public static final String COL_1_PHOTO_DATA_ADDRESS_ID = "photo_data_address_id";
    }

    public static abstract class UserMiniPhotosDataTable implements BaseColumns
    {
        public static final String TABLE_NAME = "USER_MINI_PHOTOS_DATA";
        public static final String COL_0_PHOTO_ID = "photo_id";
        public static final String COL_1_PHOTO_DATA_ADDRESS_ID = "photo_data_address_id";
    }





}
