package com.onlinehocam.ozel.ders.okul;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.onlinehocam.ozel.ders.okul.AsyncTasks.AsyncTaskHelper;

import java.util.ArrayList;
import java.util.List;

public class CustomTutorDiscoveryFeedInjector
{
    Activity activity;
    Context context;
    int stepNo;
    List<Integer> tutorsIDs;
    public List<Tutor> tutorsList;
    List<Tutor> additonalTutorsList;
    List<CustomTutorLinearLayout> tutorDisplayLinearLayouts;
    List<CustomTutorLinearLayout> additionalTutorDisplayLinearLayouts;
    LinearLayout mainFeedLinearLayout;
    public List<Integer> usersFavoriteTutorIDsList;


    TextView textViewShowMoreTutorMessage;
    ProgressBar progressBarLoading;
    Button buttonShowMoreTutor;



    //Constructor for Creating Discovery Tutors Injector For the First Time
    public CustomTutorDiscoveryFeedInjector(Activity activity, Context context, LinearLayout mainFeedLinearLayout) {
        this.activity = activity;
        this.context = context;
        this.mainFeedLinearLayout = mainFeedLinearLayout;
        this.stepNo = 0;
        this.tutorsIDs = ((StudentsTutorDiscoveryActivity)(activity)).tutorsIDs;
        this.tutorDisplayLinearLayouts = ((StudentsTutorDiscoveryActivity)(activity)).tutorDisplayLinearLayouts;
        this.usersFavoriteTutorIDsList = ((StudentsTutorDiscoveryActivity)(activity)).usersFavoriteTutorIDsList;
        this.tutorsList = ((StudentsTutorDiscoveryActivity)(activity)).tutorsList;

        this.textViewShowMoreTutorMessage = ((StudentsTutorDiscoveryActivity)(activity)).textViewShowMoreTutorMessage;
        this.progressBarLoading = ((StudentsTutorDiscoveryActivity)(activity)).progressBarLoading;
        this.buttonShowMoreTutor = ((StudentsTutorDiscoveryActivity)(activity)).buttonShowMoreTutor;

        ExtendWithNewTutors();
    }

    //Constructor for RECREATING Granted Discovery Tutors While Expanding again
    public CustomTutorDiscoveryFeedInjector(Activity activity, Context context, LinearLayout mainFeedLinearLayout, int stepNo) {
        this.activity = activity;
        this.context = context;
        this.mainFeedLinearLayout = mainFeedLinearLayout;
        this.stepNo = stepNo;
        this.tutorsIDs = ((StudentsTutorDiscoveryActivity)(activity)).tutorsIDs;
        this.tutorDisplayLinearLayouts = ((StudentsTutorDiscoveryActivity)(activity)).tutorDisplayLinearLayouts;
        this.usersFavoriteTutorIDsList = ((StudentsTutorDiscoveryActivity)(activity)).usersFavoriteTutorIDsList;
        this.tutorsList = ((StudentsTutorDiscoveryActivity)(activity)).tutorsList;

        this.textViewShowMoreTutorMessage = ((StudentsTutorDiscoveryActivity)(activity)).textViewShowMoreTutorMessage;
        this.progressBarLoading = ((StudentsTutorDiscoveryActivity)(activity)).progressBarLoading;
        this.buttonShowMoreTutor = ((StudentsTutorDiscoveryActivity)(activity)).buttonShowMoreTutor;

        //GlobalVariables.Log(context, "on CONSTRUCTOR CustomTutorDiscoveryFeedInjector >>> stepNo: "+stepNo);
        DirectlyInsertGrantedTutors();
    }


    //Constructor for Creating FAVORITE Tutors Injector For the FIRST TIME
    public CustomTutorDiscoveryFeedInjector(Activity activity, Context context, LinearLayout mainFeedLinearLayout, List<Integer> usersFavoriteTutorIDsList) {
        this.activity = activity;
        this.context = context;
        this.mainFeedLinearLayout = mainFeedLinearLayout;
        this.stepNo = 0;
        this.tutorsIDs = usersFavoriteTutorIDsList;
        this.tutorDisplayLinearLayouts = ((StudentsTutorDiscoveryActivity)(activity)).favoriteTutorDisplayLinearLayouts;
        this.usersFavoriteTutorIDsList = ((StudentsTutorDiscoveryActivity)(activity)).usersFavoriteTutorIDsList;
        this.tutorsList = ((StudentsTutorDiscoveryActivity)(activity)).favoriteTutorsList;

        this.textViewShowMoreTutorMessage = ((StudentsTutorDiscoveryActivity)(activity)).textViewShowMoreFavoriteTutorMessage;
        this.progressBarLoading = ((StudentsTutorDiscoveryActivity)(activity)).progressBarFavoriteLoading;
        this.buttonShowMoreTutor = ((StudentsTutorDiscoveryActivity)(activity)).buttonShowMoreFavoriteTutor;


        //GlobalVariables.Log(context, "on CONSTRUCTOR CustomTutorDiscoveryFeedInjector first time Favorite >>> tutorsIDs: "+tutorsIDs.size());

        ExtendWithNewTutors();
    }


    //Constructor for RECREATING Granted FAVORITE Tutors While Expanding again
    public CustomTutorDiscoveryFeedInjector(Activity activity, Context context, LinearLayout mainFeedLinearLayout, List<Integer> usersFavoriteTutorIDsList, int stepNo) {
        this.activity = activity;
        this.context = context;
        this.mainFeedLinearLayout = mainFeedLinearLayout;
        this.stepNo = stepNo;
        this.tutorsIDs = usersFavoriteTutorIDsList;
        this.tutorDisplayLinearLayouts = ((StudentsTutorDiscoveryActivity)(activity)).favoriteTutorDisplayLinearLayouts;
        this.usersFavoriteTutorIDsList = ((StudentsTutorDiscoveryActivity)(activity)).usersFavoriteTutorIDsList;
        this.tutorsList = ((StudentsTutorDiscoveryActivity)(activity)).favoriteTutorsList;

        this.textViewShowMoreTutorMessage = ((StudentsTutorDiscoveryActivity)(activity)).textViewShowMoreFavoriteTutorMessage;
        this.progressBarLoading = ((StudentsTutorDiscoveryActivity)(activity)).progressBarFavoriteLoading;
        this.buttonShowMoreTutor = ((StudentsTutorDiscoveryActivity)(activity)).buttonShowMoreFavoriteTutor;


        //GlobalVariables.Log(context, "on CONSTRUCTOR CustomTutorDiscoveryFeedInjector refilling Favorite >>> tutorsIDs: "+tutorsIDs.size());

        DirectlyInsertGrantedTutors();
    }



    private void DirectlyInsertGrantedTutors()
    {
        //GlobalVariables.Log(context, "ENTERED DirectlyInsertGrantedTutors: ");
        buttonShowMoreTutor.setEnabled(false);
        progressBarLoading.setVisibility(View.VISIBLE);


        //GlobalVariables.Log(context, "tutorsIDs.size() - GlobalVariables.TUTORS_AT_A_TIME_TO_DISPLAY * stepNo: "+(tutorsIDs.size() - GlobalVariables.TUTORS_AT_A_TIME_TO_DISPLAY * stepNo));

        if(tutorsIDs.size() > 0)
        {
            /*additonalTutorsList = ServerHelper.GetTutorListWithTutorIDsList(GetTutorIdsForThisStep(tutorsIDs));
            for (int i = 0; i < additonalTutorsList.size(); i++)
            {
                tutorsList.add(additonalTutorsList.get(i));
            }
            additionalTutorDisplayLinearLayouts = GetTutorDisplayLinearLayoutsListWithTutorObjectList(additonalTutorsList);
            for (int i = 0; i < additionalTutorDisplayLinearLayouts.size(); i++)
            {
                tutorDisplayLinearLayouts.add(additionalTutorDisplayLinearLayouts.get(i));
            }*/

            DirectlyInsertLinearLayoutsForGrantedTutors();
        }
        else
        {
            textViewShowMoreTutorMessage.setText(R.string.tutor_discovery_no_more_tutors_to_show);
        }

        progressBarLoading.setVisibility(View.GONE);
        buttonShowMoreTutor.setEnabled(true);
    }

    void InsertAdditionalLinearLayoutsForAdditionalTutors()
    {
        if(stepNo>3) // Max 40 Tutors Displayed
        {
            for (int i = 0; i < Math.min(tutorDisplayLinearLayouts.size(), GlobalVariables.TUTORS_AT_A_TIME_TO_DISPLAY); i++)
            {
                mainFeedLinearLayout.removeView(tutorDisplayLinearLayouts.get(i));
            }
            for (int i = 0; i < Math.min(tutorDisplayLinearLayouts.size(), GlobalVariables.TUTORS_AT_A_TIME_TO_DISPLAY); i++)
            {
                tutorDisplayLinearLayouts.remove(tutorDisplayLinearLayouts.get(0));
            }
        }
        for (int i = 0; i < additionalTutorDisplayLinearLayouts.size(); i++)
        {
            mainFeedLinearLayout.addView(additionalTutorDisplayLinearLayouts.get(i));
        }
        stepNo++;
    }

    void DirectlyInsertLinearLayoutsForGrantedTutors()
    {
        //GlobalVariables.Log(context, "ENTERED DirectlyInsertLinearLayoutsForGrantedTutors: ");
        //GlobalVariables.Log(context, "DirectlyInsertLinearLayoutsForGrantedTutors tutorDisplayLinearLayouts.size: "+tutorDisplayLinearLayouts.size());
        //GlobalVariables.Log(context, "DirectlyInsertLinearLayoutsForGrantedTutors tutorsIDs.size: "+tutorsIDs.size());
        //Displays MAX 40 Tutors due to -4 below
        if(!tutorDisplayLinearLayouts.isEmpty())
        {
            for(int i = 0 ; i < tutorDisplayLinearLayouts.size(); i++)
            {
                if(tutorDisplayLinearLayouts.get(i).getParent() != null)
                {
                    //GlobalVariables.Log(context, "removedTutor indices >>> i: "+i);
                    ((LinearLayout)tutorDisplayLinearLayouts.get(i).getParent()).removeView(tutorDisplayLinearLayouts.get(i));
                }
            }
            for(int i =  0 ; i < tutorDisplayLinearLayouts.size(); i++)
            {
                //GlobalVariables.Log(context, "insertedTutor indices >>> i: "+i);
                mainFeedLinearLayout.addView(tutorDisplayLinearLayouts.get(i));
            }
        }
        /*
        for(int i = Math.max(GlobalVariables.TUTORS_AT_A_TIME_TO_DISPLAY * (stepNo-4), 0) ; i < Math.min(GlobalVariables.TUTORS_AT_A_TIME_TO_DISPLAY * (stepNo), tutorDisplayLinearLayouts.size()); i++)
        {
            if(tutorDisplayLinearLayouts.get(i).getParent() != null)
            {
                //GlobalVariables.Log(context, "removedTutor indices >>> i: "+i);
                ((LinearLayout)tutorDisplayLinearLayouts.get(i).getParent()).removeView(tutorDisplayLinearLayouts.get(i));
            }
        }
        for(int i = Math.max(GlobalVariables.TUTORS_AT_A_TIME_TO_DISPLAY * (stepNo-4), 0) ; i < Math.min(GlobalVariables.TUTORS_AT_A_TIME_TO_DISPLAY * (stepNo), tutorDisplayLinearLayouts.size()); i++)
        {
            //GlobalVariables.Log(context, "insertedTutor indices >>> i: "+i);
            mainFeedLinearLayout.addView(tutorDisplayLinearLayouts.get(i));
        }*/
        new CustomToast(activity, context, "crMainFieldChildCount: "+mainFeedLinearLayout.getChildCount());
    }

    private List<Integer> GetTutorIdsForThisStep(List<Integer> tutorsIDs)
    {
        List<Integer> result = new ArrayList<>();
        for(int i = GlobalVariables.TUTORS_AT_A_TIME_TO_DISPLAY * stepNo ; i < Math.min(GlobalVariables.TUTORS_AT_A_TIME_TO_DISPLAY * (stepNo+1), tutorsIDs.size()); i++)
        {
            //GlobalVariables.Log(context, "on GetTutorIdsForThisStep >>> i: "+i);
            result.add(tutorsIDs.get(i));
        }
        return result;
    }


    private List<CustomTutorLinearLayout> GetTutorDisplayLinearLayoutsListWithTutorObjectList(List<Tutor> additionalTutorDisplayLinearLayouts)
    {
        List<CustomTutorLinearLayout> result = new ArrayList<>();

        for (int i = 0; i < additonalTutorsList.size(); i++)
        {
            result.add(GetTutorLinearLayoutByTutorObject(additonalTutorsList.get(i)));
        }

        return result;
    }


    private CustomTutorLinearLayout GetTutorLinearLayoutByTutorObject(Tutor t)
    {
        LinearLayout child = (LinearLayout) LayoutInflater.from(activity).inflate(R.layout.custom_tutor_discovery_feed_element, null);
        ImageView imageViewPhoto = (ImageView) (child.findViewById(R.id.imageViewPhoto));
        TextView textViewTutorName = child.findViewById(R.id.textViewTutorName);
        TextView textViewRating = child.findViewById(R.id.textViewRating);
        TextView textViewLastSchool = child.findViewById(R.id.textViewLastSchool);
        final LinearLayout linearLayoutCommentsFeed = child.findViewById(R.id.linearLayoutCommentsFeed);
        TextView textViewEducationField = child.findViewById(R.id.textViewEducationField);
        Button buttonAddToFavorites = child.findViewById(R.id.buttonAddToFavorites);
        final Button buttonShowComments = child.findViewById(R.id.buttonShowComments);
        final Button buttonHideComments = child.findViewById(R.id.buttonHideComments);
        final TextView textViewCommentsWarning = child.findViewById(R.id.textViewCommentsWarning);
        final ProgressBar progressBarCommentWaiting = child.findViewById(R.id.progressBarCommentWaiting);
        boolean isFavorite = false;

        final CustomTutorLinearLayout result = new CustomTutorLinearLayout(context, child, t.userID);

        if(t.photoImage != null)
        {
            imageViewPhoto.setImageBitmap(t.photoImage);
        }
        else
        {
            if(t.gender.equals(GlobalVariables.GENDER_MALE))
            {
                imageViewPhoto.setImageResource(R.drawable.male_template);
            }
            else
            {
                imageViewPhoto.setImageResource(R.drawable.female_template);
            }
        }
        textViewTutorName.setText(t.userName);
        textViewRating.setText("" + t.tutorRating);
        if(t.tutorRating == -1.0)
        {
            textViewRating.setText("N/A");
        }
        textViewLastSchool.setText(t.lastSchoolName);
        textViewEducationField.setText(t.educationField);

        //Handle The Button Add To Favorites
        if(usersFavoriteTutorIDsList.contains(t.userID))
        {
            isFavorite = true;
            buttonAddToFavorites.setText(R.string.tutor_discovery_remove_from_favorites);
        }
        else
        {
            isFavorite = false;
            buttonAddToFavorites.setText(R.string.tutor_discovery_add_to_favorites);
        }

        //ADD TO FAVORITES BUTTON
        final Tutor fT = t;
        final Button finalButtonAddToFavorites = buttonAddToFavorites;
        buttonAddToFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fT.isFavorite)
                {
                    if(ServerHelper.RemoveTutorFromFavorites(GlobalVariables.USER_ID, fT.userID))
                    {
                        GlobalVariables.Toast(context, fT.userName + " " + context.getString(R.string.question_requests_tutor_removed_from_favorites_succesfully));
                        ((StudentsTutorDiscoveryActivity)(activity)).usersFavoriteTutorIDsList.remove((Integer) (fT.userID));
                        finalButtonAddToFavorites.setText(R.string.tutor_discovery_add_to_favorites);
                        fT.isFavorite = false;
                    }
                    else
                    {
                        GlobalVariables.Toast(context, fT.userName + " " + context.getString(R.string.question_requests_tutor_failed_to_be_removed_from_favorites));
                    }
                }
                else
                {
                    if(ServerHelper.AddTutorToFavorites(GlobalVariables.USER_ID, fT.userID))
                    {
                        GlobalVariables.Toast(context, fT.userName + " " + context.getString(R.string.question_requests_tutor_added_to_favorites_succesfully));
                        ((StudentsTutorDiscoveryActivity)(activity)).usersFavoriteTutorIDsList.add((Integer) (fT.userID));
                        finalButtonAddToFavorites.setText(R.string.tutor_discovery_remove_from_favorites);
                        fT.isFavorite = true;
                    }
                    else
                    {
                        GlobalVariables.Toast(context, fT.userName + " " + context.getString(R.string.question_requests_tutor_failed_to_be_added_to_favorites));
                    }
                }
            }
        });


        final Activity fActivity = activity;

        final List<String> suggestedCommentIDs = ServerHelper.GetSuggestedCommentIDs(t.userID);
        buttonShowComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(linearLayoutCommentsFeed.getVisibility() == View.GONE)
                {
                    linearLayoutCommentsFeed.setVisibility(View.VISIBLE);
                }

                if(!result.isShowCommentTriggered)
                {
                    if(suggestedCommentIDs.isEmpty())
                    {
                        textViewCommentsWarning.setVisibility(View.VISIBLE);
                        textViewCommentsWarning.setText(R.string.tutor_discovery_no_comment);
                    }
                    else
                    {
                        if(result.customSolutionCommentInjector == null)
                        {
                            result.customSolutionCommentInjector = new CustomSolutionCommentInjector(activity, context, fT.userID, suggestedCommentIDs, progressBarCommentWaiting);
                            buttonHideComments.setVisibility(View.VISIBLE);
                            buttonShowComments.setText(R.string.tutor_discovery_show_more_comments);
                            result.isShowCommentTriggered = true;

                            if(!((StudentsTutorDiscoveryActivity)fActivity).ExtendWithNewComments(result.customSolutionCommentInjector))
                            {
                                textViewCommentsWarning.setVisibility(View.VISIBLE);
                                textViewCommentsWarning.setText(R.string.tutor_discovery_no_more_comment);
                            }
                        }
                        else
                        {
                            buttonHideComments.setVisibility(View.VISIBLE);
                            linearLayoutCommentsFeed.setVisibility(View.VISIBLE);
                            buttonShowComments.setText(R.string.tutor_discovery_show_more_comments);
                            result.isShowCommentTriggered = true;
                        }
                    }
                }
                else
                {
                    if(!((StudentsTutorDiscoveryActivity)fActivity).ExtendWithNewComments(result.customSolutionCommentInjector))
                    {
                        textViewCommentsWarning.setVisibility(View.VISIBLE);
                        textViewCommentsWarning.setText(R.string.tutor_discovery_no_more_comment);
                    }
                }
            }
        });

        buttonHideComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonHideComments.setVisibility(View.GONE);
                if(textViewCommentsWarning.getVisibility() == View.VISIBLE)
                {
                    textViewCommentsWarning.setVisibility(View.GONE);
                }
                /*for(int i = 0; i < linearLayoutCommentsFeed.getChildCount(); i++)
                {
                    linearLayoutCommentsFeed.removeView(linearLayoutCommentsFeed.getChildAt(0));
                }*/
                linearLayoutCommentsFeed.setVisibility(View.GONE);
                buttonShowComments.setText(R.string.tutor_discovery_show_comments);
                result.isShowCommentTriggered = false;
            }
        });


        buttonAddToFavorites.setEnabled(false);
        buttonShowComments.setEnabled(false);


        SetBorderToLayout(result, 4, R.color.colorBlack);
        result.setPadding(8,8,8,8);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //lp.bottomMargin = 10;
        result.setLayoutParams(lp);

        AsyncTaskHelper.SetHandleAndDisplayGetTutorByTutorID(activity, context, ((StudentsTutorDiscoveryActivity)activity).linearLayoutMainProgressBar, ((StudentsTutorDiscoveryActivity)activity).linearLayoutMainMenu, ((StudentsTutorDiscoveryActivity)activity).linearLayoutMainPanelMyFavoriteTutors, t.userID, this);

        return result;
    }


    void SetBorderToLayout(CustomTutorLinearLayout linearLayout, int strokeTickness, int strokeColorCode)
    {
        //use a GradientDrawable with only one color set, to make it a solid color
        GradientDrawable border = new GradientDrawable();
        border.setColor(0xFFFFFFFF); //white background
        border.setStroke(strokeTickness, strokeColorCode); //black border with full opacity
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            linearLayout.setBackgroundDrawable(border);
        } else {
            linearLayout.setBackground(border);
        }
    }

    public void ExtendWithNewTutors()
    {
        buttonShowMoreTutor.setEnabled(false);
        progressBarLoading.setVisibility(View.VISIBLE);

        if(tutorsIDs.size() - GlobalVariables.TUTORS_AT_A_TIME_TO_DISPLAY * stepNo > 0)
        {
            //GlobalVariables.Log(context, "tutorsList.size before: "+ "   tutorsList.size: "+tutorsList.size());
            additonalTutorsList = GetEmptyTutorListWithTutorIDsList(GetTutorIdsForThisStep(tutorsIDs));
            for (int i = 0; i < additonalTutorsList.size(); i++)
            {
                tutorsList.add(additonalTutorsList.get(i));
            }
            //GlobalVariables.Log(context, "additonalTutorsList.size after: " + additonalTutorsList.size() + "   tutorsList.size: "+tutorsList.size());

            additionalTutorDisplayLinearLayouts = GetTutorDisplayLinearLayoutsListWithTutorObjectList(additonalTutorsList);
            for (int i = 0; i < additionalTutorDisplayLinearLayouts.size(); i++)
            {
                tutorDisplayLinearLayouts.add(additionalTutorDisplayLinearLayouts.get(i));
            }
            //GlobalVariables.Log(context, "additionalTutorDisplayLinearLayouts.size after: " + additionalTutorDisplayLinearLayouts.size() + "   tutorDisplayLinearLayouts.size: "+tutorDisplayLinearLayouts.size());

            InsertAdditionalLinearLayoutsForAdditionalTutors();
        }
        else
        {
            textViewShowMoreTutorMessage.setText(R.string.tutor_discovery_no_more_tutors_to_show);
        }

        progressBarLoading.setVisibility(View.GONE);
        buttonShowMoreTutor.setEnabled(true);

        //GlobalVariables.Log(context, "after ExtendWithNewTutors >>> stepNo: " + stepNo);
    }



    public static List<Tutor> GetEmptyTutorListWithTutorIDsList(List<Integer> tutorUserIDsList)
    {
        List<Tutor> result = new ArrayList<>();

        //TODO Fill Method GetTutorListWithTutorIDsList
        for(int i = 0; i < tutorUserIDsList.size(); i++)
        {
            result.add(GetEmptyTutorByTutorID(tutorUserIDsList.get(i)));
        }

        return result;
    }



    public static Tutor GetEmptyTutorByTutorID(int tutorUserID)
    {

        int userID = tutorUserID;
        String userName;
        String gender;
        int photoID;
        Bitmap photoImage;
        double tutorRating;
        int answersDisplayedTimes;
        String lastSchoolName;
        String educationField;
        int[] mostRecentCommentIDsArray;
        int[] allCommentIDsArray;
        boolean isFavorite;
        String classSelection;


        userName = "";
        gender = "";
        photoID = -1;
        photoImage = null;
        tutorRating = -1;
        answersDisplayedTimes = 0;
        lastSchoolName = "";
        educationField = "";
        mostRecentCommentIDsArray = new int[0];
        allCommentIDsArray  = new int[0];
        isFavorite = false;
        classSelection = "";


        return new Tutor(tutorUserID, userName, gender, photoID, photoImage, tutorRating, answersDisplayedTimes, lastSchoolName, educationField, mostRecentCommentIDsArray, allCommentIDsArray, isFavorite, classSelection);
    }

    public CustomTutorLinearLayout GetCustomTutorLinearLayoutByTutorIDFromList(int tutorID)
    {
        CustomTutorLinearLayout result = null;
        for(int i = 0; i < tutorDisplayLinearLayouts.size(); i++)
        {
            if(tutorDisplayLinearLayouts.get(i).tutorID == tutorID)
            {
                return tutorDisplayLinearLayouts.get(i);
            }
        }
        return result;
    }

    /*


    public static Tutor GetEmptyTutorByTutorID(int tutorUserID)
    {

        int userID;
        String userName;
        String gender;
        int photoID;
        Bitmap photoImage;
        double tutorRating;
        int answersDisplayedTimes;
        String lastSchoolName;
        String educationField;
        int[] mostRecentCommentIDsArray;
        int[] allCommentIDsArray;
        boolean isFavorite;


        //TODO TEST Method GetTutorByTutorID

        userID = tutorUserID;
        if((""+userID).length() < 9)
        {
            String newUserIDStr = ""+userID;
            if(userID == 0)
            {
                newUserIDStr += ""+(GlobalVariables.r.nextInt(9)+1);
            }
            for(int i = (""+userID).length(); i < 9; i++)
            {
                newUserIDStr += "" + GlobalVariables.r.nextInt(10);
            }
            userID = Integer.parseInt(newUserIDStr);
        }
        int randNoForName = Integer.parseInt((""+userID).substring((""+userID).length()-2, (""+userID).length()));
        int randNoForRating = Integer.parseInt((""+userID).substring((""+userID).length()-4, (""+userID).length()-2));
        int randNoForSchoolName = Integer.parseInt((""+userID).substring((""+userID).length()-5, (""+userID).length()-3));
        int randNoForEdField = Integer.parseInt((""+userID).substring((""+userID).length()-6, (""+userID).length()-4));
        userName = exampleTutorNames[randNoForName%(exampleTutorNames.length)];
        gender = GlobalVariables.GENDERS_ARRAY[GlobalVariables.r.nextInt(GlobalVariables.GENDERS_ARRAY.length)];
        photoID = 256473731+GlobalVariables.r.nextInt(899999999);
        photoImage = null;
        tutorRating = ((double) ((randNoForRating%26) / (double)10.0)) + 2.5;
        answersDisplayedTimes = GlobalVariables.r.nextInt(400);
        lastSchoolName = exampleSchoolNames[randNoForSchoolName%(exampleSchoolNames.length)];
        educationField = exampleEducationFieldNames[randNoForEdField%(exampleEducationFieldNames.length)];
        mostRecentCommentIDsArray = new int[0];
        allCommentIDsArray  = new int[0];
        isFavorite = false;


        return new Tutor(tutorUserID, userName, gender, photoID, photoImage, tutorRating, answersDisplayedTimes, lastSchoolName, educationField, mostRecentCommentIDsArray, allCommentIDsArray, isFavorite);
    }

    * */

}
